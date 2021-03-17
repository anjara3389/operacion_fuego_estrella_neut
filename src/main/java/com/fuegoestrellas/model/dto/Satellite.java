package com.fuegoestrellas.model.dto;

public class Satellite {
	private String name;
	private Float distance;
	private String[] message;
	
	public Satellite() {
		
	}
	
	public Satellite(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public String[] getMessage() {
		return message;
	}
	public void setMessage(String[] messages) {
		this.message = messages;
	}
}
