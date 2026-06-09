@echo off
cd /d "%~dp0"

echo Compiling...
if not exist target\classes mkdir target\classes
dir /s /b src\main\java\*.java > sources.txt
javac -d target\classes -encoding UTF-8 @sources.txt
del sources.txt

echo Running...
java -cp target\classes com.restaurant.Main
