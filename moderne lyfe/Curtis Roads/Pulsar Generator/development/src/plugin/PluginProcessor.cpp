#include "PluginProcessor.h"
#include "PluginEditor.h"

namespace {

juce::AudioProcessorValueTreeState::ParameterLayout makeLayout()
{
    using namespace juce;
    AudioProcessorValueTreeState::ParameterLayout l;
    auto boolean = [&](const char* id, const char* name, bool def) { l.add(std::make_unique<AudioParameterBool>(ParameterID{ id, 1 }, name, def)); };
    auto real = [&](const char* id, const char* name, NormalisableRange<float> r, float def) { l.add(std::make_unique<AudioParameterFloat>(ParameterID{ id, 1 }, name, r, def)); };
    auto integer = [&](const char* id, const char* name, int lo, int hi, int def) { l.add(std::make_unique<AudioParameterInt>(ParameterID{ id, 1 }, name, lo, hi, def)); };

    boolean("run", "Run", false);
    for (int i = 1; i <= 3; ++i) {
        boolean(("train" + std::to_string(i) + "On").c_str(), ("Train " + std::to_string(i) + " On").c_str(), true);
        real(("train" + std::to_string(i) + "Level").c_str(), ("Train " + std::to_string(i) + " Volume").c_str(), { 0.0f, 10.0f, 0.1f }, 3.0f);
    }
    boolean("burstOn", "Burst Ratio", false);
    integer("burstOns", "Burst On", 1, 100, 2);
    integer("burstOffs", "Burst Off", 0, 100, 1);
    real("trainDur", "Train Duration", { 0.1f, 100.0f, 0.1f, 0.4f }, 10.0f);
    real("edge", "Edge", { 0.0f, 10.0f, 0.1f }, 5.0f);
    real("master", "Master Volume", { 0.0f, 3.0f, 0.001f }, 1.0f);
    boolean("mute", "Mute", false);
    boolean("loop", "Loop", true);
    boolean("freeze", "Freeze", false);
    boolean("backwards", "Backwards", false);
    real("ringMix", "Ring Mod Dry/Wet", { 0.0f, 1.0f, 0.001f }, 0.0f);
    real("fadeTime", "Fade Time", { 0.0f, 30.0f, 0.05f, 0.35f }, 2.0f);
    boolean("linkSettings", "Link Play Controls to Tables", true);
    // Model 2: one switch per fix, and A/B against Model 1.
    for (int i = 0; i < pulsar::Fixes::kCount; ++i)
        boolean((std::string("fix_") + pulsar::Fixes::id(i)).c_str(), (std::string("Fix: ") + pulsar::Fixes::name(i)).c_str(), false);
    boolean("abModel1", "A/B: Hear Model 1", false);
    return l;
}

float value(const juce::AudioProcessorValueTreeState& p, const char* id) { return p.getRawParameterValue(id)->load(); }

void setParam(juce::AudioProcessorValueTreeState& p, const char* id, float v)
{
    if (auto* param = p.getParameter(id))
        param->setValueNotifyingHost(param->convertTo0to1(v));
}

} // namespace

PulsarProcessor::PulsarProcessor()
    : AudioProcessor(BusesProperties().withOutput("Output", juce::AudioChannelSet::stereo(), true)),
      params(*this, nullptr, "PulsarGenerator", makeLayout())
{
    for (int i = 0; i < 3; ++i) {
        p.trainOn[(size_t) i] = params.getRawParameterValue("train" + juce::String(i + 1) + "On");
        p.trainLevel[(size_t) i] = params.getRawParameterValue("train" + juce::String(i + 1) + "Level");
    }
    p.run = params.getRawParameterValue("run");
    p.burstOn = params.getRawParameterValue("burstOn");
    p.burstOns = params.getRawParameterValue("burstOns");
    p.burstOffs = params.getRawParameterValue("burstOffs");
    p.trainDur = params.getRawParameterValue("trainDur");
    p.edge = params.getRawParameterValue("edge");
    p.master = params.getRawParameterValue("master");
    p.mute = params.getRawParameterValue("mute");
    p.loop = params.getRawParameterValue("loop");
    p.freeze = params.getRawParameterValue("freeze");
    p.backwards = params.getRawParameterValue("backwards");
    p.ringMix = params.getRawParameterValue("ringMix");
    p.fadeTime = params.getRawParameterValue("fadeTime");
    p.linkSettings = params.getRawParameterValue("linkSettings");
    for (int i = 0; i < pulsar::Fixes::kCount; ++i)
        p.fixes[(size_t) i] = params.getRawParameterValue(juce::String("fix_") + pulsar::Fixes::id(i));
    p.abModel1 = params.getRawParameterValue("abModel1");
    writerThread.startThread();
    startTimerHz(30);
}

