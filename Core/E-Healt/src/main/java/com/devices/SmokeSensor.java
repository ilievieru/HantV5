package com.devices;

import com.devices.abstractFactory.AbstractDevice;
import com.localConstants.LocalConstants;

import java.util.Map;

public class SmokeSensor extends AbstractDevice {

    private String CO2 = "10";
    private String Temperature = "40";

    public SmokeSensor() {
    }

    public SmokeSensor(Map<String, Object> data) {
        super.update(data);
        update(data);
        this.setDeviceName("Smoke Sensor");
    }

    public String getCO2() {
        return CO2;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setCO2(String CO2) {
        this.CO2 = CO2;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    @Override
    public void update(Map<String, Object> data) {
        System.out.println("update " + data);
        this.setCO2(data.get(LocalConstants.CO2).toString());
        this.setTemperature(data.get(LocalConstants.temperature).toString());
    }

    @Override
    public boolean IsRelevant() {
        return Double.parseDouble(CO2) > 10 || Double.parseDouble(Temperature) > 45;
    }

    @Override
    public String getDecisionVariables() {
        return CO2;
    }
}
