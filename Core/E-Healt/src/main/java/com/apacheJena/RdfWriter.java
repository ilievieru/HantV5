package com.apacheJena;

import com.agents.Patient;
import com.devices.*;
import com.localConstants.LocalConstants;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.*;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class RdfWriter {

    public void writeRdf() {
        String personURI = "http://localhost:8080/rdfResources/IlieVieru.rdf";
        String lastName = "Ilie-Constantin";
        String firstName = "Vieriu";

        Model model = ModelFactory.createDefaultModel();
        Resource node = model.createResource(personURI)
                .addProperty(FOAF.accountName, firstName + lastName)
                .addProperty(FOAF.name,
                        model.createResource()
                                .addProperty(FOAF.firstName, firstName)
                                .addProperty(FOAF.lastName, lastName));
        try {
            FileOutputStream fout = new FileOutputStream(
                    "src\\main\\resources\\rdfResources\\IlieVieru.rdf");
            model.write(fout);
            FileOutputStream fout1 = new FileOutputStream(
                    "src\\main\\resources\\rdfResources\\IlieVieru.xml");
            model.write(fout1);
        } catch (IOException e) {
            System.out.println("Exception caught " + e.getMessage());
        }
    }

    public void writeRdfPatient(Map<String, Object> data) {
        ArrayList<Map<String, Object>> get;
        get = (ArrayList<Map<String, Object>>) data.get(LocalConstants.patients);
        if (get != null) {
            for (Map<String, Object> entry : get) {
                Patient patient = new Patient();
                LocalConstants.patient.add(patient);
                if (entry.containsKey(LocalConstants.age))
                    patient.setAge((int) entry.get(LocalConstants.age));
                if (entry.containsKey(LocalConstants.pulse))
                    patient.setHeartRate((int) entry.get(LocalConstants.pulse));
                if (entry.containsKey(LocalConstants.height))
                    patient.setHeight((double) entry.get(LocalConstants.height));
                if (entry.containsKey(LocalConstants.patientId))
                    patient.setIdPerson((int) entry.get(LocalConstants.patientId));
                if (entry.containsKey(LocalConstants.isMoving))
                    patient.setIsMoving((boolean) entry.get(LocalConstants.isMoving));
                if (entry.containsKey(LocalConstants.bodyTemperature))
                    patient.setBodyTemperature((double) entry.get(LocalConstants.bodyTemperature));
                if (entry.containsKey(LocalConstants.cam))
                    patient.setCameraID((int) entry.get(LocalConstants.cam));
                String deviceUri = "https://localhost:8443/rdfResources/PatientWithId" + patient.getIdPerson() + ".rdf";
                Model model = ModelFactory.createDefaultModel();
                Resource node = model.createResource(deviceUri)
                        .addProperty(PatientOntology.idPerson, String.valueOf(patient.getIdPerson()))
                        .addProperty(FOAF.name, patient.getName() + "")
                        .addProperty(PatientOntology.age, String.valueOf(patient.getAge()))
                        .addProperty(PatientOntology.birthday, String.valueOf(patient.getDateOfBirth()))
                        .addProperty(PatientOntology.height, String.valueOf(patient.height))
                        .addProperty(PatientOntology.heartRate, String.valueOf(patient.getHeartRate()))
                        .addProperty(PatientOntology.bodyTemperature, String.valueOf(patient.getBodyTemperature()))
                        .addProperty(PatientOntology.camera, patient.getCameraID() + "");
                try {
                    FileOutputStream fout = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\PatientWithId" + patient.getIdPerson() + ".rdf");
                    model.write(fout);
                    FileOutputStream fout1 = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\PatientWithId" + patient.getIdPerson() + ".xml");
                    model.write(fout1);
                } catch (IOException e) {
                    System.out.println("Exception caught " + e.getMessage());
                }
            }
        }
    }

    public void writeRdfSensors(Map<String, Object> data) {
        if (data.get(LocalConstants.type).equals(LocalConstants.leakSensor)) {
            System.out.println("RDF leak sensor");

            LeakSensor leakSensor = new LeakSensor(data);
            LocalConstants.rdfDevices.add(leakSensor);

            String deviceUri = "https://localhost:8443/rdfResources/" + LocalConstants.leakSensor + "-" + data.get(LocalConstants.id) + ".rdf";
            Model model = ModelFactory.createDefaultModel();
            Resource node = model.createResource(deviceUri)
                    .addProperty(SensorOntology.deviceId, data.get(LocalConstants.id).toString())
                    .addProperty(FOAF.name, LocalConstants.leakSensor)
                    .addProperty(SensorOntology.type, data.get(LocalConstants.type).toString())
                    .addProperty(SensorOntology.humidityLevel, data.get(LocalConstants.humidityLevel).toString())
                    .addProperty(SensorOntology.waterLevel, data.get(LocalConstants.waterLevel).toString());
            try {
                FileOutputStream fout = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.leakSensor + data.get(LocalConstants.id) + ".rdf");
                model.write(fout);
                FileOutputStream fout1 = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.leakSensor + data.get(LocalConstants.id) + ".xml");
                model.write(fout1);
            } catch (IOException e) {
                System.out.println("Exception caught " + e.getMessage());
            }
        }

        if (data.get(LocalConstants.type).equals(LocalConstants.luminositySensor)) {
            System.out.println("RDF luminosity sensor");

            LuminositySensor luminositySensor = new LuminositySensor(data);
            LocalConstants.rdfDevices.add(luminositySensor);

            String deviceUri = "https://localhost:8443/rdfResources/" + LocalConstants.luminositySensor + "-" + data.get(LocalConstants.id) + ".rdf";
            Model model = ModelFactory.createDefaultModel();
            Resource node = model.createResource(deviceUri)
                    .addProperty(SensorOntology.deviceId, data.get(LocalConstants.id).toString())
                    .addProperty(FOAF.name, LocalConstants.luminositySensor)
                    .addProperty(SensorOntology.type, data.get(LocalConstants.type).toString())
                    .addProperty(SensorOntology.lightLevel, data.get(LocalConstants.lightLevel).toString())
                    .addProperty(SensorOntology.Luminosity, data.get(LocalConstants.luminosity).toString());
            try {
                FileOutputStream fout = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.luminositySensor + data.get(LocalConstants.id) + ".rdf");
                model.write(fout);
                FileOutputStream fout1 = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.luminositySensor + data.get(LocalConstants.id) + ".xml");
                model.write(fout1);
            } catch (IOException e) {
                System.out.println("Exception caught " + e.getMessage());
            }
        }

        if (data.get(LocalConstants.type).equals(LocalConstants.smokeSensor)) {
            System.out.println("RDF smoke sensor");

            SmokeSensor smokeSensor = new SmokeSensor(data);
            LocalConstants.rdfDevices.add(smokeSensor);

            String deviceUri = "https://localhost:8443/rdfResources/" + LocalConstants.smokeSensor + "-" + data.get(LocalConstants.id) + ".rdf";
            Model model = ModelFactory.createDefaultModel();
            Resource node = model.createResource(deviceUri)
                    .addProperty(SensorOntology.deviceId, data.get(LocalConstants.id).toString())
                    .addProperty(FOAF.name, LocalConstants.smokeSensor)
                    .addProperty(SensorOntology.type, data.get(LocalConstants.type).toString())
                    .addProperty(SensorOntology.CO2, data.get(LocalConstants.CO2).toString())
                    .addProperty(SensorOntology.Temperature, data.get(LocalConstants.temperature).toString());
            try {
                FileOutputStream fout = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.smokeSensor + data.get(LocalConstants.id) + ".rdf");
                model.write(fout);
                FileOutputStream fout1 = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.smokeSensor + data.get(LocalConstants.id) + ".xml");
                model.write(fout1);
            } catch (IOException e) {
                System.out.println("Exception caught " + e.getMessage());
            }
        }

        if (data.get(LocalConstants.type).equals(LocalConstants.motionSensor)) {
            System.out.println("RDF motion sensor");

            MotionSensor motionSensor = new MotionSensor(data);
            LocalConstants.rdfDevices.add(motionSensor);

            String deviceUri = "https://localhost:8443/rdfResources/" + LocalConstants.motionSensor + "-" + data.get(LocalConstants.id) + ".rdf";
            Model model = ModelFactory.createDefaultModel();
            Resource node = model.createResource(deviceUri)
                    .addProperty(SensorOntology.deviceId, data.get(LocalConstants.id).toString())
                    .addProperty(FOAF.name, LocalConstants.motionSensor)
                    .addProperty(SensorOntology.type, data.get(LocalConstants.type).toString())
                    .addProperty(SensorOntology.motionDetected, data.get(LocalConstants.motionDetected).toString())
                    .addProperty(SensorOntology.DisturbanceLevel, data.get(LocalConstants.disturbanceLevel).toString());
            try {
                FileOutputStream fout = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.motionSensor + data.get(LocalConstants.id) + ".rdf");
                model.write(fout);
                FileOutputStream fout1 = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.motionSensor + data.get(LocalConstants.id) + ".xml");
                model.write(fout1);
            } catch (IOException e) {
                System.out.println("Exception caught " + e.getMessage());
            }
        }

        if (data.get(LocalConstants.type).equals(LocalConstants.thermostatSensor)) {
            System.out.println("RDF thermostat sensor");

            ThermostatSensor thermostatSensor = new ThermostatSensor(data);
            LocalConstants.rdfDevices.add(thermostatSensor);

            String deviceUri = "https://localhost:8443/rdfResources/" + LocalConstants.thermostatSensor + "-" + data.get(LocalConstants.id) + ".rdf";
            Model model = ModelFactory.createDefaultModel();
            Resource node = model.createResource(deviceUri)
                    .addProperty(SensorOntology.deviceId, data.get(LocalConstants.id).toString())
                    .addProperty(FOAF.name, LocalConstants.thermostatSensor)
                    .addProperty(SensorOntology.type, data.get(LocalConstants.type).toString())
                    .addProperty(SensorOntology.Temperature, data.get(LocalConstants.temperature).toString());
            try {
                FileOutputStream fout = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.thermostatSensor + data.get(LocalConstants.id) + ".rdf");
                model.write(fout);
                FileOutputStream fout1 = new FileOutputStream("Core\\E-Healt\\src\\main\\resources\\static\\rdfResources\\" + LocalConstants.thermostatSensor + data.get(LocalConstants.id) + ".xml");
                model.write(fout1);
            } catch (IOException e) {
                System.out.println("Exception caught " + e.getMessage());
            }
        }
    }

    public void createOntology(){

        OntModel m = ModelFactory.createOntologyModel();
        System.out.println(m.getBaseModel());
    }

    
}