PulsarProcessor::~PulsarProcessor()
{
    stopTimer();
    stopRecording();
    writerThread.stopThread(2000);
}

bool PulsarProcessor::isBusesLayoutSupported(const BusesLayout& layouts) const
{
    return layouts.getMainOutputChannelSet() == juce::AudioChannelSet::stereo();
}

void PulsarProcessor::prepareToPlay(double sampleRate, int)
{
    engine.prepare(sampleRate);
    engine.setTables(pulsar::TableData::from(current));
    setLatencySamples(0);
}

pulsar::Controls PulsarProcessor::readControls() const
{
    pulsar::Controls c;
    for (size_t i = 0; i < 3; ++i) {
        c.trainOn[i] = p.trainOn[i]->load() > 0.5f;
        c.trainLevel[i] = p.trainLevel[i]->load();
    }
    c.burstOn = p.burstOn->load() > 0.5f;
    c.burstOns = (int) p.burstOns->load();
    c.burstOffs = (int) p.burstOffs->load();
    c.trainDuration = p.trainDur->load();
    c.edge = p.edge->load();
    c.masterVolume = p.master->load();
    c.mute = p.mute->load() > 0.5f;
    c.loop = p.loop->load() > 0.5f;
    c.freeze = p.freeze->load() > 0.5f;
    c.backwards = p.backwards->load() > 0.5f;
    c.ringModBlend = p.ringMix->load();
    return c;
}

void PulsarProcessor::processBlock(juce::AudioBuffer<float>& buffer, juce::MidiBuffer&)
{
    juce::ScopedNoDenormals noDenormals;
    buffer.clear();

    {
        const juce::SpinLock::ScopedTryLockType lock(pendingLock);
        if (lock.isLocked()) {
            if (pendingTables) {
                if (pendingCrossfade) engine.crossfadeTo(std::move(pendingTables), pendingFadeTime);
                else engine.setTables(std::move(pendingTables));
                pendingTables.reset();
            }
            if (pendingLoopSet) { engine.setRingModSound(std::move(pendingLoop)); pendingLoopSet = false; }
        }
    }

    engine.setControls(readControls());
    engine.setFixes(p.abModel1->load() > 0.5f ? pulsar::Fixes::model1() : fixes());
    const bool wantRun = p.run->load() > 0.5f;
    if (wantRun && !engine.isRunning() && !stoppedAtEnd.load()) engine.start();
    if (!wantRun && engine.isRunning()) engine.stop();
    const double jump = jumpRequest.exchange(-1.0);
    if (jump >= 0.0) engine.jumpToPosition(jump);

    float* l = buffer.getWritePointer(0);
    float* r = buffer.getWritePointer(1);
    engine.process(l, r, buffer.getNumSamples());
    if (engine.consumeStoppedAtEnd()) stoppedAtEnd.store(true);

    engineRunning.store(engine.isRunning());
    enginePosition.store(engine.position());
    enginePulsars.store(engine.pulsarCount());

    // Scope feed and recorder.
    const int n = buffer.getNumSamples();
    int s1, n1, s2, n2;
    scopeFifo.prepareToWrite(n, s1, n1, s2, n2);
    std::copy_n(l, n1, scopeL.data() + s1); std::copy_n(r, n1, scopeR.data() + s1);
    std::copy_n(l + n1, n2, scopeL.data() + s2); std::copy_n(r + n1, n2, scopeR.data() + s2);
    scopeFifo.finishedWrite(n1 + n2);

    const juce::SpinLock::ScopedTryLockType rec(recorderLock);
    if (rec.isLocked() && recorder != nullptr && engine.isRunning())
        recorder->write(buffer.getArrayOfReadPointers(), n);
}

