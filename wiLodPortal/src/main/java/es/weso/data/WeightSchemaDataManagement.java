package es.weso.data;

import java.util.Collection;

import es.weso.model.WeightSchema;

/**
 * Defines the operations that can be performed related to {@link WeightSchema}
 * retrieval in the data layer
 * 
 * @author Alejandro Montes García
 * @since 13/09/2013
 * @version 1.0
 */
public interface WeightSchemaDataManagement {

	/**
	 * Gets all the {@link WeightSchema}s in a hierarchical structure
	 * 
	 * @return
	 */
	public Collection<WeightSchema> getAllWeightSchemas();

	/**
	 * Retrieves an {@link WeightSchema} from the SPARQL endpoint with the given
	 * uri
	 * 
	 * @param uri
	 * @return
	 */
	public WeightSchema getWeightSchemaByURI(String uri);
}
