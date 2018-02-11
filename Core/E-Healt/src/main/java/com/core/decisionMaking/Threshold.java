package com.core.decisionMaking;

import com.localConstants.LocalConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Threshold {

    static final Map<String, ArrayList<Threshold>> ThresholdMap;
    static {
        ThresholdMap = new HashMap<>();
        ThresholdMap.put(LocalConstants.leakSensor, new ArrayList<>());
        ThresholdMap.put(LocalConstants.luminositySensor, new ArrayList<>());
        ThresholdMap.put(LocalConstants.motionSensor, new ArrayList<>());
        ThresholdMap.put(LocalConstants.patientService, new ArrayList<>());
        ThresholdMap.put(LocalConstants.smokeSensor, new ArrayList<>());
        ThresholdMap.put(LocalConstants.thermostatSensor, new ArrayList<>());

        //ToDo: Define thresholds for each device
    }

    public static ArrayList<Threshold> GetThresholds(String deviceType)
    {
        return ThresholdMap.containsKey(deviceType) ? ThresholdMap.get(deviceType) : null;
    }

    private static int Level = 0;
    private Map<String, Object> ThresholdValues;

    public static int getLevel()
    {
        return Level;
    }

    public static void setLevel(int level)
    {
        Level = level;
    }

    public Map<String, Object> getThresholds()
    {
        return ThresholdValues;
    }

    public void setTresholds(Map<String, Object> tresholds)
    {
        ThresholdValues = tresholds;
    }
}
