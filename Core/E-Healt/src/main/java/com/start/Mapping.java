package com.start;

import com.agents.Patient;
import com.apacheJena.RdfReader;
import com.core.DataParser;
import com.core.decisionMaking.Decision;
import com.devices.PatientData;
import com.devices.abstractFactory.AbstractDevice;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.localConstants.LocalConstants;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Mapping {

    Map<Long, String> devices;

    @RequestMapping(value = "/NotificationEndpoint", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public int readData(@RequestBody String jsonString) {
        Map<String, Object> data = new HashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON string to Map
            data = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
            System.out.println(data);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            AbstractDevice currentDevice = DataParser.getOrCreateRelevantDevice(data);
            if (currentDevice == null)
                return 200; // Irelevant data

            new Decision().makeDecision(currentDevice);
        } catch (NullPointerException e) {
            System.out.println("....");
        }

        return 200;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/getDevices", method = RequestMethod.POST)
    @ResponseBody
    public List<AbstractDevice>  getDevicesList() {
        List<AbstractDevice> devicesList = LocalConstants.devices;
        return devicesList;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/getDevicesAlarm", method = RequestMethod.POST)
    @ResponseBody
    public List<AbstractDevice>  getDevicesAlamList() {
        List<AbstractDevice> devicesList = DataParser.devices;
        return devicesList;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/getPatients", method = RequestMethod.POST)
    @ResponseBody
    public List<Patient>  getPatientList() {
        List<Patient> devicesList = LocalConstants.patientIndividual;
        return devicesList;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/getPatientsGeneral", method = RequestMethod.POST)
    @ResponseBody
    public List<AbstractDevice>  getPatientGenerralList() {
        List<AbstractDevice> devicesList = LocalConstants.patient;
        return devicesList;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/getRestore", method = RequestMethod.POST)
    @ResponseBody
    public String  getRestore() {
        LocalConstants.patient.clear();
        DataParser.devices.clear();
        DataParser.initializeDevices();
        PatientData.patients.clear();
        return "200";
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/getAlarmsSmokeSensorsTemp", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,  Map<String, String>>  getAlarmsSmokeSensorsTemp() {
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
        }
        return results;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/getAlarmsLuminositySensors", method = RequestMethod.POST)
    @ResponseBody
    public void getAlarmsLuminositySensors() {
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
        }
    }


}
