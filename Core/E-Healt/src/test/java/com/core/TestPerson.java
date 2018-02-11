package com.core;

import com.agents.Doctors;
import com.agents.Nurse;
import com.agents.Persons;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration()
public class TestPerson {

    @Test
    public void testPerson()
    {
        Persons p = new Persons();

        p.setName("Test");
        assertEquals(p.getName(), "Test");

        p.setIdPerson(1);
        assertEquals(p.getIdPerson(), 1);

        p.setAge(20);
        assertEquals(p.getAge(), 20);

        p.setDateOfBirth(new Date());
        assertEquals(p.getDateOfBirth(), new Date());
    }

    @Test
    public void testDoctor()
    {
        Persons p = new Doctors();

        p.setName("Test");
        assertEquals(p.getName(), "Test");

        p.setIdPerson(1);
        assertEquals(p.getIdPerson(), 1);

        p.setAge(20);
        assertEquals(p.getAge(), 20);

        p.setDateOfBirth(new Date());
        assertEquals(p.getDateOfBirth(), new Date());
    }

    @Test
    public void testNurse()
    {
        Persons p = new Nurse();

        p.setName("Test");
        assertEquals(p.getName(), "Test");

        p.setIdPerson(1);
        assertEquals(p.getIdPerson(), 1);

        p.setAge(20);
        assertEquals(p.getAge(), 20);

        p.setDateOfBirth(new Date());
        assertEquals(p.getDateOfBirth(), new Date());
    }

    @Test
    public void testPatient()
    {
        Persons p = new Persons();

        p.setName("Test");
        assertEquals(p.getName(), "Test");

        p.setIdPerson(1);
        assertEquals(p.getIdPerson(), 1);

        p.setAge(20);
        assertEquals(p.getAge(), 20);

        p.setDateOfBirth(new Date());
        assertEquals(p.getDateOfBirth(), new Date());
    }
}
