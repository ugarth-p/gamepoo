#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
source "$SCRIPT_DIR/setup_env.sh"

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
cp "$SCRIPT_DIR/src"/*.jpg "$SCRIPT_DIR/src"/*.gif "$SCRIPT_DIR/bin/" 2>/dev/null

echo "Launching MergedGame..."
java --module-path "$JAVAFX_PATH:$SCRIPT_DIR/bin" \
     --add-modules javafx.controls,javafx.graphics \
     -m Game/application.Main