// ---------------------------------------------------------------------------
void PulsarProcessor::timerCallback()
{
    // Tables ran out with Loop off: the original stopped the synth.
    if (stoppedAtEnd.load()) {
        setParam(params, "run", 0.0f); // first, so the audio thread cannot restart
        stopRecording();
        stoppedAtEnd.store(false);
    }
    if (morph.active())
        applySettingsToParams(morph.advance(1.0 / 30.0));
}

void PulsarProcessor::applySettingsToParams(const pulsar::Settings& s)
{
    const auto c = pulsar::Controls::fromSettings(s, readControls());
    for (int i = 0; i < 3; ++i) {
        setParam(params, ("train" + std::to_string(i + 1) + "On").c_str(), c.trainOn[(size_t) i] ? 1.0f : 0.0f);
        setParam(params, ("train" + std::to_string(i + 1) + "Level").c_str(), c.trainLevel[(size_t) i]);
    }
    setParam(params, "burstOn", c.burstOn ? 1.0f : 0.0f);
    setParam(params, "burstOns", (float) c.burstOns);
    setParam(params, "burstOffs", (float) c.burstOffs);
    setParam(params, "trainDur", c.trainDuration);
    setParam(params, "edge", c.edge);
}

pulsar::Fixes PulsarProcessor::fixes() const
{
    pulsar::Fixes f;
    for (int i = 0; i < pulsar::Fixes::kCount; ++i) f[i] = p.fixes[(size_t) i]->load() > 0.5f;
    return f;
}

void PulsarProcessor::setFixes(const pulsar::Fixes& f)
{
    for (int i = 0; i < pulsar::Fixes::kCount; ++i)
        setParam(params, (std::string("fix_") + pulsar::Fixes::id(i)).c_str(), f[i] ? 1.0f : 0.0f);
}

juce::String PulsarProcessor::modelName() const
{
    const auto f = fixes();
    if (f == pulsar::Fixes::model1()) return "Model 1 (original)";
    if (f == pulsar::Fixes::model2()) return "Model 2 (corrected)";
    return "Model 2 (custom fixes)";
}

void PulsarProcessor::captureSettings() { readControls().toSettings(current.settings); }

void PulsarProcessor::pushTables(bool crossfade)
{
    auto data = pulsar::TableData::from(current);
    const juce::SpinLock::ScopedLockType lock(pendingLock);
    pendingTables = std::move(data);
    pendingCrossfade = crossfade && isRunning();
    pendingFadeTime = value(params, "fadeTime");
}

void PulsarProcessor::tablesEdited() { pushTables(false); }

void PulsarProcessor::useTables(const pulsar::TableSet& set)
{
    previous = current;
    current = set;
    const bool linked = value(params, "linkSettings") > 0.5f;
    if (isRunning()) {
        pushTables(true);
        if (linked) {
            pulsar::Settings now = current.settings;
            readControls().toSettings(now);
            morph.begin(now, set.settings, value(params, "fadeTime"));
        }
    } else {
        pushTables(false);
        if (linked) applySettingsToParams(set.settings);
    }
    if (onTablesChanged) onTablesChanged();
}

void PulsarProcessor::undoTableSwitch() { useTables(pulsar::TableSet(previous)); }

void PulsarProcessor::randomizeControls()
{
    // PG.randomizeControls: a random state, morphed to while playing.
    const auto target = pulsar::randomSettings(rng);
    if (isRunning()) {
        pulsar::Settings now = current.settings;
        readControls().toSettings(now);
        morph.begin(now, target, value(params, "fadeTime"));
    } else {
        applySettingsToParams(target);
    }
}

