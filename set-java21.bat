@echo off
echo Setting JAVA_HOME to JDK 21...
set JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.8.9-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
echo JAVA_HOME set to: %JAVA_HOME%
echo.
echo Current Java version:
java -version
echo.
echo You can now build your project with Java 21!
echo Use: mvn clean compile
pause