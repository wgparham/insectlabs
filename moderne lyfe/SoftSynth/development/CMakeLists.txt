cmake_minimum_required(VERSION 3.22)

project(Softsynth VERSION 2.1.0 LANGUAGES CXX)

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
