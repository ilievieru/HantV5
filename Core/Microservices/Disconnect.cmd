@echo off

set me=%nxI
rem set interactive=0

rem echo %CMDCMDLINE% | findstr /L %COMSPEC% >NUL 2>&1
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

curl localhost:%ThermostatPort%/Disconnect/127.0.0.1/%port%
echo.
curl localhost:%LuminositySensorPort%/Disconnect/127.0.0.1/%port%
echo.
curl localhost:%MotionDetectorPort%/Disconnect/127.0.0.1/%port%
echo.
curl localhost:%SmokeSensorPort%/Disconnect/127.0.0.1/%port%
echo.
curl localhost:%LeakSensorPort%/Disconnect/127.0.0.1/%port%
echo.
curl localhost:%AlarmPort%/Disconnect/127.0.0.1/%port%
echo.
curl localhost:%EvacArrow1Port%/Disconnect/127.0.0.1/%port%
echo.
curl localhost:%EvacArrow2Port%/Disconnect/127.0.0.1/%port%
echo.
curl localhost:%EvacArrow3Port%/Disconnect/127.0.0.1/%port%

rem if "%interactive%"=="0" pause
pause