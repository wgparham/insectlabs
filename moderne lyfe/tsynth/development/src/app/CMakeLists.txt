juce_add_gui_app(TurboSynth
    PRODUCT_NAME "TurboSynth"
    COMPANY_NAME "insectlabs"
    BUNDLE_ID "com.insectlabs.turbosynth"
    VERSION ${PROJECT_VERSION})

target_sources(TurboSynth PRIVATE
    Main.cpp
    MainComponent.cpp
    PatchView.cpp
    ModulePanel.cpp
    WaveView.cpp
    ClassicLook.cpp
    ClassicIcons.cpp)

target_compile_definitions(TurboSynth PRIVATE
    JUCE_WEB_BROWSER=0
    JUCE_USE_CURL=0
    JUCE_APPLICATION_NAME_STRING="$<TARGET_PROPERTY:TurboSynth,JUCE_PRODUCT_NAME>"
    JUCE_APPLICATION_VERSION_STRING="$<TARGET_PROPERTY:TurboSynth,JUCE_VERSION>")

target_link_libraries(TurboSynth PRIVATE
    tsynth_engine
    juce::juce_gui_extra
    juce::juce_audio_utils
    juce::juce_recommended_config_flags
    juce::juce_recommended_lto_flags
    juce::juce_recommended_warning_flags)
