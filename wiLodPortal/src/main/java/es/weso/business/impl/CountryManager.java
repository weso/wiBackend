package es.weso.business.impl;

import java.util.ArrayDeque;
import java.util.Collection;

import es.weso.business.CountryManagement;
import es.weso.data.CountryDataManagement;
import es.weso.data.CountryGroupDataManagement;
import es.weso.model.Country;
import es.weso.model.CountryForRegion;
import es.weso.model.CountryGroup;

/**
 * Implementation of {@link Country} management operations
 * 
 * @author Alejandro Montes García
 * @since 02/07/2013
 * @version 1.0
 */
public class CountryManager implements CountryManagement {

	private static CountryDataManagement countryDataManager;
	private static CountryGroupDataManagement countryGroupDataManager;

	public void setCountryDataManager(CountryDataManagement countryDataManager) {
		CountryManager.countryDataManager = countryDataManager;
	}
	
	public void setCountryGroupDataManager(CountryGroupDataManagement countryGroupDataManager) {
		CountryManager.countryGroupDataManager = countryGroupDataManager;
	}

	public Collection<Country> getAllCountries() {
		return countryDataManager.getCountries();
	}

	public Collection<Country> getCountriesFrom(String countryGroup) {
		CountryGroup cg = countryGroupDataManager.getCountryGroup(countryGroup);
		Collection<Country> countries = new ArrayDeque<Country>(cg.getCountries().size());
		for(CountryForRegion nu : cg.getCountries()) {
			try {
				countries.add(getCountry(nu.getIsoCode3()));
			} catch(IllegalArgumentException iae) {
				//Country without observations
			}
		}
		return countries;
	}

	public Country getCountry(String code) {
		return countryDataManager.getCountry(parseCode(code));
	}

	/**
	 * Gives a ISO Code a standard format
	 * 
	 * @param code
	 *            The code to be formatted
	 * @return The code formatted
	 */
	private String parseCode(String code) {
		code = code.trim().toUpperCase();
		if (code.length() == 2) {
			code = toISO3Code(code);
		} else if (code.length() != 3) {
			throw new IllegalArgumentException(code + " is an invalid code");
		}
		return code;
	}

	/**
	 * Transforms an ISO alpha-2 code to an ISO alpha-3 code
	 * 
	 * @param code
	 *            The ISO alpha-2 code to be converted
	 * @return The resulting ISO alpha-3 code
	 */
	private String toISO3Code(String code) {
		return countryDataManager.toISO3Code(code.toUpperCase());
	}
	
}
