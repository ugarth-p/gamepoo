#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAVAFX_PATH="$HOME/javafx-sdk/javafx-sdk-25.0.3/lib"

if [ ! -d "$JAVAFX_PATH" ]; then
    echo "ERROR: JavaFX SDK not found at $JAVAFX_PATH"
    echo "Download it from https://gluonhq.com/products/javafx/ and extract to ~/javafx-sdk/"
    exit 1
fi

mkdir -p "$SCRIPT_DIR/bin"

echo "Compiling..."
javac --module-path "$JAVAFX_PATH" \
      --add-modules javafx.controls,javafx.graphics \
      -d "$SCRIPT_DIR/bin" \
      $(find "$SCRIPT_DIR/src" -name "*.java")

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Copying assets..."
cp -r "$SCRIPT_DIR/src/main/resources"/* "$SCRIPT_DIR/bin/"

echo "Launching MergedGame..."
java --module-path "$JAVAFX_PATH:$SCRIPT_DIR/bin" \
     --add-modules javafx.controls,javafx.graphics \
     -m Game/com.gamepoo.launcher.GameLauncher
