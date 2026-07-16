#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAVAFX_PATH="$HOME/javafx-sdk/javafx-sdk-25.0.3/lib"

if [ ! -d "$JAVAFX_PATH" ]; then
    echo "ERROR: JavaFX SDK not found at $JAVAFX_PATH"
    echo "Download from https://gluonhq.com/products/javafx/ and extract to ~/javafx-sdk/"
    exit 1
fi

echo "=== MergedGame Linux Build ==="

echo "Cleaning build directory..."
rm -rf "$SCRIPT_DIR/build"
mkdir -p "$SCRIPT_DIR/build/classes"

echo "Compiling..."
javac --module-path "$JAVAFX_PATH" \
      --add-modules javafx.controls,javafx.graphics \
      -d "$SCRIPT_DIR/build/classes" \
      $(find "$SCRIPT_DIR/src" -name "*.java")

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Copying assets..."
cp -r "$SCRIPT_DIR/src/main/resources"/* "$SCRIPT_DIR/build/classes/"

echo "Creating modular JAR..."
jar --create --file "$SCRIPT_DIR/build/MergedGame.jar" \
    --main-class com.gamepoo.launcher.GameLauncher \
    -C "$SCRIPT_DIR/build/classes" .

if [ $? -ne 0 ]; then
    echo "JAR creation failed."
    exit 1
fi

echo "Creating app image with jpackage..."
jpackage --input "$SCRIPT_DIR/build" \
         --module-path "$JAVAFX_PATH" \
         --module Game/com.gamepoo.launcher.GameLauncher \
         --name MergedGame \
         --type app-image \
         --dest "$SCRIPT_DIR/build/output" \
         --force

if [ $? -ne 0 ]; then
    echo "jpackage failed."
    exit 1
fi

echo ""
echo "=== Build complete ==="
echo "App image: $SCRIPT_DIR/build/output/MergedGame/"
echo "Run with: ./build/output/MergedGame/bin/MergedGame"
