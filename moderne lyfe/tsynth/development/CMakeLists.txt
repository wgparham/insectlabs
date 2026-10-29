cmake_minimum_required(VERSION 3.22)

project(TurboSynth VERSION 2.22.0 LANGUAGES C CXX)

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
add_library(tsynth_engine STATIC
    src/engine/Patch.cpp
    src/engine/Engine.cpp
    src/engine/SineTable.cpp
    src/engine/Presets.cpp
    src/engine/SampleEdit.cpp
    src/engine/Rules.cpp
)
target_include_directories(tsynth_engine PUBLIC src/engine)

add_executable(tsynth-cli src/cli/Main.cpp)
target_link_libraries(tsynth-cli PRIVATE tsynth_engine)

if(MSVC)
    target_compile_options(tsynth_engine PRIVATE /W4)
    target_compile_options(tsynth-cli PRIVATE /W4)
    target_compile_definitions(tsynth-cli PRIVATE _CRT_SECURE_NO_WARNINGS NOMINMAX WIN32_LEAN_AND_MEAN)
    target_link_libraries(tsynth-cli PRIVATE shell32)
else()
    target_compile_options(tsynth_engine PRIVATE -Wall -Wextra)
    target_compile_options(tsynth-cli PRIVATE -Wall -Wextra)
endif()

# ---------------------------------------------------------------------------
# Standalone application (JUCE).
option(TSYNTH_BUILD_APP "Build the JUCE standalone app" ON)
if(TSYNTH_BUILD_APP AND EXISTS ${CMAKE_CURRENT_SOURCE_DIR}/src/app/Main.cpp)
    include(FetchContent)
    FetchContent_Declare(JUCE
        GIT_REPOSITORY https://github.com/juce-framework/JUCE.git
        GIT_TAG        8.0.4
        GIT_SHALLOW    ON)
    FetchContent_MakeAvailable(JUCE)
    add_subdirectory(src/app)
endif()
