package es.weso.wiLodPortal.business;

import java.util.Collection;

import models.Country;

/**
 * Defines the operations that can be performed related to {@link Country}
 * retrieval
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
public interface CountryManagement {

	/**
	 * Gets all the {@link Country countries} that have at least one observation
	 * 
	 * @return A {@link Collection} with all the {@link Country countries} that
	 *         have at least one observation
	 */
	public Collection<Country> getAllCountries();

	/**
	 * Gets all the {@link Country countries} that have at least one observation
	 * and belong to the specified {@link es.weso.model.CountryGroup
	 * CountryGroup}
	 * 
	 * @param countryGroup
	 *            The name of the {@link es.weso.model.CountryGroup
	 *            CountryGroup}
	 * @return A {@link Collection} with all the {@link Country countries} that
	 *         have at least one observation and belong to the specified
	 *         {@link es.weso.model.CountryGroup CountryGroup}
	 */
	public Collection<Country> getCountriesFrom(String countryGroup);

	/**
	 * Gets the data of a specific {@link Country}
	 * 
	 * @param code
	 *            The code of the desired {@link Country}
	 * @return The {@link Country} with the given code
	 */
	public Country getCountry(String code);

}
