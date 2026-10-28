cmake_minimum_required(VERSION 3.22)

project(PulsarGenerator VERSION 2.0.0 LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_OSX_DEPLOYMENT_TARGET "11.0" CACHE STRING "Minimum macOS version")
set(CMAKE_OSX_ARCHITECTURES "arm64;x86_64" CACHE STRING "Universal binary")

if(MSVC)
    # Static CRT so the binaries run without the VC++ redistributable.
    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
endif()

# ---------------------------------------------------------------------------
# Synthesis engine and file formats: plain C++, no JUCE.
add_library(pulsargen_engine STATIC
    src/engine/PulsarEngine.cpp
    src/engine/Tables.cpp
    src/engine/TableOps.cpp
)
target_include_directories(pulsargen_engine PUBLIC src/engine)
set_target_properties(pulsargen_engine PROPERTIES POSITION_INDEPENDENT_CODE ON)

add_executable(pulsargen-cli src/cli/Main.cpp)
target_link_libraries(pulsargen-cli PRIVATE pulsargen_engine)

# ---------------------------------------------------------------------------
# Standalone application and plugins (JUCE).
option(PULSARGEN_BUILD_PLUGIN "Build the JUCE standalone app and plugins" ON)

if(PULSARGEN_BUILD_PLUGIN)
    include(FetchContent)
    FetchContent_Declare(JUCE
        GIT_REPOSITORY https://github.com/juce-framework/JUCE.git
        GIT_TAG        8.0.4
        GIT_SHALLOW    ON)
    FetchContent_MakeAvailable(JUCE)

    set(PULSARGEN_FORMATS Standalone VST3)
    if(APPLE)
        list(APPEND PULSARGEN_FORMATS AU)
    elseif(UNIX)
        list(APPEND PULSARGEN_FORMATS LV2)
    endif()

    juce_add_plugin(PulsarGenerator
        PRODUCT_NAME "Pulsar Generator"
        COMPANY_NAME "Curtis Roads and Alberto de Campo"
        BUNDLE_ID "com.curtisroads.pulsargenerator"
        VERSION ${PROJECT_VERSION}
        FORMATS ${PULSARGEN_FORMATS}
        IS_SYNTH TRUE
        NEEDS_MIDI_INPUT FALSE
        NEEDS_MIDI_OUTPUT FALSE
        IS_MIDI_EFFECT FALSE
        EDITOR_WANTS_KEYBOARD_FOCUS TRUE
        PLUGIN_MANUFACTURER_CODE Rdcp
        PLUGIN_CODE Plsg
        VST3_CATEGORIES Instrument Synth
        AU_MAIN_TYPE kAudioUnitType_MusicDevice
        LV2URI "https://github.com/wgparham/insectlabs/pulsar-generator"
        COPY_PLUGIN_AFTER_BUILD FALSE)

    target_sources(PulsarGenerator PRIVATE
        src/plugin/PluginProcessor.cpp
        src/plugin/PluginEditor.cpp
        src/plugin/TableView.cpp)

    target_compile_definitions(PulsarGenerator PUBLIC
        JUCE_WEB_BROWSER=0
        JUCE_USE_CURL=0
        JUCE_VST3_CAN_REPLACE_VST2=0
        JUCE_DISPLAY_SPLASH_SCREEN=0)

    target_link_libraries(PulsarGenerator
        PRIVATE
            pulsargen_engine
            juce::juce_audio_utils
            juce::juce_audio_formats
            juce::juce_gui_extra
        PUBLIC
            juce::juce_recommended_config_flags
            juce::juce_recommended_lto_flags
            juce::juce_recommended_warning_flags)
endif()
