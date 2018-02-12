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
                Property temperature = jenaModel.createObjectProperty("https://en.wikipedia.org/wiki/Temperature");
                smokeSensor.addProperty(co2, co2);
                smokeSensor.addProperty(temperature, temperature);
                smokeSensor.addProperty(id, id);
                smokeSensor.addProperty(timestamp, timestamp);
                sensors.addProperty(smokeSensor, smokeSensor);

                Property thermostatSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Thermostat#ThermostatSensor");
                Property temperatureThm = jenaModel.createObjectProperty("https://en.wikipedia.org/wiki/Temperature");
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
    public void testGetAlarmsSmokeSensorsTemp() {
        Map<String,  Map<String, String>> results = new HashMap<>();
        Map<String, String> temp = new HashMap<>();
        FileManager.get().addLocatorClassLoader(RdfReader.class.getClassLoader());
        Model model = FileManager.get().loadModel("D:/SensorsOntologyIndividualFinal.owl");
        String queryString = LocalConstants.jenaQueryPrefixes +
                "SELECT * " +
                "where {?SmokeSensor j.0:Celsius ?x." +
                "?SmokeSensor j.0:Fahrenheit ?y ." +
                "?SmokeSensor j.0:Kelvin ?z} ";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = queryExecution.execSelect();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            temp = new HashMap<>();
            temp.put("Celsius",solution.get("x").toString());
            temp.put("Fahrenheit",solution.get("y").toString());
            temp.put("Kelvin",solution.get("z").toString());
            results.put(solution.get("SmokeSensor").toString(),temp);
            System.out.println(solution.get("x"));
            System.out.println(solution.get("y"));
            System.out.println(solution.get("z"));

            System.out.println(solution.get("SmokeSensor"));
            System.out.println(solution + "Complete");
        }
    }

    @Test
    public void testGetAlarmsLuminositySensors() {
        Map<String,  Map<String, String>> results = new HashMap<>();
        Map<String, String> temp;
        FileManager.get().addLocatorClassLoader(RdfReader.class.getClassLoader());
        Model model = FileManager.get().loadModel("D:/SensorsOntologyIndividualFinal.owl");
        String queryString = LocalConstants.jenaQueryPrefixes +
                "SELECT * " +
                "where {?LuminositySensor j.4:Luminosity ?x." +
                "?LuminositySensor j.1:LightLevel ?y ." +
                "?LuminositySensor j.4:Timestamp ?z} ";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = queryExecution.execSelect();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            temp = new HashMap<>();
            temp.put("Luminosity",solution.get("x").toString());
            temp.put("LightLevel",solution.get("y").toString());
            temp.put("Timestamp",solution.get("z").toString());
            results.put(solution.get("LuminositySensor").toString(),temp);
            System.out.println(solution.get("x"));
            System.out.println(solution.get("y"));
            System.out.println(solution.get("z"));

            System.out.println(solution.get("SmokeSensor"));
            System.out.println(solution + "Complete");
        }
    }

    @Test
    public void insertRdfTest() {
        insertRdf1();
        insertRdf();
    }

    public void insertRdf() {
        jenaModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open("D:/SensorOntologyFinal.owl");
            try {
                jenaModel.read(in, null);
                Resource sensors = jenaModel.createResource("https://en.wikipedia.org/wiki/Sensor");
                Resource node = jenaModel.createResource(sensors);
                insertLeakSensor(node, "12", "22", "33");
                insertLuminositykSensor(node, "13", "23", "34");
                insertMotionSensor(node, "14", "24", "35");
                insertSmokeSensor(node, "15", "25", "36");
                insertThermostatSensor(node, "16", "26");

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

    public void insertRdf1() {
        jenaModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open("D:/SensorOntologyFinal.owl");
            try {
                jenaModel.read(in, null);
                Resource sensors = jenaModel.createResource("https://en.wikipedia.org/wiki/Sensor");
                Resource node = jenaModel.createResource(sensors);
                insertLeakSensor(node, "129999", "229999", "339999");
                insertLuminositykSensor(node, "139999", "239999", "349999");
                insertMotionSensor(node, "149999", "249999", "359999");
                insertSmokeSensor(node, "159999", "259999", "369999");
                insertThermostatSensor(node, "169999", "269999");

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

    public void insertLeakSensor(Resource node, String idValue, String waterLevelValueCM, String humidityValue) {
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

    public void insertLuminositykSensor(Resource node, String idValue, String lightLevelValue, String luminosityValue) {
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

    public void insertMotionSensor(Resource node, String idValue, String motionDetectedValue, String disturbanceLevelValue) {
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

    public void insertSmokeSensor(Resource node, String idValue, String co2Value, String temperatureValueCelsius) {
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

    public void insertThermostatSensor(Resource node, String idValue, String temperatureThmValue) {
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

    @Test
    public void testPatients() {
        System.out.println(LocalConstants.patient.size());
        System.out.println(LocalConstants.patientIndividual.size());
        System.out.println(LocalConstants.rdfDevices.size());
        System.out.println(DataParser.devices.size());
    }

    @Test
    public void testCreatePatientOntology() {

    }
}
