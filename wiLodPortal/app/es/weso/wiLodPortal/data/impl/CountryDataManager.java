package es.weso.wiLodPortal.data.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.hp.hpl.jena.query.ResultSet;

import models.Country;
import es.weso.wiLodPortal.data.CountryDataManagement;
import es.weso.wiLodPortal.util.Conf;
import es.weso.wiLodPortal.util.JenaMemcachedClient;

/**
 * Implementation of {@link Country} data retrieval operations
 * 
 * @author Alejandro Montes García
 * @since 04/07/2013
 * @version 1.0
 */
public class CountryDataManager extends AbstractDataManager implements
		CountryDataManagement {

	public CountryDataManager() throws IOException {
		client = JenaMemcachedClient.create();
	}

	public Collection<Country> getCountries() {
		ResultSet rs = client.executeQuery(Conf.getQuery("allCountries"));
		Collection<Country> countries = new HashSet<Country>();
		while (rs.hasNext()) {
			countries.add(querySolutionToCountry(rs.next()));
		}
		return countries;
	}

	@SuppressWarnings("unchecked")
	public Country getCountry(String code) {
		return resultSetToCountry(client.executeQuery(Conf.getQueryWithFilters(
				"oneCountry", Collections.singleton(code))));
	}

	public Collection<Country> getCountriesFromOrganization(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Country> getCountriesFromContinent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
