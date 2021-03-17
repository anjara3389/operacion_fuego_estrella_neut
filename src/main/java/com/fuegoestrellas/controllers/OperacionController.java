package com.fuegoestrellas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fuegoestrellas.model.dto.RequestSatellite;
import com.fuegoestrellas.model.dto.RequestSplit;
import com.fuegoestrellas.model.dto.ResponseTopSecret;
import com.fuegoestrellas.model.dto.Satellite;
import com.fuegoestrellas.services.OperacionService;

/**
 * Servicio operación fuego de estrellas
 * @author Andrea Rosero
 * @since 17/03/2021
 */
@CrossOrigin(allowedHeaders = "*")
@RestController
public class OperacionController {
	@Autowired
	private OperacionService operacionService;
	
	private Satellite kenobiSatellite = new Satellite("kenobi");
	private Satellite skywalkerSatellite = new Satellite("skywalker");
	private Satellite soloSatellite = new Satellite("solo");

	/**
	 * Obtiene la posición y mensaje de una nave según la información de los satélites
	 * @param satelites json con la información de los satélites
	 * @return retorna json con la posición y mensaje de la nave
	 * @author Andrea Rosero
	 */
	@PostMapping("/topsecret")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseTopSecret> getPositionAndMessage(@RequestBody RequestSatellite satellites) {
		try {
			ResponseTopSecret response = new ResponseTopSecret();

			String message = operacionService.getMessage(satellites.getSatellites()[0].getMessage(),
					satellites.getSatellites()[1].getMessage(), satellites.getSatellites()[2].getMessage());
			response.setMessage(message);

			float[] distances = new float[satellites.getSatellites().length];
			for (int i = 0; i < satellites.getSatellites().length; i++) {
				distances[i] = satellites.getSatellites()[i].getDistance();
			}

			response.setPosition(operacionService.GetLocation(distances));
			return new ResponseEntity<ResponseTopSecret>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseTopSecret>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Obtiene la posición y mensaje de una nave
	 * @param satellite_name String con el nombre del satélite que envía la información
	 * @param data mensaje y distancia del satélite
	 * @return retorna respuesta de creación
	 * @author Andrea Rosero
	 */
	@PostMapping("/topsecret_split/{satellite_name}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> addPositionAndMessageSplit(@PathVariable(value = "satellite_name") String satelliteName,
			@RequestBody RequestSplit data) {
		if (satelliteName.equals(kenobiSatellite.getName()) ) {
			kenobiSatellite.setDistance(data.getDistance());
			kenobiSatellite.setMessage(data.getMessage());
		}
		else if (satelliteName.equals(skywalkerSatellite.getName())) {
			skywalkerSatellite.setDistance(data.getDistance());
			skywalkerSatellite.setMessage(data.getMessage());
		}
		else if (satelliteName.equals(soloSatellite.getName())) {
			soloSatellite.setDistance(data.getDistance());
			soloSatellite.setMessage(data.getMessage());
		}else {
			return new ResponseEntity<String>("Satélite no encontrado",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Satélite creado",HttpStatus.OK);
	}
	
	/**
	 * Obtiene la posición y mensaje de una nave
	 * @return retorna json con la posición y mensaje de la nave
	 * @author Andrea Rosero
	 */
	@GetMapping("/topsecret_split")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> getPositionAndMessageSplit() {
		try {
			ResponseTopSecret response = new ResponseTopSecret();
			if (kenobiSatellite.getDistance() != null && skywalkerSatellite.getDistance() != null
					&& soloSatellite.getDistance() != null && kenobiSatellite.getMessage() != null
					&& skywalkerSatellite.getMessage() != null && soloSatellite.getMessage() != null) {
				
				String message = operacionService.getMessage(kenobiSatellite.getMessage(),
						skywalkerSatellite.getMessage(), soloSatellite.getMessage());
				response.setMessage(message);

				float[] distances = new float[3];
				distances[0] = kenobiSatellite.getDistance();
				distances[1] = skywalkerSatellite.getDistance();
				distances[2] = soloSatellite.getDistance();
				response.setPosition(operacionService.GetLocation(distances));
				return new ResponseEntity<ResponseTopSecret>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No hay suficiente información",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<ResponseTopSecret>(HttpStatus.NOT_FOUND);
		}
	}
}
