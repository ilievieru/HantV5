package com.core;

import com.apacheJena.RdfReader;
import com.apacheJena.RdfWriter;
import com.localConstants.LocalConstants;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class RdfWriterTest {
    static OntModel jenaModel = null;


    @Test
    public void testRdfWriter() {
        List<Map<String, Object>> data2 = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("PatientId", 5);
        data.put("Cam", 3);
        data.put("Age", 3);
        data.put("IsMoving", false);
        data.put("Pulse", 3);
        data.put("BodyTemperature", 3.0);
        data.put("Height", 3.0);
        data2.add(data);

        Map<String, Object> data1 = new HashMap<>();
        data1.put("PatientId", 156);
        data1.put("Cam", 3);
        data1.put("Age", 3);
        data1.put("IsMoving", false);
        data1.put("Pulse", 3);
        data1.put("BodyTemperature", 3.0);
        data1.put("Height", 3.0);
        data2.add(data1);

        Map<String, Object> dataFull = new HashMap<>();
        dataFull.put("Patients", data2);

        System.out.println(dataFull);
        System.out.println((ArrayList<Map<String, Object>>) dataFull.get(LocalConstants.patients));
        RdfWriter rdfWriter = new RdfWriter();
        rdfWriter.writeRdfPatient(dataFull);
    }

    @Test
    public void testRdfRead() {

    /*    RdfWriter rdfWriter = new RdfWriter();
        rdfWriter.writeRdf();*/

        RdfReader rdfReader = new RdfReader();
        rdfReader.readRdfFile();
    }

    @Test
    public void testReadOntologyFile() {
        jenaModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open("D:/TestV2.owl");
            try {
                jenaModel.read(in, null);

                Resource sensors = jenaModel.createResource("https://en.wikipedia.org/wiki/Sensor");

                Property leakSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Leak_detection#LeakSensor");
                Property waterLevel = jenaModel.createObjectProperty("https://en.wikipedia.org/wiki/Level_sensor#waterLevel");
                Property humidity = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Humidity");
                Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
                Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

                Property waterLevelUnitOfMeasure = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Centimetre");
                Property waterLevelUnitOfMeasureM = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Metre");

                Property unitOfMeasure = jenaModel.createProperty("https://en.wikipedia.org/wiki/Units_of_measurement#UnitOfMeasure");
                unitOfMeasure.addProperty(waterLevelUnitOfMeasure, waterLevelUnitOfMeasure);
                unitOfMeasure.addProperty(waterLevelUnitOfMeasureM, waterLevelUnitOfMeasureM);

                Property temp = jenaModel.createProperty("https://en.wikipedia.org/wiki/Temperature_measurement#TemperatureMeasurement");
                Property celsius = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Celsius");
                Property farenhite = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Fahrenheit");
                Property kelvin = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Kelvin");

                temp.addProperty(celsius, celsius);
                temp.addProperty(kelvin, kelvin);
                temp.addProperty(farenhite, farenhite);
                unitOfMeasure.addProperty(waterLevelUnitOfMeasure, waterLevelUnitOfMeasure);
                unitOfMeasure.addProperty(waterLevelUnitOfMeasureM, waterLevelUnitOfMeasureM);

                leakSensor.addProperty(id, id);
                leakSensor.addProperty(timestamp, timestamp);
                leakSensor.addProperty(temp, temp);
                leakSensor.addProperty(unitOfMeasure, unitOfMeasure);

                leakSensor.addProperty(humidity, humidity);
                leakSensor.addProperty(waterLevel, waterLevel);
                sensors.addProperty(leakSensor, leakSensor);

                Property luminositySensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Light_meter#LuminositySensor");
                Property lightLevel = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Light_level_geolocator#LightLevel");
                Property luminosity = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Luminosity");
                luminositySensor.addProperty(lightLevel, lightLevel);
                luminositySensor.addProperty(luminosity, luminosity);
                luminositySensor.addProperty(id, id);
                luminositySensor.addProperty(timestamp, timestamp);
                sensors.addProperty(luminositySensor, luminositySensor);

                Property motionSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Motion_detector#MotionSensor");
                Property motionDetected = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Motion_(physics)#MotionDetected");
                Property disturbanceLevel = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Sound_level#DisturbanceLevel");
                motionSensor.addProperty(motionDetected, motionDetected);
                motionSensor.addProperty(disturbanceLevel, disturbanceLevel);
                motionSensor.addProperty(id, id);
                motionSensor.addProperty(timestamp, timestamp);
                sensors.addProperty(motionSensor, motionSensor);

                Property smokeSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Smoke_detector#SmokeSensor");
                Property co2 = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Carbon_dioxide#CO2");
                Property temperature = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Temperature");
                smokeSensor.addProperty(co2, co2);
                smokeSensor.addProperty(temperature, temperature);
                smokeSensor.addProperty(id, id);
                smokeSensor.addProperty(timestamp, timestamp);
                sensors.addProperty(smokeSensor, smokeSensor);

                Property thermostatSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Thermostat#ThermostatSensor");
                Property temperatureThm = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Temperature");
                thermostatSensor.addProperty(temperatureThm, temperatureThm);
                thermostatSensor.addProperty(id, id);
                thermostatSensor.addProperty(timestamp, timestamp);
                sensors.addProperty(thermostatSensor, thermostatSensor);

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
            fw = new FileWriter("D:/TestTest.owl");
            fw1 = new FileWriter("D:/TestTestNTriples.owl");
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

    @Test
    public void testWriteRdfSensorIndivitduals() {

        jenaModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open("D:/SensorOntologyFinal.owl");
            try {
                jenaModel.read(in, null);

                Resource sensors = jenaModel.createResource("https://en.wikipedia.org/wiki/Sensor");
                Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
                Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

                Property smokeSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Smoke_detector#SmokeSensor");
                Property co2 = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Carbon_dioxide#CO2");
                Property temperature = jenaModel.createObjectProperty("https://en.wikipedia.org/wiki/Temperature");
                Property celsius = jenaModel.createDatatypeProperty("https://ro.wikipedia.org/wiki/Celsius");


                Resource node = jenaModel.createResource(sensors)
                        .addProperty(smokeSensor, jenaModel.createResource()
                                .addProperty(co2, "255")
                                .addProperty(id, "1")
                                .addProperty(timestamp, new Date().toString())
                                .addProperty(temperature, jenaModel.createResource()
                                        .addProperty(celsius, "50"))
                        );

               /* Resource node = jenaModel.createResource(smokeSensor)
                        .addProperty(co2, "255")
                        .addProperty(temperature,   jenaModel.createResource()
                                .addProperty(celsius, "50"));*/

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
            fw = new FileWriter("D:/SensorsOntologyIndividualFinal.owl");
            fw1 = new FileWriter("D:/SensorsOntologyIndividualFinalTriples.owl");
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

    @Test
    public void readRdfFile() {
        FileManager.get().addLocatorClassLoader(RdfReader.class.getClassLoader());
        Model model = FileManager.get().loadModel("D:/SensorsOntologyIndividualFinal.owl");
        String queryString = LocalConstants.jenaQueryPrefixes +
                "SELECT * " +
                "where {?SmokeSensor j.0:Celsius ?x}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = queryExecution.execSelect();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            System.out.println(solution.get("x"));
            System.out.println(solution + "teat");
        }
    }

    public void insertRdf(){
        jenaModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open("D:/SensorOntologyFinal.owl");
            try {
                jenaModel.read(in, null);
        Resource sensors = jenaModel.createResource("https://en.wikipedia.org/wiki/Sensor");
        Resource node = jenaModel.createResource(sensors);

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
            fw = new FileWriter("D:/SensorsOntologyIndividualFinal.owl");
            fw1 = new FileWriter("D:/SensorsOntologyIndividualFinalTriples.owl");
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

    public void insertLeakSensor(Resource node, String idValue, String waterLevelValue, String humidityValue) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property leakSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Leak_detection#LeakSensor");
        Property waterLevel = jenaModel.createObjectProperty("https://en.wikipedia.org/wiki/Level_sensor#waterLevel");
        Property humidity = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Humidity");


       /* node = jenaModel.createResource(sensors)
                .addProperty(smokeSensor, jenaModel.createResource()
                        .addProperty(co2, "255")
                        .addProperty(id, "1")
                        .addProperty(timestamp, new Date().toString())
                        .addProperty(temperature, jenaModel.createResource()
                                .addProperty(celsius, "50"))
                );*/
    }

    public void insertLuminositykSensor(Resource node, String idValue, String lightLevelValue, String luminosityValue ) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property luminositySensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Light_meter#LuminositySensor");
        Property lightLevel = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Light_level_geolocator#LightLevel");
        Property luminosity = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Luminosity");

    }

    public void insertMotionSensor(Resource node, String idValue, String motionDetectedValue, String disturbanceLevelValue) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property motionSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Motion_detector#MotionSensor");
        Property motionDetected = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Motion_(physics)#MotionDetected");
        Property disturbanceLevel = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Sound_level#DisturbanceLevel");

    }

    public void insertSmokeSensor(Resource node, String idValue, String co2Value, String temperatureValue) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property smokeSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Smoke_detector#SmokeSensor");
        Property co2 = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Carbon_dioxide#CO2");
        Property temperature = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Temperature");

    }

    public void insertThermostatSensor(Resource node, String idValue, String temperatureThmValue) {
        Property id = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Unique_identifier");
        Property timestamp = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Timestamp");

        Property thermostatSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Thermostat#ThermostatSensor");
        Property temperatureThm = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Temperature");

    }

    @Test
    public void testPatients(){
        System.out.println(LocalConstants.patient.size());
        System.out.println(LocalConstants.patientIndividual.size());
        System.out.println(LocalConstants.rdfDevices.size());
        System.out.println(DataParser.devices.size());
    }
}
