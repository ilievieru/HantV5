package com.localConstants;

import com.agents.Patient;
import com.devices.abstractFactory.AbstractDevice;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

public interface LocalConstants {

    String id = "ID";
    String type = "Type";

    String leakSensor = "LeakSensor";
    String smokeSensor = "SmokeSensor";
    String motionSensor = "MotionSensor";
    String luminositySensor = "LuminositySensor";
    String thermostatSensor = "ThermostatSensor";
    String patientService = "PatientData";
    String alarmSensor = "Alarm";
    String evacArrowSensor = "EvacArrow";

    //Patients service data
    String patients = "Patients";
    String patientId = "PatientId";
    String cam = "Cam";
    String isMoving = "IsMoving";
    String pulse = "Pulse";
    String bodyTemperature = "BodyTemperature";
    String age = "Age";
    String height = "Height";

    //Leak sensor data
    String humidityLevel = "HumidityLevel";
    String waterLevel = "WaterLevel";

    //Smoke sensor data
    String CO2 = "CO2";
    String temperature = "Temperature";

    //Motion sensor data
    String disturbanceLevel = "DisturbanceLevel";
    String motionDetected = "MotionDetected";

    //Luminosity sensor data
    String lightLevel = "LightLevel";
    String luminosity = "Luminosity";

    //Alarm
    String alarmValue = "Alarm";

    //Evac Arrow
    String EvacArrowValue = "IsOn";

    List<Patient> patient = new ArrayList<>();
    List<AbstractDevice> rdfDevices = new ArrayList<>();

    int initialNumberOfSensors = 18;

}
