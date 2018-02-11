package com.nonFunctional;

import com.devices.*;
import com.localConstants.LocalConstants;
import org.junit.Test;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertTrue;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration()
public class TestDataParser {

    Map<String, Object> data;

    public Map<String, Object> populateLeakSensor()
    {
        data = new HashMap<>();
        data.put(LocalConstants.id, "1");
        data.put(LocalConstants.type, LocalConstants.leakSensor);
        data.put(LocalConstants.humidityLevel, "1");
        data.put(LocalConstants.waterLevel, "1");
        return data;
    }

    public Map<String, Object> populateSmokeSensor()
    {
        data = new HashMap<>();
        data.put(LocalConstants.id, "1");
        data.put(LocalConstants.type, LocalConstants.smokeSensor);
        data.put(LocalConstants.CO2, "20");
        data.put(LocalConstants.temperature, "100");
        return data;
    }

    public Map<String, Object> populateLuminositySensor()
    {
        data = new HashMap<>();
        data.put(LocalConstants.id, "1");
        data.put(LocalConstants.type, LocalConstants.luminositySensor);
        data.put(LocalConstants.lightLevel, "10");
        data.put(LocalConstants.luminosity, "101");
        return data;
    }

    public Map<String, Object> populateMotionSensor()
    {
        data = new HashMap<>();
        data.put(LocalConstants.id, "1");
        data.put(LocalConstants.type, LocalConstants.motionSensor);
        data.put(LocalConstants.disturbanceLevel, "20");
        data.put(LocalConstants.motionDetected, "0");

        return data;
    }

    public Map<String, Object> populateThermostatSensor()
    {
        data = new HashMap<>();
        data.put(LocalConstants.id, "1");
        data.put(LocalConstants.type, LocalConstants.thermostatSensor);
        data.put(LocalConstants.temperature, "50");
        return data;
    }

    @Test
    public void test()
    {
        Date date1 = new Date();
        int i = 0;
        populateLeakSensor();
        while (i < 10000000) {
            assertTrue(new LeakSensor(data).IsRelevant());
            i++;
        }
        i = 0;
        populateLuminositySensor();
        while (i < 10000000) {
            assertTrue(new LuminositySensor(data).IsRelevant());
            i++;
        }
        i = 0;
        populateMotionSensor();
        while (i < 10000000) {
            assertTrue(new MotionSensor(data).IsRelevant());
            i++;
        }
        i = 0;
        populateSmokeSensor();
        while (i < 10000000) {
            assertTrue(new SmokeSensor(data).IsRelevant());
            i++;
        }
        i = 0;
        populateThermostatSensor();
        while (i < 10000000) {
            assertTrue(new ThermostatSensor(data).IsRelevant());
            i++;
        }
        Date date2 = new Date();
        long diff = date1.getTime() - date2.getTime();
        System.out.println(diff);//-7788
    }
}
