package es.weso.data.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import es.weso.data.WeightSchemaDataManagement;
import es.weso.model.WeightSchema;
import es.weso.util.Conf;
import es.weso.util.JenaMemcachedClient;

/**
 * Implementation of {@link WeightSchema} data retrieval operations
 * 
 * @author Alejandro Montes García
 * @since 26/08/2013
 * @version 1.0
 */
public class WeightSchemaDataManager extends AbstractDataManager implements
		WeightSchemaDataManagement {

	public WeightSchemaDataManager() throws IOException {
		client = JenaMemcachedClient.create();
	}

	@Override
	public Collection<WeightSchema> getAllWeightSchemas() {
		return resultSetToWeightSchemaCollection(client.executeQuery(Conf
				.getQuery("allWeightSchemas")));
	}

	@SuppressWarnings("unchecked")
	@Override
	public WeightSchema getWeightSchemaByURI(String uri) {
		return resultSetToWeightSchema(client.executeQuery(Conf
				.getQueryWithFilters("oneWeightSchema",
						Collections.singleton(uri))));
	}
}
