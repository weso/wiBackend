package es.weso.wiLodPortal.business;

import java.util.Collection;

import models.WeightSchema;

public interface WeightSchemaManagement {

	public WeightSchema getOne(String uri);
	
	public Collection<WeightSchema> getAll();
}