bool PulsarProcessor::loadBank(const juce::File& file, juce::String& error)
{
    std::string err;
    const auto paths = pulsar::loadBank(file.getFullPathName().toStdString(), {}, &err);
    if (paths.empty()) { error = err; return false; }
    std::vector<pulsar::TableSet> sets;
    for (const auto& path : paths) {
        pulsar::TableSet s;
        if (pulsar::loadSet(path, s, &err)) sets.push_back(std::move(s));
    }
    if (sets.empty()) { error = "None of the bank's table sets could be read."; return false; }
    bank = std::move(sets);
    bankName = file.getFileNameWithoutExtension();
    selectBankSet(0);
    return true;
}

bool PulsarProcessor::saveBank(const juce::File& bankFile, juce::String& error)
{
    // Like the original: every set as a .set file next to the .bank file.
    std::vector<std::string> paths;
    const auto folder = bankFile.getParentDirectory();
    for (const auto& s : bank) {
        const auto f = folder.getChildFile(juce::File::createLegalFileName(s.name) + ".set");
        std::string err;
        if (!pulsar::saveSet(f.getFullPathName().toStdString(), s, &err)) { error = err; return false; }
        paths.push_back(f.getFullPathName().toStdString());
    }
    std::string err;
    if (!pulsar::saveBank(bankFile.getFullPathName().toStdString(), paths, &err)) { error = err; return false; }
    bankName = bankFile.getFileNameWithoutExtension();
    return true;
}

bool PulsarProcessor::loadSetFile(const juce::File& file, juce::String& error)
{
    pulsar::TableSet s;
    std::string err;
    if (!pulsar::loadSet(file.getFullPathName().toStdString(), s, &err)) { error = err; return false; }
    bank.push_back(s);
    selectBankSet((int) bank.size() - 1);
    return true;
}

bool PulsarProcessor::saveCurrentSet(const juce::File& file, juce::String& error)
{
    captureSettings();
    std::string err;
    if (!pulsar::saveSet(file.getFullPathName().toStdString(), current, &err)) { error = err; return false; }
    return true;
}

void PulsarProcessor::addCurrentToBank()
{
    captureSettings();
    pulsar::TableSet s = current;
    if (s.name.empty()) s.name = "Set " + std::to_string(bank.size() + 1);
    bank.push_back(s);
    selectedSet = (int) bank.size() - 1;
    if (onTablesChanged) onTablesChanged();
}

void PulsarProcessor::selectBankSet(int index)
{
    if (index < 0 || index >= (int) bank.size()) return;
    selectedSet = index;
    useTables(bank[(size_t) index]); // a copy becomes current, as in the original
}

bool PulsarProcessor::loadRingModSound(const juce::File& file, juce::String& error)
{
    juce::AudioFormatManager fm;
    fm.registerBasicFormats();
    std::unique_ptr<juce::AudioFormatReader> reader(fm.createReaderFor(file));
    if (reader == nullptr || reader->lengthInSamples <= 0) { error = "Cannot read " + file.getFileName(); return false; }
    juce::AudioBuffer<float> buf((int) reader->numChannels, (int) reader->lengthInSamples);
    reader->read(&buf, 0, buf.getNumSamples(), 0, true, true);
    auto sound = std::make_shared<pulsar::LoopSound>();
    sound->sampleRate = reader->sampleRate;
    sound->left.assign(buf.getReadPointer(0), buf.getReadPointer(0) + buf.getNumSamples());
    if (buf.getNumChannels() > 1)
        sound->right.assign(buf.getReadPointer(1), buf.getReadPointer(1) + buf.getNumSamples());
    const juce::SpinLock::ScopedLockType lock(pendingLock);
    pendingLoop = std::move(sound);
    pendingLoopSet = true;
    ringModName = file.getFileName();
    return true;
}

void PulsarProcessor::setRunning(bool run)
{
    stoppedAtEnd.store(false);
    setParam(params, "run", run ? 1.0f : 0.0f);
}

