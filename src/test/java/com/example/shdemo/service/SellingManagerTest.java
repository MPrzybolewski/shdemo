package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.example.shdemo.domain.Monitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SellingManagerTest {

	@Autowired
	SellingManager sellingManager;

	private final String NAME_1 = "Bolek";
	private final String PIN_1 = "1234";

	private final String NAME_2 = "Lolek";
	private final String PIN_2 = "4321";


	private final String MODEL_1 = "LG";
	private final Double DIAGONAL_1 = 22.4;
	private final int FREQUENCY_1= 60;

	private final String MODEL_2 = "SAMSUNG";
	private final Double DIAGONAL_2 = 24.65;
	private final  int FREQUENCY_2 = 80;


	@Test
	public void addClientCheck() {

		List<Person> retrievedClients = sellingManager.getAllClients();

		// If there is a client with PIN_1 delete it
		for (Person client : retrievedClients) {
			if (client.getPin().equals(PIN_1)) {
				sellingManager.deleteClient(client);
			}
		}

		Person person = new Person();
		person.setFirstName(NAME_1);
		person.setPin(PIN_1);
		// ... other properties here

		// Pin is Unique
		sellingManager.addClient(person);

		Person retrievedClient = sellingManager.findClientByPin(PIN_1);

		assertEquals(NAME_1, retrievedClient.getFirstName());
		assertEquals(PIN_1, retrievedClient.getPin());
		// ... check other properties here
	}

	@Test
	public void addMonitorCheck() {

		Monitor monitor = new Monitor();
		monitor.setFrequency(FREQUENCY_1);
		monitor.setDiagonal(DIAGONAL_1);
		monitor.setModel(MODEL_1);
		// ... other properties here

		Long monitorId = sellingManager.addNewMonitor(monitor);

		Monitor retrievedMonitor = sellingManager.findMonitorById(monitorId);
		assertEquals(DIAGONAL_1, retrievedMonitor.getDiagonal());
		assertEquals(FREQUENCY_1, retrievedMonitor.getFrequency());
		assertEquals(MODEL_1, retrievedMonitor.getModel());
		// ... check other properties here

	}

	@Test
	public void sellMonitorCheck() {

		Person person = new Person();
		person.setFirstName(NAME_2);
		person.setPin(PIN_2);

		sellingManager.addClient(person);

		Person retrievedPerson = sellingManager.findClientByPin(PIN_2);

		Monitor monitor = new Monitor();
		monitor.setDiagonal(DIAGONAL_2);
		monitor.setFrequency(FREQUENCY_2);
		monitor.setModel(MODEL_2);

		Long monitorId = sellingManager.addNewMonitor(monitor);

		sellingManager.sellMonitor(retrievedPerson.getId(), monitorId);

		List<Monitor> ownedMonitors = sellingManager.getOwnedMonitors(retrievedPerson);

		assertEquals(1, ownedMonitors.size());
		assertEquals(FREQUENCY_2, ownedMonitors.get(0).getFrequency());
		assertEquals(DIAGONAL_2, ownedMonitors.get(0).getDiagonal());
		assertEquals(MODEL_2, ownedMonitors.get(0).getModel());
	}

	// @Test -
	public void disposeCarCheck() {
		// Do it yourself
	}

}
