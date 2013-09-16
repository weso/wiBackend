package es.weso.data;

import java.util.Collection;

import es.weso.model.Subindex;

/**
 * Defines the operations that can be performed related to {@link Subindex}
 * retrieval in the data layer
 * 
 * @author Alejandro Montes García
 * @since 16/09/2013
 * @version 1.0
 */
public interface SubindexDataManagement {

	/**
	 * Gets all the {@link Subindex}s in a hierarchical structure
	 * 
	 * @return
	 */
	public Collection<Subindex> getAllSubindexes();

	/**
	 * Retrieves an {@link Subindex} from the SPARQL endpoint with the given
	 * uri
	 * 
	 * @param uri
	 * @return
	 */
	public Subindex getSubindexByURI(String uri);
}
