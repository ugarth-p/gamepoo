@echo off
setlocal

set "SCRIPT_DIR=%~dp0.."
pushd "%SCRIPT_DIR%"
set "SCRIPT_DIR=%CD%"
popd
call "%SCRIPT_DIR%setup_env.bat"
if %errorlevel% neq 0 exit /b 1

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
