package com.devices;

import com.devices.abstractFactory.AbstractDevice;
import com.localConstants.LocalConstants;

import java.util.Map;

public class MotionSensor extends AbstractDevice {

    private String motionDetected = "false";
    private String DisturbanceLevel = "10";

    public MotionSensor() {

    }

    public MotionSensor(Map<String, Object> data) {
        super.update(data);
        update(data);
        this.setDeviceName("Motion Sensor");
    }

    public String getMotionDetected() {
        return motionDetected;
    }

    public String getDisturbanceLevel() {
        return DisturbanceLevel;
    }

    public void setMotionDetected(String motionDetected) {
        this.motionDetected = motionDetected;
    }

    public void setDisturbanceLevel(String disturbanceLevel) {
        DisturbanceLevel = disturbanceLevel;
    }

    @Override
    public void update(Map<String, Object> data) {
        System.out.println("update " + data);

        this.setDisturbanceLevel(data.get(LocalConstants.disturbanceLevel).toString());
        this.setMotionDetected(data.get(LocalConstants.motionDetected).toString());
    }

    @Override
    public boolean IsRelevant() {
        return Double.parseDouble(DisturbanceLevel) > 10;
    }

    @Override
    public String getDecisionVariables() {
        return (motionDetected.equalsIgnoreCase("True") ? "1" : "0") + "," + DisturbanceLevel;
    }
}
