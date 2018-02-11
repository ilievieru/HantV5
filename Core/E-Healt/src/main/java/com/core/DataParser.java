package com.core;

import com.devices.*;
import com.devices.abstractFactory.AbstractDevice;
import com.devices.abstractFactory.FactoryDevice;
import com.localConstants.LocalConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataParser {

    public static List<AbstractDevice> devices = new ArrayList<>();
    private static List<String> deviceTypeOrder = new ArrayList<>();
    private static Map<String, String> deviceDefault = new HashMap<>();

    static {
        deviceTypeOrder.add(LocalConstants.leakSensor);
        deviceTypeOrder.add(LocalConstants.luminositySensor);
        deviceTypeOrder.add(LocalConstants.motionSensor);
        deviceTypeOrder.add(LocalConstants.smokeSensor);
        deviceTypeOrder.add(LocalConstants.thermostatSensor);

        deviceDefault.put(LocalConstants.leakSensor, "10,0");
        deviceDefault.put(LocalConstants.luminositySensor, "0");
        deviceDefault.put(LocalConstants.motionSensor, "0,15");
        deviceDefault.put(LocalConstants.smokeSensor, "12");
        deviceDefault.put(LocalConstants.thermostatSensor, "21");
    }

    public static boolean isRelevant(Map<String, Object> data) {
        return getInstance(data).IsRelevant();
    }

    public static AbstractDevice getOrCreateRelevantDevice(Map<String, Object> data) {
        return isRelevant(data) ? getInstance(data) : null;
    }

    private static AbstractDevice getInstance(Map<String, Object> data) {
        for (AbstractDevice abstractDevice : devices)
            if (Integer.parseInt(data.get("ID").toString()) == abstractDevice.getDeviceId()) {
                abstractDevice.update(data);
                return abstractDevice;
            }

        AbstractDevice instance = FactoryDevice.getDevice(data);
        devices.add(instance);
        return instance;
    }

    public static List<AbstractDevice> getDevices() {
        return devices;
    }

    public static String getDevicesRepresentationForDecisionMaking() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        boolean isFirst = true;
        for (String deviceType : deviceTypeOrder) {
            boolean isFound = false;
            for (AbstractDevice device : devices)
                if (device.getDeviceType().equalsIgnoreCase(deviceType)) {
                    String values = device.getDecisionVariables();
                    if (values == null || "".equals(values))
                        continue;

                    if (!isFirst)
                        sb.append(",");

                    sb.append(values); // implement this for every device
                    isFirst = false;
                    isFound = true;
                    break;
                }

            if (!isFound)
                sb.append(deviceDefault.get(deviceType));
        }

        sb.append("]");

        return sb.toString();
    }

    public static void initializeDevices() {
        for (int i = 0; i <= LocalConstants.initialNumberOfSensors; i++) {
            initLeakSensor(i); //10 - 28
            initLuminositySensor(i); // 29-47
            initMotionSensor(i);//48-66
            initSmokeSensor(i);//67-85
            initThermostatSensor(i);//86-104
        }
    }

    private static void initLeakSensor(int i) {
        LeakSensor leakSensor = new LeakSensor();
        leakSensor.setDeviceId(i + 10);
        leakSensor.setDeviceName(LocalConstants.leakSensor);
        leakSensor.setDeviceType(LocalConstants.leakSensor);
        DataParser.devices.add(leakSensor);
    }

    private static void initMotionSensor(int i) {
        MotionSensor motionSensor = new MotionSensor();
        motionSensor.setDeviceId(i + 29);
        motionSensor.setDeviceName(LocalConstants.motionSensor);
        motionSensor.setDeviceType(LocalConstants.motionSensor);
        DataParser.devices.add(motionSensor);
    }

    private static void initLuminositySensor(int i) {
        LuminositySensor luminositySensor = new LuminositySensor();
        luminositySensor.setDeviceId(i + 48);
        luminositySensor.setDeviceName(LocalConstants.luminositySensor);
        luminositySensor.setDeviceType(LocalConstants.luminositySensor);
        DataParser.devices.add(luminositySensor);
    }

    private static void initThermostatSensor(int i) {
        ThermostatSensor thermostatSensor = new ThermostatSensor();
        thermostatSensor.setDeviceId(i + 67);
        thermostatSensor.setDeviceName(LocalConstants.thermostatSensor);
        thermostatSensor.setDeviceType(LocalConstants.thermostatSensor);
        DataParser.devices.add(thermostatSensor);
    }

    private static void initSmokeSensor(int i) {
        SmokeSensor smokeSensor = new SmokeSensor();
        smokeSensor.setDeviceId(i + 86);
        smokeSensor.setDeviceName(LocalConstants.smokeSensor);
        smokeSensor.setDeviceType(LocalConstants.smokeSensor);
        DataParser.devices.add(smokeSensor);
    }
}
