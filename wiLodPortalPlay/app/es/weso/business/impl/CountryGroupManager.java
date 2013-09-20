package es.weso.business.impl;

import java.util.Collection;

import es.weso.business.CountryGroupManagement;
import es.weso.data.CountryGroupDataManagement;
import models.CountryGroup;

/**
 * Implementation of {@link CountryGroup} management operations
 * 
 * @author Alejandro Montes García
 * @since 02/07/2013
 * @version 1.0
 */
public class CountryGroupManager implements CountryGroupManagement {

	private static CountryGroupDataManagement countryGroupDataManager;

	public void setCountryGroupDataManager(
			CountryGroupDataManagement countryGroupDataManager) {
		CountryGroupManager.countryGroupDataManager = countryGroupDataManager;
	}

	public Collection<CountryGroup> getAllCountryGroups() {
		return countryGroupDataManager.getContinents();
	}

	public CountryGroup getCountryGroup(String name) {
		return countryGroupDataManager.getCountryGroup(name);
	}

}
