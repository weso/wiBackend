package es.weso.business;

import java.util.Collection;

import models.Subindex;

public interface SubindexManagement {

	public Subindex getOne(String uri);
	
	public Collection<Subindex> getAll();
}
