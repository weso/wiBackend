package es.weso.data;

import java.util.Collection;

import models.Indicator;
import models.JSONHashMap;

/**
 * Defines the operations that can be performed related to {@link Indicator}
 * retrieval in the data layer
 * 
 * @author Alejandro Montes García
 * @since 14/08/2013
 * @version 1.0
 */
public interface IndicatorDataManagement {

	/**
	 * Retrieves an {@link Indicator} from the SPARQL endpoint with the given
	 * label
	 * 
	 * @param label
	 * @return
	 */
	public Indicator getIndicator(String label);

	/**
	 * Gets all the {@link Indicator}s in a hierarchical structure
	 * 
	 * @return
	 */
	public JSONHashMap<String, JSONHashMap<String, Collection<String>>> getHierarchy();

	/**
	 * Retrieves an {@link Indicator} from the SPARQL endpoint with the given
	 * uri
	 * 
	 * @param uri
	 * @return
	 */
	public Indicator getIndicatorByURI(String uri);

	/**
	 * Gets all the {@link Indicator}s
	 * 
	 * @return
	 */
	public Collection<Indicator> geAllIndicators();
}
