package com.fuegoestrellas.model.dto;

public class RequestSatellite {
	private Satellite[] satellites;

	public RequestSatellite() {

	}

	public Satellite[] getSatellites() {
		return satellites;
	}

	public void setSatellites(Satellite[] satellites) {
		this.satellites = satellites;
	}
}
