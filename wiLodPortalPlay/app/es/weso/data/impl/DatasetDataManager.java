package es.weso.data.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.hp.hpl.jena.query.ResultSet;

import es.weso.data.DatasetDataManagement;
import models.Dataset;
import es.weso.util.Conf;
import es.weso.util.JenaMemcachedClient;

/**
 * Implementation of {@link Dataset} data retrieval operations
 * 
 * @author Alejandro Montes García
 * @since 26/08/2013
 * @version 1.0
 */
public class DatasetDataManager extends AbstractDataManager implements
		DatasetDataManagement {

	public DatasetDataManager() throws IOException {
		client = JenaMemcachedClient.create();
	}

	@Override
	public Collection<Dataset> getAllDatasets() {
		ResultSet rs = client.executeQuery(Conf.getQuery("allDatasets"));
		Collection<Dataset> datasets = new HashSet<Dataset>();
		while (rs.hasNext()) {
			datasets.add(querySolutionToDataset(rs.next()));
		}
		return datasets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dataset getDatasetByURI(String uri) {
		return querySolutionToDataset(client.executeQuery(
				Conf.getQueryWithFilters("oneDataset",
						Collections.singleton(uri))).next());
	}
}
