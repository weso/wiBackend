package es.weso.data;

import java.util.Collection;

import es.weso.model.Observation;

/**
 * Defines the operations that can be performed related to {@link Observation}
 * retrieval in the data layer
 * 
 * @author Alejandro Montes García
 * @since 03/07/2013
 * @version 1.0
 */
public interface ObservationDataManagement {

	/**
	 * Get all the {@link Observation}s that refer to a set of
	 * {@link es.weso.model.Country countries} and have a specific code within a
	 * specific period of time
	 * 
	 * @param countries
	 *            The desired {@link es.weso.model.Country countries}
	 * @param indicatos
	 *            The desired observations
	 * @param years
	 *            The desired years
	 * @return A {@link Collection} with all the {@link Observation}s that refer
	 *         to a set of {@link es.weso.model.Country countries} and have a
	 *         specific code within a specific period of time
	 */
	public Collection<Observation> getObservationsFrom(
			Collection<String> countries, Collection<String> observations,
			Collection<Integer> years);

	/**
	 * Gets an {@link Observation} identified by a uri
	 * 
	 * @param uri
	 *            The uri slice that identifies the {@link Observation}
	 * @return The {@link Observation} that matches with the URI slice
	 */
	public Observation getObservationByURI(String uri);

	/**
	 * Gets the country codes of all the countries inside the region of the
	 * specified country that have the given indicator
	 * 
	 * @param code
	 * @param indicator
	 * @param year
	 * @return
	 */
	public Collection<String> getCountriesInRegion(String code, String indicator, Integer year);
	
	/**
	 * Gets the country codes of all the countries outside the region of the
	 * specified country that have the given indicator
	 * 
	 * @param code
	 * @param indicator
	 * @param year
	 * @return
	 */
	public Collection<String> getCountriesOutsideRegion(String code, String indicator, Integer year);
	
	/**
	 * Gets the ranking of an indicator for a specific year
	 * @param indicator
	 * @param year
	 * @return
	 */
	public Collection<Observation> getRanking(String indicator, Integer year);
}
