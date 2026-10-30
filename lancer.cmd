@echo off
setlocal
cd /d "%~dp0"
call mvnw.cmd -o -DskipTests package
if errorlevel 1 exit /b 1
java -jar target\gestion-commandes-0.0.1-SNAPSHOT.jar %*
