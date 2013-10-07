package es.weso.business.impl;

import java.io.IOException;
import java.util.Collection;

import models.Dataset;
import es.weso.business.DatasetManagement;
import es.weso.data.DatasetDataManagement;
import es.weso.data.impl.DatasetDataManager;

public class DatasetManager implements DatasetManagement {

	private static DatasetDataManagement datasetDataManager;
	private static DatasetManager instance;
	
	private DatasetManager() {}
	
	public static DatasetManager getInstance() {
		if(instance == null) {
			try {
				instance = new DatasetManager();
				datasetDataManager = new DatasetDataManager();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
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
