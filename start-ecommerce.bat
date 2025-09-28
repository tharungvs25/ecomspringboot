@echo off
title Spring Boot E-Commerce Application

echo.
echo ===============================================
echo   🚀 Starting E-Commerce Application...
echo ===============================================
echo.
echo ⏳ Please wait while the application starts...
echo    This may take 30-60 seconds on first run.
echo.

REM Start the application in background
start /B cmd /c "./mvnw.cmd spring-boot:run"

echo ⏳ Waiting for application to start...
timeout /t 30 /nobreak >nul

echo.
echo ✅ Application should now be running!
echo.
echo 🌐 Open your browser and go to:
echo    👉 http://localhost:8081
echo.
echo 🔑 Demo Login Credentials:
echo    Username: admin
echo    Password: admin123
echo.
echo 📊 Database Console (H2):
echo    👉 http://localhost:8081/h2-console
echo    JDBC URL: jdbc:h2:mem:ecommerce
echo    Username: sa
echo    Password: (leave empty)
echo.
echo 📝 To stop the application:
echo    Press Ctrl+C in the terminal where it's running
echo.
echo ===============================================
echo   Happy Shopping! 🛒
echo ===============================================

REM Open browser automatically
start http://localhost:8081

pause