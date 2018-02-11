package com.agents;


import java.util.List;

public class Doctors extends Persons {

    private List<Patient> patients;

    public Doctors() {
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}
