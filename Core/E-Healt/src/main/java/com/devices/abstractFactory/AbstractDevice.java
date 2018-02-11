package com.devices.abstractFactory;

import com.localConstants.LocalConstants;

import java.util.Map;

public abstract class AbstractDevice {

    private String deviceType;
    private int deviceId;
    private String deviceName;
    public boolean isRelevantData;
    public boolean isAlarmResponsable;


    public String getDeviceType() {
        return deviceType;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void update(Map<String, Object> data) {
        this.setDeviceId(Integer.parseInt(data.get(LocalConstants.id).toString()));
        this.setDeviceType(data.get(LocalConstants.type).toString());
    }

    public abstract boolean IsRelevant();

    public abstract String getDecisionVariables();
}
