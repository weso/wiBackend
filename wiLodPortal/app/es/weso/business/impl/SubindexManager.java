package es.weso.business.impl;

import java.io.IOException;
import java.util.Collection;

import es.weso.business.SubindexManagement;
import es.weso.data.SubindexDataManagement;
import es.weso.data.impl.SubindexDataManager;
import models.Subindex;

public class SubindexManager implements SubindexManagement {

	private static SubindexDataManagement subindexDataManager;
	private static SubindexManager instance;
	
	private SubindexManager() {}
	
	public static SubindexManager getInstance() {
		if(instance == null) {
			try {
				instance = new SubindexManager();
				subindexDataManager = new SubindexDataManager();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
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
