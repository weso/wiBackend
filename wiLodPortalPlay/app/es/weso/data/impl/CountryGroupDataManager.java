package es.weso.data.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import es.weso.data.CountryGroupDataManagement;
import models.CountryGroup;
import es.weso.util.Conf;
import es.weso.util.JenaMemcachedClient;

/**
 * Implementation of {@link CountryGroup} data retrieval operations
 * 
 * @author Alejandro Montes García
 * @since 08/07/2013
 * @version 1.0
 */
public class CountryGroupDataManager extends AbstractDataManager implements
		CountryGroupDataManagement {

	public CountryGroupDataManager() throws IOException {
		client = JenaMemcachedClient.create();
	}

	@SuppressWarnings("unchecked")
	public CountryGroup getCountryGroup(String name) {
		return resultSetToCountryGroup(client.executeQuery(Conf
				.getQueryWithFilters("countryGroup", Collections.singleton(name))));
	}

	public Collection<CountryGroup> getContinents() {
		return resultSetToContinentCollection(client.executeQuery(Conf
				.getQuery("allContinents")));
	}

}
