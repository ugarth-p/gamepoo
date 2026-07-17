@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
set "JAVAFX_PATH="

for %%D in (
    "C:\javafx-sdk"
    "%USERPROFILE%\javafx-sdk"
    "%USERPROFILE%\Desktop\javafx-sdk"
    "%SCRIPT_DIR%javafx-sdk"
) do (
    for /d %%P in ("%%~D\*") do (
        if exist "%%~P\lib" (
            set "JAVAFX_PATH=%%~P\lib"
            goto :found
        )
    )
)

echo ERROR: JavaFX SDK not found.
echo Download from https://gluonhq.com/products/javafx/
echo Extract to C:\javafx-sdk\ or %USERPROFILE%\javafx-sdk\
pause
exit /b 1

:found
echo === MergedGame Windows Build ===

echo Cleaning build directory...
if exist "%SCRIPT_DIR%build" rmdir /s /q "%SCRIPT_DIR%build"
mkdir "%SCRIPT_DIR%build\classes"

echo Compiling...
javac --module-path "%JAVAFX_PATH%" ^
      --add-modules javafx.controls,javafx.graphics ^
      -d "%SCRIPT_DIR%build\classes" ^
      "%SCRIPT_DIR%src\application\*.java" ^
      "%SCRIPT_DIR%src\module-info.java"

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo Copying assets...
copy "%SCRIPT_DIR%src\*.gif" "%SCRIPT_DIR%build\classes\" >nul 2>&1
copy "%SCRIPT_DIR%src\*.jpg" "%SCRIPT_DIR%build\classes\" >nul 2>&1

echo Creating modular JAR...
jar --create --file "%SCRIPT_DIR%build\MergedGame.jar" ^
    --main-class application.Main ^
    -C "%SCRIPT_DIR%build\classes" .

if %errorlevel% neq 0 (
    echo JAR creation failed.
    pause
    exit /b 1
)

echo Creating app image with jpackage...
jpackage --input "%SCRIPT_DIR%build" ^
         --module-path "%JAVAFX_PATH%" ^
         --module Game/application.Main ^
         --name MergedGame ^
         --type app-image ^
         --dest "%SCRIPT_DIR%build\output" ^
         --force

if %errorlevel% neq 0 (
    echo jpackage failed.
    pause
    exit /b 1
)

echo.
echo === Build complete ===
echo App image: %SCRIPT_DIR%build\output\MergedGame\
echo Run with: build\output\MergedGame\bin\MergedGame.exe
pause
