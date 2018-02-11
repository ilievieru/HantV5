package com.devices;

import com.devices.abstractFactory.AbstractDevice;
import com.localConstants.LocalConstants;

import java.util.Map;

public class LeakSensor extends AbstractDevice {

    private String humidityLevel = "80";
    private String waterLevel = "1";

    public LeakSensor() {
    }

    public LeakSensor(Map<String, Object> data) {
        super.update(data);
        update(data);
        this.setDeviceName("Leak Sensor");
    }


    public String getHumidityLevel() {
        return humidityLevel;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public void setHumidityLevel(String humidityLevel) {
        this.humidityLevel = humidityLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }

    @Override
    public void update(Map<String, Object> data) {
        System.out.println("update " + data);

        this.setHumidityLevel(data.get(LocalConstants.humidityLevel).toString());
        this.setWaterLevel(data.get(LocalConstants.waterLevel).toString());
    }

    @Override
    public boolean IsRelevant() {
        return Double.parseDouble(humidityLevel) > 100 || Double.parseDouble(waterLevel) > 2;
    }

    @Override
    public String getDecisionVariables() {
        return humidityLevel + "," + waterLevel;
    }
}
