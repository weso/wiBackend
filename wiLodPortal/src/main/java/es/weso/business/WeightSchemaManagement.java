package es.weso.business;

import java.util.Collection;

import es.weso.model.WeightSchema;

public interface WeightSchemaManagement {

	public WeightSchema getOne(String uri);
	
	public Collection<WeightSchema> getAll();
}
