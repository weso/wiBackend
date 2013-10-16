package es.weso.wiLodPortal.data.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import models.Component;
import es.weso.wiLodPortal.data.ComponentDataManagement;
import es.weso.wiLodPortal.util.Conf;
import es.weso.wiLodPortal.util.JenaMemcachedClient;

/**
 * Implementation of {@link Component} data retrieval operations
 * 
 * @author Alejandro Montes García
 * @since 26/08/2013
 * @version 1.0
 */
public class ComponentDataManager extends AbstractDataManager implements
		ComponentDataManagement {

	public ComponentDataManager() throws IOException {
		client = JenaMemcachedClient.create();
	}

	@Override
	public Collection<Component> getAllComponents() {
		return resultSetToComponentCollection(client.executeQuery(Conf
				.getQuery("allComponents")));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Component getComponentByURI(String uri) {
		return resultSetToComponent(client
				.executeQuery(Conf.getQueryWithFilters("oneComponent",
						Collections.singleton(uri))));
	}
}
