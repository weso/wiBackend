package es.weso.business;

import es.weso.model.Indicator;

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
	 * @param label
	 * @return
	 */
	public Indicator getIndicator(String label);
}
