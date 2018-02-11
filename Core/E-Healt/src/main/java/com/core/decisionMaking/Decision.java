package com.core.decisionMaking;

import com.core.DataParser;

import com.devices.abstractFactory.AbstractDevice;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class Decision {

    public void makeDecision(AbstractDevice device)
    {
        String decision = getDecision(device);
        if (decision == null || "".equals(decision) || "Normal".equals(decision)) {
            if (device.isAlarmResponsable) {
                device.isAlarmResponsable = false;
                checkAlarmDismissal();
            }

            return;
        }

        device.isAlarmResponsable = true;
        Threshold.setLevel(1);

        SendAlarm(device);
    }

    private String getDecision(AbstractDevice device)
    {
        /*System.out.println("Fetching decision...");

        String devicesData = DataParser.getDevicesRepresentationForDecisionMaking();
        String decisionData = "{ \"data\": [" + devicesData + "], \"type\": \"Health\" }"; // device.getDecisionData();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(decisionData, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange("http://127.0.0.1:7070/predict", HttpMethod.POST, entity, String.class);
        }
        catch (RestClientException ex) {
            System.out.println("An error occured while fetching the decision.");
            return null;
        }

        String responseString = response.getBody();
        System.out.println(responseString);
        return responseString;*/
        return "";
    }

    private void SendAlarm(AbstractDevice device)
    {
        System.out.println("Sending alarm alert...");

        String alertData = "{ \"deviceId\": \"" + device.getDeviceId() + "\", \"type\": \"" + device.getDeviceType() + "\", \"isAlarm\": \"1\" }";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(alertData, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange("http://127.0.0.1:7071/hospital", HttpMethod.POST, entity, String.class);
        }
        catch (RestClientException ex) {
            System.out.println("An error occured while sending the alarm !");
            return;
        }

        System.out.println(response.getBody());
    }

    private void checkAlarmDismissal()
    {
        for (AbstractDevice device : DataParser.getDevices())
            if (device.isAlarmResponsable)
                return;

        System.out.println("Disabling alarm alert...");

        String alertDisableData = "{ \"isAlarm\": \"0\" }";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(alertDisableData, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange("http://127.0.0.1:7071/hospitalDisableAlarm", HttpMethod.POST, entity, String.class);
        }
        catch (RestClientException ex) {
            System.out.println("An error occured while disabling the alarm !");
            return;
        }

        System.out.println(response.getBody());
    }
}
