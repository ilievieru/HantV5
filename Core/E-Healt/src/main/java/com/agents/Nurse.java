package com.agents;


import java.util.List;

public class Nurse extends Persons {

    private List<Patient> patients;

    public Nurse() {
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
