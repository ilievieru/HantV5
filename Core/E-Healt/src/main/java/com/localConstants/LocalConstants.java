package com.localConstants;

import com.agents.Patient;
import com.devices.PatientData;
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

    List<AbstractDevice> patient = new ArrayList<>();
    List<AbstractDevice> rdfDevices = new ArrayList<>();
    List<Patient> patientIndividual = new ArrayList<>();
    List<AbstractDevice> devices = new ArrayList<>();

    int initialNumberOfSensors = 19;

    String jenaQueryPrefixes = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-sytax-ns#> " +
            "PREFIX j.0:  <https://ro.wikipedia.org/wiki/>" +
            "PREFIX j.1: <https://en.wikipedia.org/wiki/Light_level_geolocator#>" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
            "PREFIX j.2: <https://en.wikipedia.org/wiki/Temperature_measurement#>" +
            "PREFIX j.3: <https://en.wikipedia.org/wiki/Thermostat#>" +
            "PREFIX j.4: <https://en.wikipedia.org/wiki/Carbon_dioxide#>" +
            "PREFIX j.5: <https://en.wikipedia.org/wiki/>" +
            "PREFIX j.6: <https://en.wikipedia.org/wiki/Units_of_measurement#>" +
            "PREFIX j.7: <https://en.wikipedia.org/wiki/Sound_level#>" +
            "PREFIX j.8: <https://en.wikipedia.org/wiki/Motion_detector#>" +
            "PREFIX j.9: <https://en.wikipedia.org/wiki/Light_meter#>" +
            "PREFIX j.10: <https://en.wikipedia.org/wiki/Motion_(physics)#>" +
            "PREFIX j.11: <https://en.wikipedia.org/wiki/Leak_detection#>" +
            "PREFIX j.12: <https://en.wikipedia.org/wiki/Smoke_detector#>" +
            "PREFIX j.13: <https://en.wikipedia.org/wiki/Level_sensor#>" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema>"+
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";

}
