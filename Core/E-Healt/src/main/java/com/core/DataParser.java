package com.core;

import com.devices.*;
import com.devices.abstractFactory.AbstractDevice;
import com.devices.abstractFactory.FactoryDevice;
import com.localConstants.LocalConstants;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DataParser {

    public static List<AbstractDevice> devices = new ArrayList<>();
    private static List<String> deviceTypeOrder = new ArrayList<>();
    private static Map<String, String> deviceDefault = new HashMap<>();
    static OntModel jenaModel = null;

    static {
        deviceTypeOrder.add(LocalConstants.leakSensor);
        deviceTypeOrder.add(LocalConstants.luminositySensor);
        deviceTypeOrder.add(LocalConstants.motionSensor);
        deviceTypeOrder.add(LocalConstants.smokeSensor);
        deviceTypeOrder.add(LocalConstants.thermostatSensor);

        deviceDefault.put(LocalConstants.leakSensor, "10,0");
        deviceDefault.put(LocalConstants.luminositySensor, "0");
        deviceDefault.put(LocalConstants.motionSensor, "0,15");
        deviceDefault.put(LocalConstants.smokeSensor, "12");
        deviceDefault.put(LocalConstants.thermostatSensor, "21");
    }

    public static boolean isRelevant(Map<String, Object> data) {
        return getInstance(data).IsRelevant();
    }

    public static AbstractDevice getOrCreateRelevantDevice(Map<String, Object> data) {
        return isRelevant(data) ? getInstance(data) : null;
    }

    private static AbstractDevice getInstance(Map<String, Object> data) {
        for (AbstractDevice abstractDevice : devices)
            if (Integer.parseInt(data.get("ID").toString()) == abstractDevice.getDeviceId()) {
                abstractDevice.update(data);
                insertRdf(data);
                return abstractDevice;
            }

        AbstractDevice instance = FactoryDevice.getDevice(data);
        if (instance.getDeviceType().equals("PatientData")) {
            LocalConstants.patient.add(instance);
        } else {
            devices.add(instance);
            insertRdf(data);
        }
        return instance;
    }

    public static List<AbstractDevice> getDevices() {
        return devices;
    }

    public static String getDevicesRepresentationForDecisionMaking() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        boolean isFirst = true;
        for (String deviceType : deviceTypeOrder) {
            boolean isFound = false;
            for (AbstractDevice device : devices)
                if (device.getDeviceType().equalsIgnoreCase(deviceType)) {
                    String values = device.getDecisionVariables();
                    if (values == null || "".equals(values))
                        continue;

                    if (!isFirst)
                        sb.append(",");

                    sb.append(values); // implement this for every device
                    isFirst = false;
                    isFound = true;
                    break;
                }

            if (!isFound)
                sb.append(deviceDefault.get(deviceType));
        }

        sb.append("]");

        return sb.toString();
    }

    public static void initializeDevices() {
        for (int i = 1; i <= LocalConstants.initialNumberOfSensors; i++) {
            initLeakSensor(i); //
            initLuminositySensor(i);//
            initMotionSensor(i);//
            initSmokeSensor(i);//
            initThermostatSensor(i);//
        }
    }

    private static void initLeakSensor(int i) {
        LeakSensor leakSensor = new LeakSensor();
        leakSensor.setDeviceId(i + 100);
        leakSensor.setDeviceName(LocalConstants.leakSensor);
        leakSensor.setDeviceType(LocalConstants.leakSensor);
        LocalConstants.devices.add(leakSensor);
    }

    private static void initMotionSensor(int i) {
        MotionSensor motionSensor = new MotionSensor();
        motionSensor.setDeviceId(i + 200);
        motionSensor.setDeviceName(LocalConstants.motionSensor);
        motionSensor.setDeviceType(LocalConstants.motionSensor);
        LocalConstants.devices.add(motionSensor);
    }

    private static void initLuminositySensor(int i) {
        LuminositySensor luminositySensor = new LuminositySensor();
        luminositySensor.setDeviceId(i + 300);
        luminositySensor.setDeviceName(LocalConstants.luminositySensor);
        luminositySensor.setDeviceType(LocalConstants.luminositySensor);
        LocalConstants.devices.add(luminositySensor);
    }

    private static void initThermostatSensor(int i) {
        ThermostatSensor thermostatSensor = new ThermostatSensor();
        thermostatSensor.setDeviceId(i + 400);
        thermostatSensor.setDeviceName(LocalConstants.thermostatSensor);
        thermostatSensor.setDeviceType(LocalConstants.thermostatSensor);
        LocalConstants.devices.add(thermostatSensor);
    }

    private static void initSmokeSensor(int i) {
        SmokeSensor smokeSensor = new SmokeSensor();
        smokeSensor.setDeviceId(i + 500);
        smokeSensor.setDeviceName(LocalConstants.smokeSensor);
        smokeSensor.setDeviceType(LocalConstants.smokeSensor);
        LocalConstants.devices.add(smokeSensor);
    }


    public static void insertRdf(Map<String, Object> data) {
        jenaModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open("D:/wade/SensorsOntologyIndividualFinalProd.owl");
            try {
                jenaModel.read(in, null);
                Resource sensors = jenaModel.createResource("https://en.wikipedia.org/wiki/Sensor");
                Resource node = jenaModel.createResource(sensors);
                if(data.get("Type").equals("LeakSensor")){
                    insertLeakSensor(node,data.get("ID").toString(),data.get("WaterLevel").toString(),data.get("HumidityLevel").toString());
                }
                if(data.get("Type").equals("LuminositySensor")){
                    insertLuminositykSensor(node,data.get("ID").toString(),data.get("LightLevel").toString(),data.get("Luminosity").toString());

                }
                if(data.get("Type").equals("MotionSensor")){
                    insertMotionSensor(node, data.get("ID").toString(), data.get("MotionDetected").toString(), data.get("DisturbanceLevel").toString());

                }
                if(data.get("Type").equals("SmokeSensor")){
                    insertSmokeSensor(node,data.get("ID").toString(),data.get("CO2").toString(),data.get("Temperature").toString());
                }
                if(data.get("Type").equals("ThermostatSensor")){
                    insertThermostatSensor(node,data.get("ID").toString(),data.get("Temperature").toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JenaException je) {
            System.err.println("ERROR" + je.getMessage());
            je.printStackTrace();
            System.exit(0);
        }

        FileWriter fw = null;
        FileWriter fw1 = null;
        try {
            fw = new FileWriter("D:/wade/SensorsOntologyIndividualFinalProd.owl");
            fw1 = new FileWriter("D:/wade/SensorsOntologyIndividualFinalTriplesProd.owl");
            jenaModel.write(fw, "RDF/XML-ABBREV");
            jenaModel.write(fw1, "N-TRIPLES");

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ignore) {
                }
            }

        }
    }

    public static void insertLeakSensor(Resource node, String idValue, String waterLevelValueCM, String humidityValue) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property leakSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Leak_detection#LeakSensor");
        Property waterLevel = jenaModel.createObjectProperty("https://en.wikipedia.org/wiki/Level_sensor#waterLevel");
        Property humidity = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Humidity");

        Property centimeters = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Centimetre");
        Property meters = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Metre");


        node.addProperty(leakSensor, jenaModel.createResource()
                .addProperty(id, idValue)
                .addProperty(timestamp, new Date().toString())
                .addProperty(humidity, humidityValue)
                .addProperty(waterLevel, jenaModel.createResource()
                        .addProperty(centimeters, waterLevelValueCM)
                        .addProperty(meters, (Double.parseDouble(waterLevelValueCM) / 100) + ""))
        );
    }

    public static void insertLuminositykSensor(Resource node, String idValue, String lightLevelValue, String luminosityValue) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property luminositySensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Light_meter#LuminositySensor");
        Property lightLevel = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Light_level_geolocator#LightLevel");
        Property luminosity = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Luminosity");

        node.addProperty(luminositySensor, jenaModel.createResource()
                .addProperty(id, idValue)
                .addProperty(timestamp, new Date().toString())
                .addProperty(lightLevel, lightLevelValue)
                .addProperty(luminosity, luminosityValue)
        );

    }

    public static void insertMotionSensor(Resource node, String idValue, String motionDetectedValue, String disturbanceLevelValue) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property motionSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Motion_detector#MotionSensor");
        Property motionDetected = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Motion_(physics)#MotionDetected");
        Property disturbanceLevel = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Sound_level#DisturbanceLevel");

        node.addProperty(motionSensor, jenaModel.createResource()
                .addProperty(id, idValue)
                .addProperty(timestamp, new Date().toString())
                .addProperty(motionDetected, motionDetectedValue)
                .addProperty(disturbanceLevel, disturbanceLevelValue));

    }

    public static void insertSmokeSensor(Resource node, String idValue, String co2Value, String temperatureValueCelsius) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property smokeSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Smoke_detector#SmokeSensor");
        Property co2 = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Carbon_dioxide#CO2");
        Property temperature = jenaModel.createObjectProperty("https://en.wikipedia.org/wiki/Temperature");

        Property celsius = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Celsius");
        Property fahrenheit = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Fahrenheit");
        Property kelvin = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Kelvin");

        String tempFahrenheit = ((Double.parseDouble(temperatureValueCelsius) * (9 / 5)) + 32) + "";
        String tempKelvin = Double.parseDouble(temperatureValueCelsius) + 273.15 + "";

        node.addProperty(smokeSensor, jenaModel.createResource()
                .addProperty(id, idValue)
                .addProperty(timestamp, new Date().toString())
                .addProperty(co2, co2Value)
                .addProperty(temperature, jenaModel.createResource()
                        .addProperty(celsius, temperatureValueCelsius)
                        .addProperty(fahrenheit, tempFahrenheit + "")
                        .addProperty(kelvin, tempKelvin + ""))
        );

    }

    public static void insertThermostatSensor(Resource node, String idValue, String temperatureThmValue) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property thermostatSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Thermostat#ThermostatSensor");
        Property temperatureThm = jenaModel.createObjectProperty("https://en.wikipedia.org/wiki/Temperature");

        Property celsius = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Celsius");
        Property fahrenheit = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Fahrenheit");
        Property kelvin = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Kelvin");

        String tempFahrenheit = ((Double.parseDouble(temperatureThmValue) * (9 / 5)) + 32) + "";
        String tempKelvin = Double.parseDouble(temperatureThmValue) + 273.15 + "";

        node.addProperty(thermostatSensor, jenaModel.createResource()
                .addProperty(id, idValue)
                .addProperty(timestamp, new Date().toString())
                .addProperty(temperatureThm, jenaModel.createResource()
                        .addProperty(celsius, temperatureThmValue)
                        .addProperty(fahrenheit, tempFahrenheit + "")
                        .addProperty(kelvin, tempKelvin + ""))
        );
    }
}
