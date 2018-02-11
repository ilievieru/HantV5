@echo off
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

start node Thermostat.js %ThermostatPort%
start node LuminositySensor.js %LuminositySensorPort%
start node MotionDetector.js %MotionDetectorPort%
start node SmokeSensor.js %SmokeSensorPort%
start node LeakSensor.js %LeakSensorPort%
start node PatientService.js %PatientServicePort%
start node Alarm.js %AlarmPort%
start node EvacArrow.js %EvacArrow1Port%
start node EvacArrow.js %EvacArrow2Port%
start node EvacArrow.js %EvacArrow3Port%
start python RestDecisionMaking.py

where curl >nul 2>nul
if %errorlevel% neq 0 (
	echo Curl is not installed or not set in the PATH environment variable
	exit /b 0
	)

curl localhost:%ThermostatPort%/GenerateStart
curl localhost:%LuminositySensorPort%/GenerateStart
curl localhost:%MotionDetectorPort%/GenerateStart
curl -k https://localhost:%SmokeSensorPort%/GenerateStart
curl localhost:%LeakSensorPort%/GenerateStart
curl localhost:%PatientServicePort%/GenerateStart
curl localhost:%AlarmPort%/GenerateStart
curl localhost:%EvacArrow1Port%/GenerateStart
curl localhost:%EvacArrow2Port%/GenerateStart
curl localhost:%EvacArrow3Port%/GenerateStart
