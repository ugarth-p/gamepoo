@echo off
rem setup_env.bat — Auto-installs Java 25 and JavaFX 25.0.3 if not present.
rem Called by build_win.bat and run_win.bat.
rem Sets: JAVA_HOME, JAVAFX_PATH, PATH (persists to caller).

set "JAVA_VERSION=25.0.3"
set "JAVA_BUILD=9"
set "JAVAFX_VERSION=25.0.3"
set "INSTALL_DIR=%USERPROFILE%"

rem --- Detect architecture ---
set "PLATFORM_ARCH=x64"
if "%PROCESSOR_ARCHITECTURE%"=="ARM64" set "PLATFORM_ARCH=aarch64"

rem --- Find or install JDK 25 ---
call :find_java
if %errorlevel% neq 0 call :install_java

set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Using Java:
java -version 2>&1 | findstr /i "version"

rem --- Find or install JavaFX SDK ---
set "JAVAFX_PATH=%INSTALL_DIR%\javafx-sdk\javafx-sdk-%JAVAFX_VERSION%\lib"
call :find_javafx
if %errorlevel% neq 0 call :install_javafx

echo Using JavaFX: %JAVAFX_PATH%
goto :eof

rem ============================================================
rem FUNCTIONS
rem ============================================================

:find_java
    java -version >nul 2>&1
    if %errorlevel% neq 0 exit /b 1

    for /f "tokens=3" %%v in ('java -version 2^>^&1 ^| findstr /i "version"') do set "DETECTED_VER=%%~v"
    set "DETECTED_VER=%DETECTED_VER:~0,2%"
    if not "%DETECTED_VER%"=="25" exit /b 1

    rem Try to get JAVA_HOME from java settings
    for /f "tokens=2 delims==" %%p in ('java -XshowSettings:property 2^>^&1 ^| findstr /i "java.home"') do set "JAVA_HOME=%%p"
    if not "%JAVA_HOME%"=="" exit /b 0

    rem Fallback: derive from where java
    for /f "tokens=*" %%j in ('where java') do set "JAVA_HOME=%%~dpj.."
    exit /b 0

:install_java
    echo Java 25 not found. Downloading Eclipse Temurin JDK %JAVA_VERSION%+%JAVA_BUILD%...
    set "URL=https://github.com/adoptium/temurin25-binaries/releases/download/jdk-%JAVA_VERSION%%%2B%JAVA_BUILD%/OpenJDK25U-jdk_%PLATFORM_ARCH%_windows_hotspot_%JAVA_VERSION%_%JAVA_BUILD%.zip"
    set "TMP_FILE=%INSTALL_DIR%\jdk_download.zip"

    curl -fSL -o "%TMP_FILE%" "%URL%"
    if %errorlevel% neq 0 (
        echo ERROR: Failed to download JDK.
        exit /b 1
    )

    echo Extracting JDK...
    tar -xf "%TMP_FILE%" -C "%INSTALL_DIR%"
    del "%TMP_FILE%" 2>nul

    rem Find extracted directory
    set "JAVA_HOME="
    for /d %%d in ("%INSTALL_DIR%\jdk-*") do set "JAVA_HOME=%%d"
    if "%JAVA_HOME%"=="" (
        echo ERROR: JDK extraction failed.
        exit /b 1
    )
    echo JDK installed to %JAVA_HOME%
    exit /b 0

:find_javafx
    if exist "%JAVAFX_PATH%" exit /b 0
    rem Check any version
    for /d %%v in ("%INSTALL_DIR%\javafx-sdk\javafx-sdk-*") do (
        if exist "%%v\lib" (
            set "JAVAFX_PATH=%%v\lib"
            exit /b 0
        )
    )
    exit /b 1

:install_javafx
    echo JavaFX SDK not found. Downloading JavaFX %JAVAFX_VERSION%...
    set "URL=https://download2.gluonhq.com/openjfx/%JAVAFX_VERSION%/openjfx-%JAVAFX_VERSION%_windows-%PLATFORM_ARCH%_bin-sdk.zip"
    set "TMP_FILE=%INSTALL_DIR%\javafx_download.zip"

    curl -fSL -o "%TMP_FILE%" "%URL%"
    if %errorlevel% neq 0 (
        echo ERROR: Failed to download JavaFX.
        exit /b 1
    )

    echo Extracting JavaFX SDK...
    if not exist "%INSTALL_DIR%\javafx-sdk" mkdir "%INSTALL_DIR%\javafx-sdk"
    tar -xf "%TMP_FILE%" -C "%INSTALL_DIR%\javafx-sdk"
    del "%TMP_FILE%" 2>nul

    set "JAVAFX_PATH=%INSTALL_DIR%\javafx-sdk\javafx-sdk-%JAVAFX_VERSION%\lib"
    if not exist "%JAVAFX_PATH%" (
        echo ERROR: JavaFX extraction failed.
        exit /b 1
    )
    echo JavaFX SDK installed to %JAVAFX_PATH%
    exit /b 0
