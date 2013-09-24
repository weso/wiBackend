package es.weso.business.impl;

import java.util.Collection;

import es.weso.business.SubindexManagement;
import es.weso.data.SubindexDataManagement;
import models.Subindex;

public class SubindexManager implements SubindexManagement {

	private static SubindexDataManagement subindexDataManager;

	public void setSubindexDataManager(
			SubindexDataManagement subindexDataManager) {
		SubindexManager.subindexDataManager = subindexDataManager;
	}

	@Override
	public Subindex getOne(String uri) {
		return subindexDataManager.getSubindexByURI(uri);
	}

	@Override
	public Collection<Subindex> getAll() {
		return subindexDataManager.getAllSubindexes();
	}

}
