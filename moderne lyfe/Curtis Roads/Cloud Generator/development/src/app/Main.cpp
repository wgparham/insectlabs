#include "MainComponent.h"

#include <juce_gui_extra/juce_gui_extra.h>

class CloudGeneratorApplication : public juce::JUCEApplication
{
public:
    const juce::String getApplicationName() override { return JUCE_APPLICATION_NAME_STRING; }
    const juce::String getApplicationVersion() override { return JUCE_APPLICATION_VERSION_STRING; }
    bool moreThanOneInstanceAllowed() override { return true; }

    void initialise(const juce::String& commandLine) override
    {
        // CloudGenerator [--snapshot image.png [--dark]]: paint the console with a
        // generated cloud into an image off screen, then quit (documentation and tests).
        auto args = juce::StringArray::fromTokens(commandLine, true);
        for (auto& a : args) a = a.unquoted();
        juce::File snapshot;
        bool dark = false;
        for (int i = 0; i < args.size(); ++i) {
            if (args[i] == "--snapshot" && i + 1 < args.size()) snapshot = juce::File(args[++i]);
            else if (args[i] == "--dark") dark = true;
        }
        if (snapshot != juce::File()) {
            MainComponent main(dark ? 1 : 0);
            main.setBounds(0, 0, main.getWidth(), main.getHeight());
            main.generateNow();
            const auto img = main.createComponentSnapshot(main.getLocalBounds(), true, 1.0f);
            juce::FileOutputStream out(snapshot);
            out.setPosition(0);
            out.truncate();
            juce::PNGImageFormat().writeImageToStream(img, out);
            quit();
            return;
        }
        mainWindow = std::make_unique<MainWindow>(getApplicationName());
    }
    void shutdown() override { mainWindow = nullptr; }
    void systemRequestedQuit() override { quit(); }

private:
    class MainWindow : public juce::DocumentWindow
    {
    public:
        explicit MainWindow(const juce::String& name)
            : DocumentWindow(name, classic::theme().paper, DocumentWindow::allButtons)
        {
            setUsingNativeTitleBar(true);
            setContentOwned(new MainComponent(), true);
            setResizable(true, true);
            setResizeLimits(900, 600, 4000, 3000);
            centreWithSize(getWidth(), getHeight());
            setVisible(true);
        }

        void closeButtonPressed() override { JUCEApplication::getInstance()->systemRequestedQuit(); }
    };

    std::unique_ptr<MainWindow> mainWindow;
};

START_JUCE_APPLICATION(CloudGeneratorApplication)
