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
if not exist "%SCRIPT_DIR%bin" mkdir "%SCRIPT_DIR%bin"

echo Compiling...
javac --module-path "%JAVAFX_PATH%" ^
      --add-modules javafx.controls,javafx.graphics ^
      -d "%SCRIPT_DIR%bin" ^
      "%SCRIPT_DIR%src\application\*.java"

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo Copying assets...
copy "%SCRIPT_DIR%src\*.jpg" "%SCRIPT_DIR%bin\" >nul 2>&1
copy "%SCRIPT_DIR%src\*.gif" "%SCRIPT_DIR%bin\" >nul 2>&1

echo Launching MergedGame...
java --module-path "%JAVAFX_PATH%;%SCRIPT_DIR%bin" ^
     --add-modules javafx.controls,javafx.graphics ^
     -m Game/application.Main
