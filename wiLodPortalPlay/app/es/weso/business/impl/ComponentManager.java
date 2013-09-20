package es.weso.business.impl;

import java.util.Collection;

import es.weso.business.ComponentManagement;
import es.weso.data.ComponentDataManagement;
import models.Component;

public class ComponentManager implements ComponentManagement {

	private static ComponentDataManagement componentDataManager;

	public void setComponentDataManager(
			ComponentDataManagement componentDataManager) {
		ComponentManager.componentDataManager = componentDataManager;
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
