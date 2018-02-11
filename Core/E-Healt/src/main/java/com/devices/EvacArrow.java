package com.devices;

import com.devices.abstractFactory.AbstractDevice;
import com.localConstants.LocalConstants;

import java.util.Map;

public class EvacArrow extends AbstractDevice {

    private int isOn;

    public EvacArrow() {
    }

    public EvacArrow(Map<String, Object> data) {
        super.update(data);
        update(data);
        this.setDeviceName("Evacuation Arrow");
    }

    public int getIsOn() {
        return isOn;
    }

    public void setIsOn(int isOn) {
        this.isOn = isOn;
    }

    @Override
    public void update(Map<String, Object> data) {
        this.setIsOn(Integer.parseInt(data.get(LocalConstants.EvacArrowValue).toString()));
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
