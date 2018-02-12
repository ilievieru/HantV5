package com.agents;

public class Patient extends Persons {

    public double height;
    public int heartRate;
    public Doctors doctor;
    public boolean isMoving;
    public double bodyTemperature;
    public int camera;
    public int medicalCondition;
    public int evacPriority;

    public Patient() {
    }

    public int getCameraID() {
        return camera;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    public double getBodyTemperature() {
        return bodyTemperature;
    }

    public Double getHeight() {
        return height;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public Doctors getDoctor() {
        return doctor;
    }

    public void setCameraID(int cameraID) {
        camera = cameraID;
    }

    public void setBodyTemperature(Double temperature) {
        bodyTemperature = temperature;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setDoctor(Doctors doctor) {
        this.doctor = doctor;
    }

    public void setIsMoving(boolean moving) {
        isMoving = moving;
    }

    public void setBodyTemperature(double bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }
}
