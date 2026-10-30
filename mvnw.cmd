@echo off
setlocal
if exist "%~dp0.tools\maven\bin\mvn.cmd" goto bundled
where mvn >nul 2>&1
if errorlevel 1 (
    echo Maven est requis. Installez Maven puis relancez cette commande.
    exit /b 1
)
call mvn -f "%~dp0pom.xml" %*
exit /b %ERRORLEVEL%
:bundled
call "%~dp0.tools\maven\bin\mvn.cmd" -f "%~dp0pom.xml" %*
exit /b %ERRORLEVEL%
