@echo off
rem Builds refrender.exe next to this file. Needs MSYS2 mingw32 and Musashi in MacEmu.
set PATH=C:\msys64\mingw32\bin;%PATH%
cd /d "%~dp0"
set M=C:\Users\wgparham\MacEmu\musashi
g++ -O2 -std=c++17 -static -I"%M%" -I..\..\src\engine refrender.cpp ..\..\src\engine\Edit.cpp ..\..\src\engine\Engine.cpp ..\..\src\engine\Patch.cpp ..\..\src\engine\WaveTables.cpp "%M%\m68kcpu.o" "%M%\m68kdasm.o" "%M%\m68kops.o" "%M%\softfloat.o" -o refrender.exe
