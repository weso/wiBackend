package es.weso.wiLodPortal.data.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import models.Indicator;
import models.JSONHashMap;
import es.weso.wiLodPortal.data.IndicatorDataManagement;
import es.weso.wiLodPortal.util.Conf;
import es.weso.wiLodPortal.util.JenaMemcachedClient;

/**
 * Implementation of {@link Indicator} data retrieval operations
 * 
 * @author Alejandro Montes García
 * @since 14/08/2013
 * @version 1.0
 */
public class IndicatorDataManager extends AbstractDataManager implements
		IndicatorDataManagement {

	public IndicatorDataManager() throws IOException {
		client = JenaMemcachedClient.create();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Indicator getIndicator(String label) {
		return querySolutionToIndicator(client.executeQuery(
				Conf.getQueryWithFilters("indicator",
						Collections.singleton(label))).next());
	}

	@Override
	public JSONHashMap<String, JSONHashMap<String, Collection<String>>> getHierarchy() {
		JSONHashMap<String, JSONHashMap<String, Collection<String>>> result = new JSONHashMap<String, JSONHashMap<String, Collection<String>>>();
		ResultSet rs = client.executeQuery(Conf.getQuery("indicatorsHierarchy"));
		while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			String subindex = getString(qs, "subindexLabel");
			String element = getString(qs, "elementLabel");
			String indicator = getString(qs, "indicatorLabel");
			if (result.containsKey(subindex)) {
				JSONHashMap<String, Collection<String>> elements = result
						.get(subindex);
				addIndicator(result, subindex, element, indicator, elements,
						elements.get(element));
			} else {
				addIndicator(result, subindex, element, indicator, null, null);
			}
		}
		return result;
	}

	private void addIndicator(
			JSONHashMap<String, JSONHashMap<String, Collection<String>>> result,
			String subindex, String element, String indicator,
			JSONHashMap<String, Collection<String>> elements,
			Collection<String> indicators) {
		if (indicators == null) {
			indicators = new LinkedList<String>();
		}
		if (elements == null) {
			elements = new JSONHashMap<String, Collection<String>>();
		}
		indicators.add(indicator);
		elements.put(element, indicators);
		result.put(subindex, elements);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Indicator getIndicatorByURI(String uri) {
		return querySolutionToIndicator(client.executeQuery(
				Conf.getQueryWithFilters("indicatorByURI",
						Collections.singleton(uri))).next());
	}

	@Override
	public Collection<Indicator> geAllIndicators() {
		Collection<Indicator> indicators = new HashSet<Indicator>();
		ResultSet rs = client.executeQuery(Conf.getQuery("allIndicators"));
		while(rs.hasNext()) {
			indicators.add(querySolutionToIndicator(rs.next()));
		}
		return indicators;
	}
}
