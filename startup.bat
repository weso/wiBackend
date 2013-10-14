rem @echo off
set COMPUTEX=computex
set COMPUTEX_REPO=https://github.com/weso/computex.git
set COMPUTEX_BRANCH=web
set FUSEKI_DIR=fuseki
set PLAY_DIR=wiLodPortal 

rem Fetch the latest computex repository:
if EXIST %COMPUTEX% GOTO :updateComputex
cd %COMPUTEX% && git clone %COMPUTEX_REPO% && cd ..
:updateComputex
cd %COMPUTEX% && git checkout %COMPUTEX_BRANCH% && cd ..
cd %COMPUTEX% && git pull origin %COMPUTEX_BRANCH% && cd ..

rem Starts Fuseki:
start cmd.exe /K "cd %FUSEKI_DIR% && fuseki-server.bat --config=ConfigFile.ttl"

rem Starts Memchahed
rem memcached

rem Starts Play Project
start cmd.exe /K "cd %PLAY_DIR% && play ~run "
