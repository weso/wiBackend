package es.weso.business.impl;

import java.io.IOException;
import java.util.Collection;

import models.Indicator;
import models.JSONHashMap;
import es.weso.business.IndicatorManagement;
import es.weso.data.IndicatorDataManagement;
import es.weso.data.impl.IndicatorDataManager;

/**
 * Implementation of {@link Indicator} management operations
 * 
 * @author Alejandro Montes García
 * @since 14/08/2013
 * @version 1.0
 */
public class IndicatorManager implements IndicatorManagement {

	private static IndicatorDataManagement indicatorDataManager;
	private static IndicatorManager instance;

	private IndicatorManager() {
	}

	public static IndicatorManager getInstance() {
		if (instance == null) {
			try {
				instance = new IndicatorManager();
				indicatorDataManager = new IndicatorDataManager();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	@Override
	public Indicator getIndicator(String label) {
		return indicatorDataManager.getIndicator(label);
	}

	@Override
	public JSONHashMap<String, JSONHashMap<String, Collection<String>>> getIndicatorsHierarchy() {
		return indicatorDataManager.getHierarchy();
	}

	@Override
	public Indicator getIndicatorByURI(String uri) {
		return indicatorDataManager.getIndicatorByURI(uri);
	}

	@Override
	public Collection<Indicator> getAllIndicators() {
		return indicatorDataManager.geAllIndicators();
	}
}
