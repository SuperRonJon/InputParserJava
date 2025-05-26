#!/bin/bash
SCRIPT_DIR=$(cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR/..
rm -rf target/
rm -rf out/InputParser.jar
mkdir target
javac --release 8 -d target/ src/com/superronjon/inputparse/*.java
cd target
jar -cf InputParser.jar com/superronjon/inputparse/*.class
mkdir -p ../out/
cp InputParser.jar ../out/InputParser.jar
