package es.weso.business;

import java.util.Collection;

import es.weso.model.Indicator;
import es.weso.model.JSONHashMap;

/**
 * Defines the operations that can be performed related to {@link Indicator}
 * retrieval
 * 
 * @author Alejandro Montes García
 * @since 14/08/2013
 * @version 1.0
 */
public interface IndicatorManagement {

	/**
	 * Gets an {@link Indicator} by its label
	 * 
	 * @param label
	 * @return
	 */
	public Indicator getIndicator(String label);
	
	/**
	 * Gets an {@link Indicator} by its uri
	 * 
	 * @param uri
	 * @return
	 */
	public Indicator getIndicatorByURI(String uri);

	/**
	 * Gets all the {@link Indicator}s in a hierarchical structure
	 * 
	 * @return
	 */
	public JSONHashMap<String, JSONHashMap<String, Collection<String>>> getIndicatorsHierarchy();
	
	
	/**
	 * Gets all the {@link Indicator}s
	 * 
	 * @return
	 */
	public Collection<Indicator> getAllIndicators();
}
