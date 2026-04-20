@echo off
setlocal

:: Check that java exists first
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is NOT installed or NOT in the PATH.
    pause
    exit /b 1
)

:: Capture the first line of 'java -version'
:: We use '2^>^&1' because java outputs version info to the Error stream
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set "J_VER=%%g"
    goto :parse
)

:parse
:: Remove quotes from the result
set "J_VER=%J_VER:"=%"

@REM echo [INFO] Detected Java Version: %J_VER%

:: Extract the major version number for comparison
:: Modern Java: "17.0.2" -> major is "17"
:: Legacy Java: "1.8.0_361" -> major is "8"
for /f "delims=." %%m in ("%J_VER%") do set "MAJOR=%%m"
if "%MAJOR%"=="1" (
    for /f "tokens=2 delims=." %%m in ("%J_VER%") do set "MAJOR=%%m"
)

@REM echo [INFO] Major Version: %MAJOR%

:: Version comparison using numeric check
if %MAJOR% GEQ 25 (
    java -jar target/qualitytool-1.0.jar %*
) else (
    echo [WARNING] Java %MAJOR% is outdated. Please upgrade to version 25.
)