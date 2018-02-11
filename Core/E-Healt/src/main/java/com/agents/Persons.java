package com.agents;

import java.util.Date;

public class Persons {

    private long idPerson;
    private String name;
    private int age;
    private Date dateOfBirth;

    public long getIdPerson() {
        return idPerson;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setIdPerson(long idDoctor) {
        this.idPerson = idDoctor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
