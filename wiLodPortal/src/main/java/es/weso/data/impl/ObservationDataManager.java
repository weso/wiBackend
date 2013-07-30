package es.weso.data.impl;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;

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

}
