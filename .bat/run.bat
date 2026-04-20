@echo off

cd "E:\Java projects\Canvas"

@REM for /r src %%f in (*.java) do (javac -d bin -sourcepath src "%%f")

javac -d bin -sourcepath src src\app\Launcher.java

if %errorlevel% neq 0 (
    echo compilation failed.
    pause
    exit /b
)

java -cp bin app.Launcher
