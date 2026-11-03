cmake_minimum_required(VERSION 3.22)

project(Softsynth VERSION 2.1.0 LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Synthesis engine and file format: plain C++, no JUCE.
add_library(softsynth_engine STATIC
    src/engine/Patch.cpp
    src/engine/Engine.cpp
    src/engine/WaveTables.cpp
    src/engine/Edit.cpp
)
target_include_directories(softsynth_engine PUBLIC src/engine)

add_executable(softsynth-cli src/cli/Main.cpp)
target_link_libraries(softsynth-cli PRIVATE softsynth_engine)

enable_testing()
add_executable(test_engine tests/test_engine.cpp)
target_link_libraries(test_engine PRIVATE softsynth_engine)
add_test(NAME reference_renders
         COMMAND test_engine ${CMAKE_CURRENT_SOURCE_DIR}/../TBWERD1.PRM ${CMAKE_CURRENT_SOURCE_DIR}/tests/reference)
add_executable(test_edit tests/test_edit.cpp)
target_link_libraries(test_edit PRIVATE softsynth_engine)
add_test(NAME edit_references
         COMMAND test_edit ${CMAKE_CURRENT_SOURCE_DIR}/../TBWERD1.PRM ${CMAKE_CURRENT_SOURCE_DIR}/tests/reference/edits.txt)

# ---------------------------------------------------------------------------
# Model 1 application (JUCE). Set SOFTSYNTH_BUILD_GUI=OFF to build the engine and tests only.
option(SOFTSYNTH_BUILD_GUI "Build the JUCE application" ON)

if(SOFTSYNTH_BUILD_GUI)
    include(FetchContent)
    FetchContent_Declare(JUCE
        GIT_REPOSITORY https://github.com/juce-framework/JUCE.git
        GIT_TAG        8.0.4
        GIT_SHALLOW    ON)
    FetchContent_MakeAvailable(JUCE)

    juce_add_gui_app(Softsynth
        PRODUCT_NAME "Softsynth"
        COMPANY_NAME "Digidesign, adapted by Rainer Brodersen"
        VERSION      ${PROJECT_VERSION})

    target_sources(Softsynth PRIVATE
        src/app/Main.cpp
        src/app/MainComponent.cpp
        src/app/BreakPointGraph.cpp
        src/app/ClassicLook.cpp
        src/app/ClassicIcons.cpp)

    target_compile_definitions(Softsynth PRIVATE
        JUCE_WEB_BROWSER=0
        JUCE_USE_CURL=0
        JUCE_APPLICATION_NAME_STRING="$<TARGET_PROPERTY:Softsynth,JUCE_PRODUCT_NAME>"
        JUCE_APPLICATION_VERSION_STRING="$<TARGET_PROPERTY:Softsynth,JUCE_VERSION>")

    target_link_libraries(Softsynth
        PRIVATE
            softsynth_engine
            juce::juce_gui_basics
            juce::juce_gui_extra
        PUBLIC
            juce::juce_recommended_config_flags
            juce::juce_recommended_warning_flags)
endif()
