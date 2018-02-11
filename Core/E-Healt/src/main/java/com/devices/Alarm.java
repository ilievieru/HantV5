package com.devices;

import com.devices.abstractFactory.AbstractDevice;
import com.localConstants.LocalConstants;

import java.util.Map;

public class Alarm extends AbstractDevice {

    private int alarmValue;

    public Alarm() {
    }

    public Alarm(Map<String, Object> data) {
        super.update(data);
        update(data);
        this.setDeviceName("Alarm");
    }

    public int getalarmValue() {
        return alarmValue;
    }

    public void setalarmValue(int waterLevel) {
        this.alarmValue = waterLevel;
    }

    @Override
    public void update(Map<String, Object> data) {
        this.setalarmValue(Integer.parseInt(data.get(LocalConstants.alarmValue).toString()));
    }

    @Override
    public boolean IsRelevant() {
        return false;
    }

    @Override
    public String getDecisionVariables() {
        return "";
    }
}
