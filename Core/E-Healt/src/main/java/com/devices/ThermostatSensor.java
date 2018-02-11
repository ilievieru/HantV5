package com.devices;

import com.devices.abstractFactory.AbstractDevice;
import com.localConstants.LocalConstants;

import java.util.Map;

public class ThermostatSensor extends AbstractDevice {

    private String Teperature = "45";

    public ThermostatSensor() {
    }

    public ThermostatSensor(Map<String, Object> data) {
        super.update(data);
        update(data);
        this.setDeviceName("Thermostat Sensor");
    }

    public String getTeperature() {
        return Teperature;
    }

    public void setTeperature(String teperature) {
        Teperature = teperature;
    }

    @Override
    public void update(Map<String, Object> data) {
        System.out.println("update " + data);

        this.setTeperature(data.get(LocalConstants.temperature).toString());
    }

    @Override
    public boolean IsRelevant() {
        return Double.parseDouble(Teperature) > 45;
    }

    @Override
    public String getDecisionVariables() {
        return Teperature;
    }
}
