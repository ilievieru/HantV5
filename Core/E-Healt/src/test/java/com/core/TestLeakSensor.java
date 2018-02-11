package com.core;

import com.apacheJena.RdfWriter;
import com.devices.LeakSensor;
import com.localConstants.LocalConstants;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration()
public class TestLeakSensor {

    Map<String, Object> data;

    public Map<String, Object> populateLeakSensor()
    {
        data = new HashMap<>();
        data.put(LocalConstants.id, "15666356");
        data.put(LocalConstants.type, "LeakSensor");
        data.put(LocalConstants.humidityLevel, "1");
        data.put(LocalConstants.waterLevel, "1");
        return data;
    }

    @Test
    public void testId()
    {
        data = populateLeakSensor();
       /* RdfWriter rdfWriter = new RdfWriter();
        rdfWriter.writeRdfSensors(data);*/
        LeakSensor leakSensor = new LeakSensor(data);
        assertEquals(leakSensor.getDeviceId(), 15666356);
    }

    @Test
    public void testType()
    {
        data = populateLeakSensor();
        LeakSensor leakSensor = new LeakSensor(data);
        assertEquals(leakSensor.getDeviceType(), "Leak");
    }

    @Test
    public void testHumidityLevel()
    {
        data = populateLeakSensor();
        LeakSensor leakSensor = new LeakSensor(data);
        assertEquals(leakSensor.getHumidityLevel(), "1");
    }

    @Test
    public void testWaterLevel()
    {
        data = populateLeakSensor();
        LeakSensor leakSensor = new LeakSensor(data);
        assertEquals(leakSensor.getWaterLevel(), "1");
    }
}
