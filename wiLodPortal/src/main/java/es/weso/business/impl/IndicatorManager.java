package es.weso.business.impl;

import java.util.Collection;

import es.weso.business.IndicatorManagement;
import es.weso.data.IndicatorDataManagement;
import es.weso.model.Indicator;
import es.weso.model.JSONHashMap;

/**
 * Implementation of {@link Indicator} management operations
 * 
 * @author Alejandro Montes García
 * @since 14/08/2013
 * @version 1.0
 */
public class IndicatorManager implements IndicatorManagement {

	private static IndicatorDataManagement indicatorDataManager;

	public void setIndicatorDataManager(
			IndicatorDataManagement indicatorDataManager) {
		IndicatorManager.indicatorDataManager = indicatorDataManager;
	}

	@Override
	public Indicator getIndicator(String label) {
		return indicatorDataManager.getIndicator(label);
	}

	@Override
	public JSONHashMap<String, JSONHashMap<String, Collection<String>>> getAllIndicators() {
		return indicatorDataManager.getAllIndicators();
	}
}
