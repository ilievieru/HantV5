package com.start;

import com.agents.Patient;
import com.core.DataParser;
import com.core.decisionMaking.Decision;
import com.devices.PatientData;
import com.devices.abstractFactory.AbstractDevice;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.localConstants.LocalConstants;
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
        List<AbstractDevice> devicesList = DataParser.devices;
        return devicesList;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/getPatients", method = RequestMethod.POST)
    @ResponseBody
    public List<Patient>  getPatientList() {
        List<Patient> devicesList = LocalConstants.patient;
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
}
