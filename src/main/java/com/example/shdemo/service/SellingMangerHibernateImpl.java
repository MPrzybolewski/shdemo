package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.shdemo.domain.Monitor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Person;

@Component
@Transactional
public class SellingMangerHibernateImpl implements SellingManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addClient(Person person) {
		person.setId(null);
		sessionFactory.getCurrentSession().persist(person);
	}
	
	@Override
	public void deleteClient(Person person) {
		person = (Person) sessionFactory.getCurrentSession().get(Person.class,
				person.getId());
		
		// lazy loading here
		for (Monitor monitor : person.getMonitors()) {
			monitor.setSold(false);
			sessionFactory.getCurrentSession().update(monitor);
		}
		sessionFactory.getCurrentSession().delete(person);
	}

	@Override
	public List<Monitor> getOwnedMonitors(Person person) {
		person = (Person) sessionFactory.getCurrentSession().get(Person.class,
				person.getId());
		// lazy loading here - try this code without (shallow) copying
		List<Monitor> monitors = new ArrayList<Monitor>(person.getMonitors());
		return monitors;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getAllClients() {
		return sessionFactory.getCurrentSession().getNamedQuery("person.all")
				.list();
	}

	@Override
	public Person findClientByPin(String pin) {
		return (Person) sessionFactory.getCurrentSession().getNamedQuery("person.byPin").setString("pin", pin).uniqueResult();
	}


	@Override
	public Long addNewMonitor(Monitor monitor) {
		monitor.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(monitor);
	}

	@Override
	public void sellMonitor(Long personId, Long carId) {
		Person person = (Person) sessionFactory.getCurrentSession().get(
				Person.class, personId);
		Monitor monitor = (Monitor) sessionFactory.getCurrentSession()
				.get(Monitor.class, carId);
		monitor.setSold(true);
		person.getMonitors().add(monitor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Monitor> getAvailableMonitors() {
		return sessionFactory.getCurrentSession().getNamedQuery("monitor.available")
				.list();
	}
	@Override
	public void disposeMonitor(Person person, Monitor monitor) {

		person = (Person) sessionFactory.getCurrentSession().get(Person.class,
				person.getId());
		monitor = (Monitor) sessionFactory.getCurrentSession().get(Monitor.class,
				monitor.getId());

		Monitor toRemove = null;
		// lazy loading here (person.getCars)
		for (Monitor aMonitor : person.getMonitors())
			if (aMonitor.getId().compareTo(monitor.getId()) == 0) {
				toRemove = aMonitor;
				break;
			}

		if (toRemove != null)
			person.getMonitors().remove(toRemove);

		monitor.setSold(false);
	}

	@Override
	public Monitor findMonitorById(Long id) {
		return (Monitor) sessionFactory.getCurrentSession().get(Monitor.class, id);
	}

}
