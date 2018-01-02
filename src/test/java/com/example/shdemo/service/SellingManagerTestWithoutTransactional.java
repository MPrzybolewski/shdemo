package com.example.shdemo.service;


import com.example.shdemo.domain.Monitor;
import com.example.shdemo.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
public class SellingManagerTestWithoutTransactional {

    @Autowired
    SellingManager sellingManager;

    private final String NAME_1 = "Bolek";
    private final String PIN_1 = "1234";

    @Test
    public void LazyErrorCheck() {

        List<Person> retrievedClients = sellingManager.getAllClients();

        // If there is a client with PIN_1 delete it
        for (Person client : retrievedClients) {
            if (client.getPin().equals(PIN_1)) {
                sellingManager.deleteClient(client);
            }
        }
        boolean pass = false;

        Monitor mon = new Monitor();
        mon.setFrequency(44);
        mon.setDiagonal(23.44);
        mon.setSold(false);
        mon.setModel("LG");

        sellingManager.addNewMonitor(mon);
        Person person = new Person();
        person.setFirstName(NAME_1);
        person.setPin(PIN_1);

        sellingManager.addClient(person);

        sellingManager.disposeMonitor(person,mon);

        Person retrievedClient = sellingManager.findClientByPin(PIN_1);

        try {
            System.out.println(retrievedClient.getMonitors().get(0).getModel());
        }
        catch (org.hibernate.LazyInitializationException e)
        {
           e.printStackTrace();
            pass = true;
        }

        if(!pass)
            org.junit.Assert.fail();
        sellingManager.deleteMonitors();
        sellingManager.deleteClient(retrievedClient);
    }
}
