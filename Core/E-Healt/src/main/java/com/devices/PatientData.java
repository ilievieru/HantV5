package com.devices;

import com.agents.Patient;
import com.apacheJena.RdfWriter;
import com.devices.abstractFactory.AbstractDevice;
import com.localConstants.LocalConstants;

import java.util.ArrayList;
import java.util.Map;

public class PatientData extends AbstractDevice {

    public static ArrayList<Patient> patients = new ArrayList<>();

    public PatientData(Map<String, Object> data) {
        super.update(data);
        update(data);
        this.setDeviceName("Patient Data Source");
    }

    @Override
    public void update(Map<String, Object> data) {
        this.setPatients((ArrayList<Map<String, Object>>) data.get(LocalConstants.patients));
    }

    private void setPatients(ArrayList<Map<String, Object>> get) {
        for (Map<String, Object> entry : get) {
            Patient patient = new Patient();
            if (entry.containsKey(LocalConstants.age))
                patient.setAge((int) entry.get(LocalConstants.age));
            if (entry.containsKey(LocalConstants.pulse))
                patient.setHeartRate((int) entry.get(LocalConstants.pulse));
            if (entry.containsKey(LocalConstants.height))
                patient.setHeight((double) entry.get(LocalConstants.height));
            if (entry.containsKey(LocalConstants.patientId))
                patient.setIdPerson((int) entry.get(LocalConstants.patientId));
            if (entry.containsKey(LocalConstants.isMoving))
                patient.setIsMoving((boolean) entry.get(LocalConstants.isMoving));
            if (entry.containsKey(LocalConstants.bodyTemperature))
                patient.setBodyTemperature(Double.parseDouble(entry.get(LocalConstants.bodyTemperature).toString()));
            if (entry.containsKey(LocalConstants.cam))
                patient.setCameraID((int) entry.get(LocalConstants.cam));

            if ( patient.getAge() < 15) {
                patient.evacPriority = 1;
            }

            if (patient.heartRate > 60 && patient.getAge() < 20 && patient.bodyTemperature > 36) {
                patient.medicalCondition = 1;
                patient.evacPriority = 1;
            }
            if (patient.heartRate > 60 && patient.getAge() > 20 && patient.getAge() > 40 && patient.bodyTemperature > 36) {
                patient.medicalCondition = 1;
                patient.evacPriority = 2;
            }
            if (patient.heartRate > 60 &&  patient.getAge() > 40 && patient.bodyTemperature > 36) {
                patient.medicalCondition = 1;
                patient.evacPriority = 3;
            }

            patients.add(patient);
            if (LocalConstants.patientIndividual.size() != 0) {
                for (Patient p : LocalConstants.patientIndividual) {
                    if (p.getIdPerson() == patient.getIdPerson()) {
                        p = patient;
                    } else {
                        LocalConstants.patientIndividual.add(patient);
                    }
                }
            } else {
                LocalConstants.patientIndividual.add(patient);
            }

        }
    }

    @Override
    public boolean IsRelevant() {
        return true;
    }

    @Override
    public String getDecisionVariables() {
        return "";
    }
}
