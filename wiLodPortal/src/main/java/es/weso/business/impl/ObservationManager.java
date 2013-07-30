package es.weso.business.impl;

import java.util.Collection;
import java.util.Collections;

import es.weso.business.ObservationManagement;
import es.weso.data.ObservationDataManagement;
import es.weso.model.Observation;

/**
 * Defines the operations that can be performed related to {@link Observation}
 * retrieval
 * 
 * @author Alejandro Montes García
 * @since 03/07/2013
 * @version 1.0
 */
public class ObservationManager implements ObservationManagement {

	private static ObservationDataManagement observationDataManager;

	public void setObservationDataManager(
			ObservationDataManagement observationDataManager) {
		ObservationManager.observationDataManager = observationDataManager;
	}

	public Collection<Observation> getAllObservations() {
		return observationDataManager.getObservationsFrom(
				Collections.<String> emptyList(),
				Collections.<String> emptyList(),
				Collections.<Integer> emptyList());
	}

	public Collection<Observation> getAllObservationsByCountries(
			Collection<String> countries) {
		return observationDataManager.getObservationsFrom(countries,
				Collections.<String> emptyList(),
				Collections.<Integer> emptyList());
	}

	public Collection<Observation> getAllObservationsByCountries(
			Collection<String> countries, Collection<String> indicators) {
		return observationDataManager.getObservationsFrom(countries,
				indicators, Collections.<Integer> emptyList());
	}

	public Collection<Observation> getAllObservationsByCountries(
			Collection<String> countries, Collection<String> indicators,
			Collection<Integer> years) {
		return observationDataManager.getObservationsFrom(countries,
				indicators, years);
	}

	public Collection<Observation> getAllObservationsByYear(
			Collection<Integer> years) {
		return observationDataManager.getObservationsFrom(
				Collections.<String> emptyList(),
				Collections.<String> emptyList(), years);
	}

	public Collection<Observation> getAllObservationsByYear(
			Collection<Integer> years, Collection<String> countries) {
		return observationDataManager.getObservationsFrom(countries,
				Collections.<String> emptyList(), years);
	}

	public Collection<Observation> getAllObservationsByIndicator(
			Collection<String> indicators) {
		return observationDataManager.getObservationsFrom(
				Collections.<String> emptyList(), indicators,
				Collections.<Integer> emptyList());
	}

	public Collection<Observation> getAllObservationsByIndicator(
			Collection<String> indicators, Collection<Integer> years) {
		return observationDataManager.getObservationsFrom(
				Collections.<String> emptyList(), indicators, years);
	}

	public Observation getObservationByURI(String uri) {
		return observationDataManager.getObservationByURI(uri);
	}

}
