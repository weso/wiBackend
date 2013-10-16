package es.weso.wiLodPortal.business.impl;

import java.io.IOException;
import java.util.Collection;

import models.Component;
import es.weso.wiLodPortal.business.ComponentManagement;
import es.weso.wiLodPortal.data.ComponentDataManagement;
import es.weso.wiLodPortal.data.impl.ComponentDataManager;

public class ComponentManager implements ComponentManagement {

	private static ComponentDataManagement componentDataManager;
	private static ComponentManager instance;
	
	private ComponentManager() {}
	
	public static ComponentManager getInstance() {
		if(instance == null) {
			instance = new ComponentManager();
			try {
				componentDataManager = new ComponentDataManager();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	@Override
	public Component getOne(String uri) {
		return componentDataManager.getComponentByURI(uri);
	}

	@Override
	public Collection<Component> getAll() {
		return componentDataManager.getAllComponents();
	}

}
