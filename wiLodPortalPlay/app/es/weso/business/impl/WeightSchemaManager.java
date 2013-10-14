package es.weso.business.impl;

import java.io.IOException;
import java.util.Collection;

import es.weso.business.WeightSchemaManagement;
import es.weso.data.WeightSchemaDataManagement;
import es.weso.data.impl.WeightSchemaDataManager;
import models.WeightSchema;

public class WeightSchemaManager implements WeightSchemaManagement {

	private static WeightSchemaDataManagement weightSchemaDataManager;
	private static WeightSchemaManager instance;

	private WeightSchemaManager() {
	}

	public static WeightSchemaManager getInstance() {
		if (instance == null) {
			try {
				instance = new WeightSchemaManager();
				weightSchemaDataManager = new WeightSchemaDataManager();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	@Override
	public WeightSchema getOne(String uri) {
		return weightSchemaDataManager.getWeightSchemaByURI(uri);
	}

	@Override
	public Collection<WeightSchema> getAll() {
		return weightSchemaDataManager.getAllWeightSchemas();
	}

}
