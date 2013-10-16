package es.weso.wiLodPortal.data.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import models.Subindex;
import es.weso.wiLodPortal.data.SubindexDataManagement;
import es.weso.wiLodPortal.util.Conf;
import es.weso.wiLodPortal.util.JenaMemcachedClient;

/**
 * Implementation of {@link Subindex} data retrieval operations
 * 
 * @author Alejandro Montes García
 * @since 16/09/2013
 * @version 1.0
 */
public class SubindexDataManager extends AbstractDataManager implements
		SubindexDataManagement {

	public SubindexDataManager() throws IOException {
		client = JenaMemcachedClient.create();
	}

	@Override
	public Collection<Subindex> getAllSubindexes() {
		return resultSetToSubindexCollection(client.executeQuery(Conf
				.getQuery("allSubindexes")));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Subindex getSubindexByURI(String uri) {
		return resultSetToSubindex(client
				.executeQuery(Conf.getQueryWithFilters("oneSubindex",
						Collections.singleton(uri))));
	}
}
