package es.weso.business.impl;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;

import models.Country;
import models.CountryForRegion;
import models.CountryGroup;
import es.weso.business.CountryManagement;
import es.weso.data.CountryDataManagement;
import es.weso.data.CountryGroupDataManagement;
import es.weso.data.impl.CountryDataManager;
import es.weso.data.impl.CountryGroupDataManager;

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
	private static CountryManager instance;
	
	private CountryManager(){}
	
	public static CountryManager getInstance() {
		if(instance == null) {
			instance = new CountryManager();
			try {
				countryDataManager = new CountryDataManager();
				countryGroupDataManager = new CountryGroupDataManager();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
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
