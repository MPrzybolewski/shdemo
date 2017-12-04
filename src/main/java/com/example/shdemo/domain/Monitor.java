package com.example.shdemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "monitor.available", query = "Select m from Monitor m where m.sold = false")
})
public class Monitor {

	private Long id;
	private Double diagonal;
	private String model;
	private int frequency;
	private boolean sold = false;

	public Monitor() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(Double diagonal) {
		this.diagonal = diagonal;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public boolean isSold() {
		return sold;
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}
}
