package es.weso.data.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import es.weso.data.IndicatorDataManagement;
import es.weso.model.Indicator;
import es.weso.model.JSONHashMap;
import es.weso.util.Conf;
import es.weso.util.JenaMemcachedClient;

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
	public JSONHashMap<String, JSONHashMap<String, Collection<String>>> getAllIndicators() {
		JSONHashMap<String, JSONHashMap<String, Collection<String>>> result = new JSONHashMap<String, JSONHashMap<String, Collection<String>>>();
		ResultSet rs = client.executeQuery(Conf.getQuery("allIndicators"));
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
}
