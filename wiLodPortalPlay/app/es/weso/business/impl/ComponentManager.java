package es.weso.business.impl;

import java.io.IOException;
import java.util.Collection;

import models.Component;
import es.weso.business.ComponentManagement;
import es.weso.data.ComponentDataManagement;
import es.weso.data.impl.ComponentDataManager;

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
