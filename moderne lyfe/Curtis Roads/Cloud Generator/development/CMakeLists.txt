cmake_minimum_required(VERSION 3.22)

project(CloudGenerator VERSION 2.0.0 LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_OSX_DEPLOYMENT_TARGET "11.0" CACHE STRING "Minimum macOS version")
set(CMAKE_OSX_ARCHITECTURES "arm64;x86_64" CACHE STRING "Universal binary")

if(MSVC)
    # Static CRT so the .exe runs without the VC++ redistributable.
    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
endif()

# ---------------------------------------------------------------------------
# Synthesis engine: plain C++, no JUCE, shared by the GUI app and the CLI.
add_library(cloudgen_engine STATIC
    src/engine/CloudEngine.cpp
    src/engine/Waveforms.cpp
)
target_include_directories(cloudgen_engine PUBLIC src/engine)

add_executable(cloudgen-cli src/cli/Main.cpp)
target_link_libraries(cloudgen-cli PRIVATE cloudgen_engine)

# ---------------------------------------------------------------------------
# GUI application (JUCE).
option(CLOUDGEN_BUILD_GUI "Build the JUCE GUI application" ON)

if(CLOUDGEN_BUILD_GUI)
    include(FetchContent)
    FetchContent_Declare(JUCE
        GIT_REPOSITORY https://github.com/juce-framework/JUCE.git
        GIT_TAG        8.0.4
        GIT_SHALLOW    ON)
    FetchContent_MakeAvailable(JUCE)

    juce_add_gui_app(CloudGenerator
        PRODUCT_NAME "Cloud Generator"
        COMPANY_NAME "Curtis Roads and John Alexander"
        BUNDLE_ID    "com.curtisroads.cloudgenerator"
        VERSION      ${PROJECT_VERSION})

    target_sources(CloudGenerator PRIVATE
        src/app/Main.cpp
        src/app/MainComponent.cpp)

    target_compile_definitions(CloudGenerator PRIVATE
        JUCE_WEB_BROWSER=0
        JUCE_USE_CURL=0
        JUCE_APPLICATION_NAME_STRING="$<TARGET_PROPERTY:CloudGenerator,JUCE_PRODUCT_NAME>"
        JUCE_APPLICATION_VERSION_STRING="$<TARGET_PROPERTY:CloudGenerator,JUCE_VERSION>")

    target_link_libraries(CloudGenerator
        PRIVATE
            cloudgen_engine
            juce::juce_gui_extra
            juce::juce_audio_utils
            juce::juce_audio_formats
        PUBLIC
            juce::juce_recommended_config_flags
            juce::juce_recommended_lto_flags
            juce::juce_recommended_warning_flags)
endif()
