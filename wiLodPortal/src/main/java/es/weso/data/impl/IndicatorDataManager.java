package es.weso.data.impl;

import java.io.IOException;
import java.util.Collections;

import es.weso.data.IndicatorDataManagement;
import es.weso.model.Indicator;
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

}
