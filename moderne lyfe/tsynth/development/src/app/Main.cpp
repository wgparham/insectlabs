#include "MainComponent.h"

class TurboSynthApp : public juce::JUCEApplication
{
public:
    const juce::String getApplicationName() override { return JUCE_APPLICATION_NAME_STRING; }
    const juce::String getApplicationVersion() override { return JUCE_APPLICATION_VERSION_STRING; }
    bool moreThanOneInstanceAllowed() override { return true; }

    void initialise(const juce::String& commandLine) override
    {
        // TurboSynth [patch] [--snapshot image.png [--dark]]
        auto args = juce::StringArray::fromTokens(commandLine, true);
        for (auto& a : args) a = a.unquoted();
        juce::File patch, snapshot;
        bool dark = false;
        int select = 0;
        for (int i = 0; i < args.size(); ++i) {
            if (args[i] == "--snapshot" && i + 1 < args.size()) snapshot = juce::File(args[++i]);
            else if (args[i] == "--dark") dark = true;
            else if (args[i] == "--select" && i + 1 < args.size()) select = args[++i].getIntValue();
            else if (args[i].isNotEmpty()) patch = juce::File(args[i]);
        }
        if (snapshot != juce::File()) {
            // Paint the main component into an image off screen, then quit (for documentation and tests).
            MainComponent main(dark ? 1 : 0);
            if (patch != juce::File()) main.openFile(patch);
            if (select > 0) main.selectModule(select);
            main.setBounds(0, 0, 1180, 780);
            const auto img = main.createComponentSnapshot(main.getLocalBounds(), true, 1.0f);
            juce::FileOutputStream out(snapshot);
            out.setPosition(0); out.truncate();
            juce::PNGImageFormat().writeImageToStream(img, out);
            quit();
            return;
        }
        window = std::make_unique<Window>(getApplicationName());
        if (patch != juce::File()) window->main->openFile(patch);
    }
    void shutdown() override { window = nullptr; }
    void systemRequestedQuit() override { quit(); }
    void anotherInstanceStarted(const juce::String&) override {}

private:
    struct Window : juce::DocumentWindow {
        explicit Window(const juce::String& name)
            : DocumentWindow(name, juce::Colours::black, DocumentWindow::allButtons)
        {
            setUsingNativeTitleBar(true);
            main = new MainComponent();
            setContentOwned(main, true);
            setResizable(true, true);
            centreWithSize(getWidth(), getHeight());
            setVisible(true);
        }
        void closeButtonPressed() override { JUCEApplication::getInstance()->systemRequestedQuit(); }
        MainComponent* main = nullptr;
    };
    std::unique_ptr<Window> window;
};

START_JUCE_APPLICATION(TurboSynthApp)
