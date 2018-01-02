package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Monitor;
import com.example.shdemo.domain.Person;

public interface SellingManager {
	
	void addClient(Person person);
	List<Person> getAllClients();
	void deleteClient(Person person);
	Person findClientByPin(String pin);
	
	Long addNewMonitor(Monitor monitor);
	List<Monitor> getAvailableMonitors();
	void disposeMonitor(Person person, Monitor monitor);
	Monitor findMonitorById(Long id);

	List<Monitor> getOwnedMonitors(Person person);
	void sellMonitor(Long personId, Long carId);

	Monitor getMonitorById(long id);

	void deleteMonitors();


}
