package es.weso.business.impl;

import java.util.Collection;

import es.weso.business.DatasetManagement;
import es.weso.data.DatasetDataManagement;
import models.Dataset;

public class DatasetManager implements DatasetManagement {

	private static DatasetDataManagement datasetDataManager;

	public void setDatasetDataManager(DatasetDataManagement datasetDataManager) {
		DatasetManager.datasetDataManager = datasetDataManager;
	}

	@Override
	public Dataset getOne(String uri) {
		return datasetDataManager.getDatasetByURI(uri);
	}

	@Override
	public Collection<Dataset> getAll() {
		return datasetDataManager.getAllDatasets();
	}

}
