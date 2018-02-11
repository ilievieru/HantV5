package com.devices;

import com.devices.abstractFactory.AbstractDevice;
import com.localConstants.LocalConstants;

import java.util.Map;

public class LuminositySensor extends AbstractDevice {

    private String lightLevel = "0";
    private String Luminosity = "2";

    public LuminositySensor() {
    }

    public LuminositySensor(Map<String, Object> data) {
        super.update(data);
        update(data);
        this.setDeviceName("Luminuosity Sensor");
    }

    public String getLightLevel() {
        return lightLevel;
    }

    public String getLuminosity() {
        return Luminosity;
    }

    public void setLightLevel(String lightLevel) {
        this.lightLevel = lightLevel;
    }

    public void setLuminosity(String luminosity) {
        Luminosity = luminosity;
    }

    @Override
    public void update(Map<String, Object> data) {
        System.out.println("update " + data);

        this.setLightLevel(data.get(LocalConstants.lightLevel).toString());
        this.setLuminosity(data.get(LocalConstants.luminosity).toString());
    }

    @Override
    public boolean IsRelevant() {
        return Double.parseDouble(lightLevel) > 10 || Double.parseDouble(Luminosity) > 100;
    }

    @Override
    public String getDecisionVariables() {
        return lightLevel;
    }
}
