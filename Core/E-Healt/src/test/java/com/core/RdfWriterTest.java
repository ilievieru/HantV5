package com.core;

import com.apacheJena.RdfReader;
import com.apacheJena.RdfWriter;
import com.localConstants.LocalConstants;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                Property waterLevelUnitOfMeasure = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Centimetre");
                Property waterLevelUnitOfMeasureM = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Metre");

                Property unitOfMeasure = jenaModel.createProperty("https://en.wikipedia.org/wiki/Units_of_measurement#UnitOfMeasure");
                unitOfMeasure.addProperty(waterLevelUnitOfMeasure,waterLevelUnitOfMeasure);
                unitOfMeasure.addProperty(waterLevelUnitOfMeasureM,waterLevelUnitOfMeasureM);

                leakSensor.addProperty(unitOfMeasure,unitOfMeasure);

                leakSensor.addProperty(humidity, humidity);
                leakSensor.addProperty(waterLevel, waterLevel);
                sensors.addProperty(leakSensor, leakSensor);

                Property luminositySensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Light_meter#LuminositySensor");
                Property lightLevel = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Light_level_geolocator#LightLevel");
                Property luminosity = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Luminosity");
                luminositySensor.addProperty(lightLevel, lightLevel);
                luminositySensor.addProperty(luminosity, luminosity);
                sensors.addProperty(luminositySensor, luminositySensor);

                Property motionSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Motion_detector#MotionSensor");
                Property motionDetected = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Motion_(physics)#MotionDetected");
                Property disturbanceLevel = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Sound_level#DisturbanceLevel");
                motionSensor.addProperty(motionDetected, motionDetected);
                motionSensor.addProperty(disturbanceLevel, disturbanceLevel);
                sensors.addProperty(motionSensor, motionSensor);

                Property smokeSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Smoke_detector#SmokeSensor");
                Property co2 = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Carbon_dioxide#CO2");
                Property temperature = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Temperature");
                smokeSensor.addProperty(co2, co2);
                smokeSensor.addProperty(temperature, temperature);
                sensors.addProperty(smokeSensor, smokeSensor);

                Property thermostatSensor = jenaModel.createProperty("https://en.wikipedia.org/wiki/Thermostat#ThermostatSensor");
                Property temperatureThm = jenaModel.createDatatypeProperty("https://en.wikipedia.org/wiki/Temperature");
                thermostatSensor.addProperty(temperatureThm, temperatureThm);
                sensors.addProperty(thermostatSensor,thermostatSensor);

                Individual individual = jenaModel.createIndividual(smokeSensor);
                individual.addProperty(co2,"12");
                individual.addProperty(temperature,"1sad2");

                Individual individual2 = jenaModel.createIndividual(smokeSensor);
                individual2.addProperty(co2,"1222");
                individual2.addProperty(temperature,"1sad22222");

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

}
