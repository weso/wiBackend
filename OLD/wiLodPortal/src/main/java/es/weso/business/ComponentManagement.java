package es.weso.business;

import java.util.Collection;

import es.weso.model.Component;

public interface ComponentManagement {

	public Component getOne(String uri);
	
	public Collection<Component> getAll();
}
