package es.weso.wiLodPortal.data;

import java.util.Collection;

import models.Component;

/**
 * Defines the operations that can be performed related to {@link Component}
 * retrieval in the data layer
 * 
 * @author Alejandro Montes García
 * @since 26/08/2013
 * @version 1.0
 */
public interface ComponentDataManagement {

	/**
	 * Gets all the {@link Component}s in a hierarchical structure
	 * 
	 * @return
	 */
	public Collection<Component> getAllComponents();

	/**
	 * Retrieves an {@link Component} from the SPARQL endpoint with the given
	 * uri
	 * 
	 * @param uri
	 * @return
	 */
	public Component getComponentByURI(String uri);
}
