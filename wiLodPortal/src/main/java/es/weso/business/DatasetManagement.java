package es.weso.business;

import java.util.Collection;

import es.weso.model.Dataset;

public interface DatasetManagement {

	public Dataset getOne(String uri);
	
	public Collection<Dataset> getAll();
}
