package es.weso.data;

import es.weso.model.Indicator;

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
	 * Retrieves an {@link Indicator} from the SPARQL endpoint with the given label
	 * @param label
	 * @return
	 */
	public Indicator getIndicator(String label);
}
