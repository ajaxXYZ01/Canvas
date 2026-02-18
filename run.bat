@echo off

cd "E:\Java projects\Canvas"

for /r src %%f in (*.java) do (javac -d bin -sourcepath src "%%f")

java -cp bin Frame