package com.fuegoestrellas.services;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import com.fuegoestrellas.model.dto.Satellite;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class OperacionService {
	private static final double[] KENOBY_POSITION = {-500, -200};
	private static final double[] SKYWALKER_POSITION = {100, -100};
	private static final double[] SOLO_POSITION = {500, 100};
	
	/**
	 * Obtiene las coordenadas de una nave que se encuentra entre los satélites Kenoby, Skywalker y Solo
	 * @param distancias entre la nave y cada uno de los satélites
	 * @return Coordenadas de la nave origen
	 * @author Andrea Rosero
	 * @since 17/03/2021
	 */
	public float[] GetLocation(float[] distances) {
		
			double[] doubleDistances = new double[distances.length];
			for (int i = 0; i < distances.length; i++) {
				doubleDistances[i] = (float) distances[i];
			}

			double[][] positions = new double[][] { KENOBY_POSITION, SKYWALKER_POSITION, SOLO_POSITION };

			NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
					new TrilaterationFunction(positions, doubleDistances), new LevenbergMarquardtOptimizer());
			Optimum optimum = solver.solve();
			double[] centroid = optimum.getPoint().toArray();

			float[] floatCentroid = new float[centroid.length];
			for (int i = 0; i < centroid.length; i++) {
				floatCentroid[i] = (float) centroid[i];
			}
			return floatCentroid;
	}
	
	/**
	 * Retorna el mensaje de la nave según los mensajes recibidos de cada satélite
	 * @param distancias entre la nave y cada uno de los satélites
	 * @return Coordenadas de la nave origen
	 * @author Andrea Rosero
	 * @since 17/03/2021
	 */
	public String getMessage(String[] arr1, String[] arr2, String[] arr3) {
		List<String[]> listaMensajes = new ArrayList<String[]>();
		listaMensajes.add(arr1);
		listaMensajes.add(arr2);
		listaMensajes.add(arr3);
		int tamanio = calculateMessageSize(listaMensajes);

		String msj = "";

		for (int i = 0; i < tamanio; i++) {
			List<String> linea = new ArrayList<String>();

			for (String[] strings : listaMensajes) {
				if (strings.length > i && strings[i] != null) {
					linea.add(strings[i]);
				}
			}
			String valorPosicion = "";
			Map<String, Long> occurrences = linea.stream()
					.collect(Collectors.groupingBy(w -> w, Collectors.counting()));
			for (Map.Entry<String, Long> entry : occurrences.entrySet()) {
				valorPosicion = entry.getKey();
			}

			msj += valorPosicion + " ";

			for (int j = 0; j < listaMensajes.size(); j++) {
				String[] strings = listaMensajes.get(j);
				if (strings.length > i && !strings[i].equals(valorPosicion)) {
					if (strings.length != tamanio) { 
						addPos(strings, i, valorPosicion);
					} else { 
						strings[i] = valorPosicion;
					}
				}
			}
		}
		return msj;
	}

	/**
	 * Calcula el tamaño del mensaje transmitido desde la nave según los mensajes que se recibe de cada satélite
	 * @param Mensajes recibidos de cada satélite
	 * @return tamaño del mensaje
	 * @author Andrea Rosero
	 * @since 17/03/2021
	 */
	private Integer calculateMessageSize(List<String[]> listaMensajes) {
		List<Integer> lstSize = new ArrayList<>();
		for (String[] strings : listaMensajes) {
			lstSize.add(strings.length);
		}
		Collections.sort(lstSize);
		int max_count = 1, res = lstSize.get(0);
		int curr_count = 1;

		for (int i = 1; i < lstSize.size(); i++) {
			if (lstSize.get(i) == lstSize.get(i - 1))
				curr_count++;
			else {
				if (curr_count > max_count) {
					max_count = curr_count;
					res = lstSize.get(i - 1);
				}
				curr_count = 1;
			}
		}

		if (curr_count > max_count) {
			max_count = curr_count;
			res = lstSize.get(lstSize.size() - 1);
		}

		return res;
	}

	/**
	 * Agrega un valor al inicio de una lista
	 * @param lista
	 * @param pos posición 
	 * @param value valor a insertar en la lista 
	 * @author Andrea Rosero
	 * @since 17/03/2021
	 */
	private void addPos(String[] array, int pos, String value) {
		String prevValue = value;
		for (int i = pos; i < array.length; i++) {
			String tmp = prevValue;
			prevValue = array[i];
			array[i] = tmp;
		}
	}
}
