package es.weso.rest;

import java.util.ArrayDeque;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.ObservationManagement;
import es.weso.model.Observation;
import es.weso.model.ObservationWithoutIndicator;

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

	public void setObservationManager(ObservationManagement observationManager) {
		ObservationServices.observationManager = observationManager;
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
		model.addAttribute("ranking", deleteIndicator(observationManager
				.getRanking(indicator, Integer.parseInt(year))));
		model.addAttribute("history", deleteIndicator(observationManager
				.getHistory(country, indicator)));
		model.addAttribute(
				"observations",
				deleteIndicator(observationManager.getBarchart(country,
						Integer.parseInt(year), indicator)));
		try {
			model.addAttribute("previousObservations",
					deleteIndicator(observationManager.getBarchart(country,
							Integer.parseInt(year) - 1, indicator)));
		} catch (IllegalArgumentException iae) {
			model.addAttribute("previousObservations",
					"There are no previous observations");
		}
		try {
			model.addAttribute("followingObservations",
					deleteIndicator(observationManager.getBarchart(country,
							Integer.parseInt(year) + 1, indicator)));
		} catch (IllegalArgumentException iae) {
			model.addAttribute("followingObservations",
					"There are no following observations");
		}
		return "observations";
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
