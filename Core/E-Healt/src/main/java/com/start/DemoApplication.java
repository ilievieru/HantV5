package com.start;

import com.core.DataParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
       /*
        if (Initialize())*/
        DataParser.initializeDevices();
        SpringApplication.run(DemoApplication.class, args);
    }

    private static boolean Initialize() {
        System.out.println("Training the decision tree...");

        String trainData = "{\"data\":[[10,0,0,0,10,15,24],[20,0,1,0,20,15,24],[20,0,2,1,3,15,24],[20,0,0,1,14,15,24],[7,0,1,0,31,15,24],[11,0,0,0,31,15,24],[18,0,0,1,31,15,24],[26,1,0,0,10,15,24],[46,2,0,0,10,15,24],[80,2,0,0,10,15,24],[20,1,4,0,10,40,24],[20,1,3,0,10,50,24],[20,1,2,0,10,30,24],[20,1,4,0,10,60,24],[20,1,0,0,10,15,50],[10,0,1,0,10,15,60],[15,0,1,0,10,15,70],[21,0,1,1,17,10,20],[13,0,1,1,11,10,20],[5,0,3,1,10,10,30],[6,0,2,1,15,10,24]],\"classifications\":[\"Normal\",\"Normal\",\"Normal\",\"Normal\",\"Normal\",\"Normal\",\"Normal\",\"Flood\",\"Flood\",\"Flood\",\"Fire\",\"Fire\",\"Fire\",\"Fire\",\"Fire\",\"Fire\",\"Fire\",\"Earthquake\",\"Earthquake\",\"Earthquake\",\"Earthquake\"],\"type\":\"Health\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(trainData, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange("http://127.0.0.1:7070/train", HttpMethod.POST, entity, String.class);
        } catch (RestClientException ex) {
            System.out.println("The service is not started! Start the Decision making service and then try again.");
            return false;
        }

        System.out.println(response.getBody());
        return true;
    }


}
