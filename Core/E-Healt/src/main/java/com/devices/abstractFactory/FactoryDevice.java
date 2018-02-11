package com.devices.abstractFactory;

import com.apacheJena.RdfWriter;
import com.localConstants.LocalConstants;

import java.lang.reflect.Constructor;
import java.util.Map;

public class FactoryDevice {

    public static AbstractDevice getDevice(Map<String, Object> data)
    {
        System.out.println("Creating " + data.get(LocalConstants.type) + "...");
        RdfWriter rdfWriter = new RdfWriter();
        rdfWriter.writeRdfSensors(data);

        try {
            Class<?> clazz = Class.forName("com.rdfDevices." + (String) data.get(LocalConstants.type));
            Constructor<?> ctor;
            ctor = clazz.getConstructor(Map.class);
            return (AbstractDevice) ctor.newInstance(new Object[] { data });
        }
        catch (Exception ex) {
            AbstractDevice device = new AbstractDevice() {
                @Override
                public boolean IsRelevant()
                {
                    return false;
                }

                @Override
                public String getDecisionVariables()
                {
                    return "";
                }
            };

            device.update(data);
            device.setDeviceName("General Device");

            return device;
        }
    }
}
