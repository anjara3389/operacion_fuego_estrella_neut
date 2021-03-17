package com.fuegoestrellas.model.dto;

public class RequestSplit {
	private Float distance;
	private String[] message;

	public RequestSplit() {

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

	public void setMessage(String[] message) {
		this.message = message;
	}

}
