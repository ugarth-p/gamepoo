#!/bin/bash

# setup_env.sh — Auto-installs Java 25 and JavaFX 25.0.3 if not present.
# Sourced by build_lin.sh and run_lin.sh.

JAVA_VERSION="25.0.3"
JAVA_BUILD="9"
JAVAFX_VERSION="25.0.3"

INSTALL_DIR="$HOME"

# --- Detect architecture ---
ARCH=$(uname -m)
case "$ARCH" in
    x86_64)  PLATFORM_ARCH="x64" ;;
    aarch64) PLATFORM_ARCH="aarch64" ;;
    *)
        echo "ERROR: Unsupported architecture: $ARCH"
        exit 1
        ;;
esac

# --- Find or install JDK 25 ---
find_java() {
    # Check if java is available and is version 25
    if command -v java &>/dev/null; then
        local ver
        ver=$(java -version 2>&1 | head -1 | sed 's/.*"\([0-9]*\).*/\1/')
        if [ "$ver" = "25" ]; then
            JAVA_HOME=$(java -XshowSettings:property 2>&1 | grep 'java.home' | awk '{print $3}')
            if [ -z "$JAVA_HOME" ]; then
                # Fallback: derive from which java
                JAVA_HOME=$(dirname "$(dirname "$(readlink -f "$(which java)")")")
            fi
            return 0
        fi
    fi
    return 1
}

install_java() {
    echo "Java 25 not found. Downloading Eclipse Temurin JDK ${JAVA_VERSION}+${JAVA_BUILD}..."
    local url="https://github.com/adoptium/temurin${JAVA_VERSION%%.*}-binaries/releases/download/jdk-${JAVA_VERSION}%2B${JAVA_BUILD}/OpenJDK${JAVA_VERSION%%.*}U-jdk_${PLATFORM_ARCH}_linux_hotspot_${JAVA_VERSION}_${JAVA_BUILD}.tar.gz"
    local tmp_file="$INSTALL_DIR/jdk_download.tar.gz"

    if ! curl -fSL -o "$tmp_file" "$url"; then
        echo "ERROR: Failed to download JDK from $url"
        exit 1
    fi

    echo "Extracting JDK..."
    tar -xzf "$tmp_file" -C "$INSTALL_DIR"
    rm -f "$tmp_file"

    # Find the extracted directory (e.g. jdk-25.0.3+9)
    local extracted_dir
    extracted_dir=$(find "$INSTALL_DIR" -maxdepth 1 -type d -name "jdk-*" | head -1)
    if [ -z "$extracted_dir" ]; then
        echo "ERROR: JDK extraction failed — directory not found."
        exit 1
    fi

    JAVA_HOME="$extracted_dir"
    echo "JDK installed to $JAVA_HOME"
}

if ! find_java; then
    install_java
fi

export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

echo "Using Java: $(java -version 2>&1 | head -1)"

# --- Find or install JavaFX SDK ---
JAVAFX_PATH="$INSTALL_DIR/javafx-sdk/javafx-sdk-${JAVAFX_VERSION}/lib"

find_javafx() {
    # Check exact version first
    if [ -d "$JAVAFX_PATH" ]; then
        return 0
    fi
    # Check any version in ~/javafx-sdk/
    if [ -d "$INSTALL_DIR/javafx-sdk" ]; then
        local found
        found=$(find "$INSTALL_DIR/javafx-sdk" -maxdepth 2 -type d -name "lib" | head -1)
        if [ -n "$found" ]; then
            JAVAFX_PATH="$found"
            return 0
        fi
    fi
    return 1
}

install_javafx() {
    echo "JavaFX SDK not found. Downloading JavaFX ${JAVAFX_VERSION}..."
    local url="https://download2.gluonhq.com/openjfx/${JAVAFX_VERSION}/openjfx-${JAVAFX_VERSION}_linux-${PLATFORM_ARCH}_bin-sdk.zip"
    local tmp_file="$INSTALL_DIR/javafx_download.zip"

    if ! curl -fSL -o "$tmp_file" "$url"; then
        echo "ERROR: Failed to download JavaFX from $url"
        exit 1
    fi

    echo "Extracting JavaFX SDK..."
    mkdir -p "$INSTALL_DIR/javafx-sdk"
    if command -v unzip &>/dev/null; then
        unzip -qo "$tmp_file" -d "$INSTALL_DIR/javafx-sdk"
    elif command -v jar &>/dev/null; then
        local tmp_extract="$INSTALL_DIR/javafx_tmp_extract"
        mkdir -p "$tmp_extract"
        (cd "$tmp_extract" && jar -xf "$tmp_file")
        mv "$tmp_extract/javafx-sdk-${JAVAFX_VERSION}" "$INSTALL_DIR/javafx-sdk/" 2>/dev/null || true
        rm -rf "$tmp_extract"
    else
        echo "ERROR: Neither 'unzip' nor 'jar' found. Install unzip and retry."
        rm -f "$tmp_file"
        exit 1
    fi
    rm -f "$tmp_file"

    JAVAFX_PATH="$INSTALL_DIR/javafx-sdk/javafx-sdk-${JAVAFX_VERSION}/lib"
    if [ ! -d "$JAVAFX_PATH" ]; then
        echo "ERROR: JavaFX extraction failed — lib directory not found."
        exit 1
    fi
    echo "JavaFX SDK installed to $JAVAFX_PATH"
}

if ! find_javafx; then
    install_javafx
fi

echo "Using JavaFX: $JAVAFX_PATH"
