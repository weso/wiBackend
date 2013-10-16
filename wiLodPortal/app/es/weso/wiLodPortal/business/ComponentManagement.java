package es.weso.wiLodPortal.business;

import java.util.Collection;

import models.Component;

public interface ComponentManagement {

	public Component getOne(String uri);
	
	public Collection<Component> getAll();
}
