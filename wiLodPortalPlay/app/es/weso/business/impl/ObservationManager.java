package es.weso.business.impl;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import es.weso.business.CountryManagement;
import es.weso.business.IndicatorManagement;
import es.weso.business.ObservationManagement;
import es.weso.data.ObservationDataManagement;
import models.Indicator;
import models.Observation;
import models.ObservationWithoutIndicator;
import models.Stats;
import models.Trend;

/**
 * Implementation of {@link Observation} management operations
 * 
 * @author Alejandro Montes García
 * @since 03/07/2013
 * @version 1.0
 */
public class ObservationManager implements ObservationManagement {

	private static ObservationDataManagement observationDataManager;
	private static IndicatorManagement indicatorManager;
	private static CountryManagement countryManager;

	public void setIndicatorManager(IndicatorManagement indicatorManager) {
		ObservationManager.indicatorManager = indicatorManager;
	}

	public void setCountryManager(CountryManagement countryManager) {
		ObservationManager.countryManager = countryManager;
	}

	public void setObservationDataManager(
			ObservationDataManagement observationDataManager) {
		ObservationManager.observationDataManager = observationDataManager;
	}

	public Collection<Observation> getAllObservations() {
		return observationDataManager.getObservationsFrom(
				Collections.<String> emptyList(),
				Collections.<String> emptyList(),
				Collections.<Integer> emptyList());
	}

	public Collection<Observation> getAllObservationsByCountries(
			Collection<String> countries) {
		return observationDataManager.getObservationsFrom(countries,
				Collections.<String> emptyList(),
				Collections.<Integer> emptyList());
	}

	public Collection<Observation> getAllObservationsByCountries(
			Collection<String> countries, Collection<String> indicators) {
		return observationDataManager.getObservationsFrom(countries,
				indicators, Collections.<Integer> emptyList());
	}

	public Collection<Observation> getAllObservationsByCountries(
			Collection<String> countries, Collection<String> indicators,
			Collection<Integer> years) {
		return observationDataManager.getObservationsFrom(countries,
				indicators, years);
	}

	public Collection<Observation> getAllObservationsByYear(
			Collection<Integer> years) {
		return observationDataManager.getObservationsFrom(
				Collections.<String> emptyList(),
				Collections.<String> emptyList(), years);
	}

	public Collection<Observation> getAllObservationsByYear(
			Collection<Integer> years, Collection<String> countries) {
		return observationDataManager.getObservationsFrom(countries,
				Collections.<String> emptyList(), years);
	}

	public Collection<Observation> getAllObservationsByIndicator(
			Collection<String> indicators) {
		return observationDataManager.getObservationsFrom(
				Collections.<String> emptyList(), indicators,
				Collections.<Integer> emptyList());
	}

	public Collection<Observation> getAllObservationsByIndicator(
			Collection<String> indicators, Collection<Integer> years) {
		return observationDataManager.getObservationsFrom(
				Collections.<String> emptyList(), indicators, years);
	}

	public Observation getObservationByURI(String uri) {
		return observationDataManager.getObservationByURI(uri);
	}

	@Override
	public Collection<Observation> getBarchart(String code, Integer year,
			String indicator) {
		Collection<String> countries = new ArrayDeque<String>(5);
		countries.add(code);
		Collection<String> countriesInRegion = observationDataManager
				.getCountriesInRegion(code, indicator, year);
		Collection<String> countriesOutsideRegion = observationDataManager
				.getCountriesOutsideRegion(code, indicator, year);
		countries.add(countriesInRegion.iterator().next());
		countries.add(countriesOutsideRegion.iterator().next());
		String worst = "";
		for (String str : countriesInRegion) {
			worst = str;
		}
		countries.add(worst);
		for (String str : countriesOutsideRegion) {
			worst = str;
		}
		countries.add(worst);
		return getAllObservationsByCountries(countries,
				Collections.singleton(indicator), Collections.singleton(year));
	}

	@Override
	public Collection<Observation> getRanking(String indicator, int year) {
		return observationDataManager.getRanking(indicator, year);
	}

	@Override
	public Collection<Observation> getHistory(String country, String indicator) {
		return getAllObservationsByCountries(Collections.singleton(country),
				Collections.singleton(indicator));
	}

	@Override
	public Map<String, Object> getObservationForWebpage(String country,
			int year, String indicator) {
		Map<String, Object> info = new HashMap<String, Object>();
		Collection<ObservationWithoutIndicator> ranking = deleteIndicator(getRanking(
				indicator, year));
		double[] values = new double[ranking.size()];
		int i = 0;
		for (ObservationWithoutIndicator obs : ranking) {
			values[i++] = obs.getValue();
		}
		info.put("stats", new Stats(values));
		info.put("country", countryManager.getCountry(country));
		info.put("ranking", ranking);
		Collection<ObservationWithoutIndicator> history = deleteIndicator(getHistory(
				country, indicator));
		info.put("trend", new Trend(getTrend(year, history)));
		info.put("history", history);
		info.put("observations",
				deleteIndicator(getBarchart(country, year, indicator)));
		Indicator ind = indicatorManager.getIndicator(indicator);
		info.put("indicator", ind);
		info.put("indicatorHirearchy", indicatorManager.getIndicatorsHierarchy());
		int firstYear = year - 1;
		int secondYear = year + 1;
		if (ind.getStart() >= year) {
			firstYear = secondYear;
			secondYear++;
		}
		if (ind.getEnd() <= year || year == 2011) { // FIXME Esto es
													// porque
													// el
													// indicador dice
													// que
													// acaba en 2012
													// pero en
													// realidad no hay
													// datos
													// de 2012 aún,
													// borrar la segunda
													// parte del OR
			secondYear = firstYear;
			firstYear--;
		}
		info.put("relatedObservations1",
				deleteIndicator(getBarchart(country, firstYear, indicator)));
		info.put("relatedObservations2",
				deleteIndicator(getBarchart(country, secondYear, indicator)));
		return info;
	}

	private double getTrend(int intYear,
			Collection<ObservationWithoutIndicator> history) {
		double currentValue = 0, pastValue = 0;
		int valuesFound = 0;
		for (ObservationWithoutIndicator obs : history) {
			if (obs.getYear() == intYear - 1) {
				pastValue = obs.getValue();
				valuesFound++;
			}
			if (obs.getYear() == intYear) {
				currentValue = obs.getValue();
				valuesFound++;
			}
		}
		return valuesFound == 2 ? currentValue - pastValue : 0;
	}

	private Collection<ObservationWithoutIndicator> deleteIndicator(
			Collection<Observation> observations) {
		Collection<ObservationWithoutIndicator> obs = new ArrayDeque<ObservationWithoutIndicator>(
				observations.size());
		for (Observation o : observations) {
			obs.add(new ObservationWithoutIndicator(o));
		}
		return obs;
	}
}