bool PulsarProcessor::startRecording(const juce::File& file, juce::String& error)
{
    stopRecording();
    file.deleteFile();
    std::unique_ptr<juce::OutputStream> stream(file.createOutputStream().release());
    if (stream == nullptr) { error = "Cannot write " + file.getFullPathName(); return false; }
    std::unique_ptr<juce::AudioFormat> format;
    if (file.hasFileExtension("wav")) format = std::make_unique<juce::WavAudioFormat>();
    else format = std::make_unique<juce::AiffAudioFormat>();
    // The original recorded 16-bit; 24-bit keeps the headroom of the 0..3 master volume.
    std::unique_ptr<juce::AudioFormatWriter> writer(format->createWriterFor(stream.get(), getSampleRate(), 2, 24, {}, 0));
    if (writer == nullptr) { error = "Cannot create a recording at this sample rate."; return false; }
    stream.release();
    auto threaded = std::make_unique<juce::AudioFormatWriter::ThreadedWriter>(writer.release(), writerThread, 32768);
    const juce::SpinLock::ScopedLockType lock(recorderLock);
    recorder = std::move(threaded);
    return true;
}

void PulsarProcessor::stopRecording()
{
    std::unique_ptr<juce::AudioFormatWriter::ThreadedWriter> old;
    {
        const juce::SpinLock::ScopedLockType lock(recorderLock);
        old = std::move(recorder);
    }
}

// ---------------------------------------------------------------------------
void PulsarProcessor::getStateInformation(juce::MemoryBlock& dest)
{
    captureSettings();
    auto state = params.copyState();
    juce::ValueTree tables("Tables");
    auto addSet = [](juce::ValueTree& parent, const pulsar::TableSet& s) {
        const auto bytes = pulsar::encodeSet(s);
        juce::ValueTree t("Set");
        t.setProperty("name", juce::String(s.name), nullptr);
        t.setProperty("data", juce::Base64::toBase64(bytes.data(), bytes.size()), nullptr);
        parent.appendChild(t, nullptr);
    };
    juce::ValueTree cur("Current");
    addSet(cur, current);
    tables.appendChild(cur, nullptr);
    juce::ValueTree bankTree("Bank");
    bankTree.setProperty("name", bankName, nullptr);
    bankTree.setProperty("selected", selectedSet, nullptr);
    for (const auto& s : bank) addSet(bankTree, s);
    tables.appendChild(bankTree, nullptr);
    state.appendChild(tables, nullptr);
    if (auto xml = state.createXml()) copyXmlToBinary(*xml, dest);
}

void PulsarProcessor::setStateInformation(const void* data, int size)
{
    auto xml = getXmlFromBinary(data, size);
    if (xml == nullptr) return;
    auto state = juce::ValueTree::fromXml(*xml);
    auto tables = state.getChildWithName("Tables");
    auto readSet = [](const juce::ValueTree& t, pulsar::TableSet& out) {
        juce::MemoryOutputStream mo;
        if (!juce::Base64::convertFromBase64(mo, t["data"].toString())) return false;
        out.name = t["name"].toString().toStdString();
        return pulsar::decodeSet((const unsigned char*) mo.getData(), mo.getDataSize(), out);
    };
    if (tables.isValid()) {
        auto cur = tables.getChildWithName("Current").getChild(0);
        pulsar::TableSet s;
        if (cur.isValid() && readSet(cur, s)) current = s;
        auto b = tables.getChildWithName("Bank");
        bank.clear();
        for (int i = 0; i < b.getNumChildren(); ++i) {
            pulsar::TableSet bs;
            if (readSet(b.getChild(i), bs)) bank.push_back(bs);
        }
        bankName = b["name"].toString();
        selectedSet = b.getProperty("selected", -1);
        state.removeChild(tables, nullptr);
    }
    params.replaceState(state);
    setParam(params, "run", 0.0f);
    pushTables(false);
    if (onTablesChanged) onTablesChanged();
}

juce::AudioProcessorEditor* PulsarProcessor::createEditor() { return new PulsarEditor(*this); }

juce::AudioProcessor* JUCE_CALLTYPE createPluginFilter() { return new PulsarProcessor(); }
