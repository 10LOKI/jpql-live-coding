@echo off
setlocal
cd /d "%~dp0"
call mvnw.cmd -o test
exit /b %ERRORLEVEL%
