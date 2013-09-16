package es.weso.business;

import java.util.Collection;

import es.weso.model.Subindex;

public interface SubindexManagement {

	public Subindex getOne(String uri);
	
	public Collection<Subindex> getAll();
}
