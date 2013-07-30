package es.weso.business.impl;

import java.util.Collection;

import es.weso.business.CountryGroupManagement;
import es.weso.data.CountryGroupDataManagement;
import es.weso.model.CountryGroup;

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
		Collection<CountryGroup> groups = getAllContinents();
		groups.addAll(getAllOrganizations());
		return groups;
	}

	public Collection<CountryGroup> getAllCountryGroupsByType(
			boolean isContinent) {
		if (isContinent) {
			return getAllContinents();
		} else {
			return getAllOrganizations();
		}
	}

	public CountryGroup getCountryGroup(String name) {
		return countryGroupDataManager.getCountryGroup(name);
	}

	/**
	 * Gets all the continents available
	 * 
	 * @return A {@link Collection} containing all the continents available
	 */
	private Collection<CountryGroup> getAllContinents() {
		return countryGroupDataManager.getContinents();
	}

	/**
	 * Gets all the organizations available
	 * 
	 * @return A {@link Collection} containing all the organizations available
	 */
	private Collection<CountryGroup> getAllOrganizations() {
		return countryGroupDataManager.getOrganizations();
	}

}
