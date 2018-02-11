package com.apacheJena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

public class SensorOntology {
    private static final Model M_MODEL = ModelFactory.createDefaultModel();
    public static final String NS = "https://en.wikipedia.org/wiki/Sensor";

    public static Property deviceId;
    public static Property name;
    public static Property type;

    public static Property humidityLevel;
    public static Property waterLevel;

    public static Property lightLevel;
    public static Property Luminosity;

    public static Property motionDetected;
    public static Property DisturbanceLevel;

    public static Property CO2;
    public static Property Temperature;


    public SensorOntology() {
    }

    public static String getURI() {
        return "https://www.w3.org/TR/vocab-ssn/#SOSASensor";
    }

    static {
        deviceId = M_MODEL.createProperty("https://en.wikipedia.org/wiki/DeviceId");
        name = M_MODEL.createProperty("https://en.wikipedia.org/wiki/List_of_sensors");
        type = M_MODEL.createProperty("https://en.wikipedia.org/wiki/List_of_sensors");

        humidityLevel = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Humidity");
        waterLevel = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Water");

        lightLevel = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Level_sensor");
        Luminosity = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Level_sensor");

        motionDetected = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Motion_detector");
        DisturbanceLevel = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Sound_level_meter");

        CO2 = M_MODEL.createProperty("https://ro.wikipedia.org/wiki/Dioxid_de_carbon");
        Temperature = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Temperature");
    }
}
