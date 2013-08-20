package es.weso.rest;

import java.util.ArrayDeque;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.CountryManagement;
import es.weso.business.IndicatorManagement;
import es.weso.business.ObservationManagement;
import es.weso.model.Indicator;
import es.weso.model.Observation;
import es.weso.model.ObservationWithoutIndicator;
import es.weso.model.Stats;
import es.weso.model.Trend;

/**
 * Web services to retrieve {@link es.weso.model.Observation observations}
 * 
 * @author Alejandro Montes García
 * @since 03/07/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/observation")
public class ObservationServices {

	private static ObservationManagement observationManager;
	private static IndicatorManagement indicatorManager;
	private static CountryManagement countryManager;

	public void setCountryManager(CountryManagement countryManager) {
		ObservationServices.countryManager = countryManager;
	}

	public void setObservationManager(ObservationManagement observationManager) {
		ObservationServices.observationManager = observationManager;
	}

	public void setIndicatorManager(IndicatorManagement indicatorManager) {
		ObservationServices.indicatorManager = indicatorManager;
	}

	@RequestMapping(value = "/{uri}", method = RequestMethod.GET)
	public String getObservationByUri(@PathVariable String uri, ModelMap model) {
		model.addAttribute("observation",
				observationManager.getObservationByURI(uri));
		return "observation";
	}

	@RequestMapping(value = "/{country}/{year}/{indicator}", method = RequestMethod.GET)
	public String getObservations(@PathVariable String country,
			@PathVariable String year, @PathVariable String indicator,
			ModelMap model) {
		int intYear = Integer.parseInt(year);
		Collection<ObservationWithoutIndicator> ranking = deleteIndicator(observationManager
				.getRanking(indicator, intYear));
		double[] values = new double[ranking.size()];
		int i = 0;
		for (ObservationWithoutIndicator obs : ranking) {
			values[i++] = obs.getValue();
		}
		model.addAttribute("stats", new Stats(values));
		model.addAttribute("country", countryManager.getCountry(country));
		model.addAttribute("ranking", ranking);
		Collection<ObservationWithoutIndicator> history = deleteIndicator(observationManager
				.getHistory(country, indicator));
		model.addAttribute("trend", new Trend(getTrend(intYear, history)));
		model.addAttribute("history", history);
		model.addAttribute("observations", deleteIndicator(observationManager
				.getBarchart(country, intYear, indicator)));
		Indicator ind = indicatorManager.getIndicator(indicator);
		model.addAttribute("indicator", ind);
		model.addAttribute("indicatorHirearchy",
				indicatorManager.getAllIndicators());
		int firstYear = intYear - 1;
		int secondYear = intYear + 1;
		if (ind.getStart() >= intYear) {
			firstYear = secondYear;
			secondYear++;
		}
		if (ind.getEnd() <= intYear || intYear == 2011) { // FIXME Esto es
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
		model.addAttribute("relatedObservations1",
				deleteIndicator(observationManager.getBarchart(country,
						firstYear, indicator)));
		model.addAttribute("relatedObservations2",
				deleteIndicator(observationManager.getBarchart(country,
						secondYear, indicator)));
		return "redirect:/observation/obs1";
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
