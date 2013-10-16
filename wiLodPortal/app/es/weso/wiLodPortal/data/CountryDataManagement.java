package es.weso.wiLodPortal.data;

import java.util.Collection;

import models.Country;

/**
 * Defines the operations that can be performed related to {@link Country}
 * retrieval in the data layer
 * 
 * @author Alejandro Montes García
 * @since 02/07/2013
 * @version 1.0
 */
public interface CountryDataManagement extends AuxiliarDataManager {

	/**
	 * Gets all the {@link Country countries} that have at least one observation
	 * 
	 * @return A {@link Collection} with all the {@link Country countries} that
	 *         have at least one observation
	 */
	public Collection<Country> getCountries();

	/**
	 * Gets the data of a specific {@link Country}
	 * 
	 * @param code
	 *            The ISO alpha-3 code of the desired {@link Country}
	 * @return The {@link Country} with the given code
	 */
	public Country getCountry(String code);

	/**
	 * Gets all the {@link Country countries} that have at least one observation
	 * and belong to the specified {@link es.weso.model.CountryGroup
	 * organization}
	 * 
	 * @param name
	 *            The name of the {@link es.weso.model.CountryGroup
	 *            organization}
	 * @return A {@link Collection} with all the {@link Country countries} that
	 *         have at least one observation and belong to the specified
	 *         {@link es.weso.model.CountryGroup organization}
	 */
	public Collection<Country> getCountriesFromOrganization(String name);

	/**
	 * Gets all the {@link Country countries} that have at least one observation
	 * and belong to the specified {@link es.weso.model.CountryGroup continent}
	 * 
	 * @param name
	 *            The name of the {@link es.weso.model.CountryGroup continent}
	 * @return A {@link Collection} with all the {@link Country countries} that
	 *         have at least one observation and belong to the specified
	 *         {@link es.weso.model.CountryGroup continent}
	 */
	public Collection<Country> getCountriesFromContinent(String name);

}
