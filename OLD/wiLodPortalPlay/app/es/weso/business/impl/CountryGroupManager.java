package es.weso.business.impl;

import java.io.IOException;
import java.util.Collection;

import models.CountryGroup;
import es.weso.business.CountryGroupManagement;
import es.weso.data.CountryGroupDataManagement;
import es.weso.data.impl.CountryGroupDataManager;

/**
 * Implementation of {@link CountryGroup} management operations
 * 
 * @author Alejandro Montes García
 * @since 02/07/2013
 * @version 1.0
 */
public class CountryGroupManager implements CountryGroupManagement {

	private static CountryGroupDataManagement countryGroupDataManager;
	private static CountryGroupManager instance;
	
	private CountryGroupManager(){}
	
	public static CountryGroupManager getInstance() {
		if(instance == null) {
			instance = new CountryGroupManager();
			try {
				countryGroupDataManager = new CountryGroupDataManager();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}
	
	public Collection<CountryGroup> getAllCountryGroups() {
		return countryGroupDataManager.getContinents();
	}

	public CountryGroup getCountryGroup(String name) {
		return countryGroupDataManager.getCountryGroup(name);
	}

}
