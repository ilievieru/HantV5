package com.core;

import com.devices.*;
import com.localConstants.LocalConstants;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
    public void testRelevantLeakSensorData()
    {
        populateLeakSensor();
        assertTrue(new LeakSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantLeakSensorData1()
    {
        populateLeakSensor();
        assertTrue(new LeakSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantLeakSensorData2()
    {
        populateLeakSensor();
        data.put(LocalConstants.humidityLevel, "0");
        data.put(LocalConstants.waterLevel, "0");
        assertFalse(new LeakSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantSmokeSensorData()
    {
        populateSmokeSensor();
        assertTrue(new SmokeSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantSmokeSensorData1()
    {
        populateSmokeSensor();
        assertTrue(new SmokeSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantSmokeSensorData2()
    {
        populateSmokeSensor();
        data.put(LocalConstants.CO2, "9");
        data.put(LocalConstants.temperature, "40");
        assertFalse(new SmokeSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantMotionSensorData()
    {
        populateMotionSensor();
        assertTrue(new MotionSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantMotionSensorData1()
    {
        populateMotionSensor();
        assertTrue(new MotionSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantMotionSensorData2()
    {
        populateMotionSensor();
        data.put(LocalConstants.disturbanceLevel, "0");
        assertFalse(new MotionSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantLuminositySensorData()
    {
        populateLuminositySensor();
        assertTrue(new LuminositySensor(data).IsRelevant());
    }

    @Test
    public void testRelevantLuminositySensorData1()
    {
        populateLuminositySensor();
        assertTrue(new LuminositySensor(data).IsRelevant());
    }

    @Test
    public void testRelevantLuminositySensorData2()
    {
        populateLuminositySensor();
        data.put(LocalConstants.lightLevel, "9");
        data.put(LocalConstants.luminosity, "99");
        assertFalse(new LuminositySensor(data).IsRelevant());
    }

    @Test
    public void testRelevantThermostatData()
    {
        populateThermostatSensor();
        assertTrue(new ThermostatSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantThermostatData1()
    {
        populateThermostatSensor();
        assertTrue(new ThermostatSensor(data).IsRelevant());
    }

    @Test
    public void testRelevantThermostatData2()
    {
        populateThermostatSensor();
        data.put(LocalConstants.temperature, "10");
        assertFalse(new ThermostatSensor(data).IsRelevant());
    }

    @Test
    public void testInitializeDevicesList(){
        DataParser.initializeDevices();
        assertEquals(DataParser.devices.size(),LocalConstants.initialNumberOfSensors*5);
    }
}
