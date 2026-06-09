#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

echo "Compiling..."
mkdir -p target/classes
find src/main/java -name "*.java" > sources.txt
javac -d target/classes -encoding UTF-8 @sources.txt
rm sources.txt

echo "Running..."
java -cp target/classes com.restaurant.Main
