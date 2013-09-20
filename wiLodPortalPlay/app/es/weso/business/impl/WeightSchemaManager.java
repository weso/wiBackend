package es.weso.business.impl;

import java.util.Collection;

import es.weso.business.WeightSchemaManagement;
import es.weso.data.WeightSchemaDataManagement;
import models.WeightSchema;

public class WeightSchemaManager implements WeightSchemaManagement {

	private static WeightSchemaDataManagement weightSchemaDataManager;

	public void setWeightSchemaDataManager(WeightSchemaDataManagement weightSchemaDataManager) {
		WeightSchemaManager.weightSchemaDataManager = weightSchemaDataManager;
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
