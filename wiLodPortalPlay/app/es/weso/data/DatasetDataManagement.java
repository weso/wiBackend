package es.weso.data;

import java.util.Collection;

import models.Dataset;

/**
 * Defines the operations that can be performed related to {@link Dataset}
 * retrieval in the data layer
 * 
 * @author Alejandro Montes García
 * @since 12/09/2013
 * @version 1.0
 */
public interface DatasetDataManagement {

	/**
	 * Gets all the {@link Dataset}s in a hierarchical structure
	 * 
	 * @return
	 */
	public Collection<Dataset> getAllDatasets();

	/**
	 * Retrieves an {@link Dataset} from the SPARQL endpoint with the given
	 * uri
	 * 
	 * @param uri
	 * @return
	 */
	public Dataset getDatasetByURI(String uri);
}
