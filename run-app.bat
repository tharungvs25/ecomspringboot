@echo off
echo ========================================
echo    E-Commerce Spring Boot Application
echo ========================================
echo.
echo Starting the application...
echo This may take 30-60 seconds on first run.
echo.
echo Once started, open your browser and go to:
echo http://localhost:8081
echo.
echo Demo Login:
echo Username: admin
echo Password: admin123
echo.
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 21 and try again
    pause
    exit /b 1
)

REM Set JAVA_HOME to Java 21 if the batch file exists
if exist "set-java21.bat" (
    call set-java21.bat
    echo Using Java 21 from set-java21.bat
    echo.
)

REM Use Maven Wrapper to run the application
if exist "mvnw.cmd" (
    echo Using Maven Wrapper...
    call mvnw.cmd spring-boot:run
) else (
    echo Maven Wrapper not found, trying Maven directly...
    mvn spring-boot:run
)

echo.
echo Application stopped.
pause