#!/bin/bash
SCRIPT_DIR=$(cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR/..
rm -rf target/
mkdir target
javac --release 8 -d target/ src/com/superronjon/inputparse/*.java
jar -cf target/InputParser.jar target/com/superronjon/inputparse/*.class
mkdir -p out/
cp target/InputParser.jar out/InputParser.jar
