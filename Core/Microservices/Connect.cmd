@echo off

set me=%nxI
rem set interactive=0

rem echo %CMDCMDLINE% | find /I "/c" >NUL 2>&1
rem if %ERRORLEVEL% == 0 set interactive=1

if "%1" equ "" (
	echo [me] The port of the running app is not specified as a command line argument and the default 8080 is used.
	set port=8443
) else (
	set port=%1 
)

where curl >nul 2>nul
if %errorlevel% neq 0 (
	echo [me] Curl is not installed or not set in the PATH environment variable
	pause
	exit /b 0
	)

set ThermostatPort=8082
set LuminositySensorPort=8083
set MotionDetectorPort=8084
set SmokeSensorPort=8085
set LeakSensorPort=8086
set PatientServicePort=8087
set AlarmPort=8088
set EvacArrow1Port=8089
set EvacArrow2Port=8090
set EvacArrow3Port=8091

curl localhost:%ThermostatPort%/Connect/127.0.0.1/%port%
echo.
curl localhost:%LuminositySensorPort%/Connect/127.0.0.1/%port%
echo.
curl localhost:%MotionDetectorPort%/Connect/127.0.0.1/%port%
echo.
curl -k https://localhost:%SmokeSensorPort%/Connect/127.0.0.1/%port%
echo.
curl localhost:%LeakSensorPort%/Connect/127.0.0.1/%port%
echo.
curl localhost:%PatientServicePort%/Connect/127.0.0.1/%port%
echo.
curl localhost:%AlarmPort%/Connect/127.0.0.1/%port%
echo.
curl localhost:%EvacArrow1Port%/Connect/127.0.0.1/%port%
echo.
curl localhost:%EvacArrow2Port%/Connect/127.0.0.1/%port%
echo.
curl localhost:%EvacArrow3Port%/Connect/127.0.0.1/%port%
echo.
rem if "%interactive%"=="0" pause
pause