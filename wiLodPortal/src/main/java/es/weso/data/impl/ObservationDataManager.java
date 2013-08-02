package es.weso.data.impl;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import es.weso.data.ObservationDataManagement;
import es.weso.model.Observation;
import es.weso.util.Conf;
import es.weso.util.JenaMemcachedClient;

public class ObservationDataManager extends AbstractDataManager implements
		ObservationDataManagement {

	public ObservationDataManager() throws IOException {
		client = JenaMemcachedClient.create();
	}

	public Collection<Observation> getObservationsFrom(
			Collection<String> countries, Collection<String> indicators,
			Collection<Integer> years) {
		Collection<String> yearsStr = new ArrayDeque<String>(years.size());
		for (int year : years) {
			yearsStr.add(Integer.toString(year));
		}
		Collection<Observation> observations = new HashSet<Observation>();
		@SuppressWarnings("unchecked")
		ResultSet rs = client.executeQuery(Conf.getQueryWithFilters(
				"observations", indicators, yearsStr, countries));
		while (rs.hasNext()) {
			observations.add(querySolutionToObservation(rs.next()));
		}
		return observations;
	}

	public Observation getObservationByURI(String uri) {
		return querySolutionToObservation(client.executeQuery(
				Conf.getQuery("observation", uri)).next());
	}

	@Override
	public Collection<String> getCountriesInRegion(String code, String indicator) {
		@SuppressWarnings("unchecked")
		ResultSet rs = client.executeQuery(Conf.getQueryWithFilters("otherCountriesInRegion",
				Collections.singleton(code), Collections.singleton(indicator)));
		Collection<String> codes = new LinkedList<String>();
		while(rs.hasNext()) {
			QuerySolution qs = rs.next();
			codes.add(qs.getLiteral("otherCode").getString());
		}
		return codes;
	}

	@Override
	public Collection<String> getCountriesOutsideRegion(String code,
			String indicator) {
		@SuppressWarnings("unchecked")
		ResultSet rs = client.executeQuery(Conf.getQueryWithFilters("otherCountriesOutsideRegion",
				Collections.singleton(code), Collections.singleton(indicator)));
		Collection<String> codes = new LinkedList<String>();
		while(rs.hasNext()) {
			QuerySolution qs = rs.next();
			codes.add(qs.getLiteral("otherCode").getString());
		}
		return codes;
	}

}
