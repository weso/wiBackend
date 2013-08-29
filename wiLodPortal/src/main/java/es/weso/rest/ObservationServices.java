package es.weso.rest;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.ObservationManagement;
import es.weso.model.Observation;

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

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllObservations(ModelMap model) {
		model.addAttribute("observations", observationManager.getAllObservations());
		return "observations";
	}

	@RequestMapping(value = "/{uri}", method = RequestMethod.GET)
	public String getObservationByUri(@PathVariable String uri, ModelMap model) {
		Observation obs = observationManager.getObservationByURI(uri);
		fillModel(model, obs.getCountryCode(), obs.getYear(), obs
				.getIndicator().getName());
		return "observation";
	}

	@RequestMapping(value = "/{country}/{year}/{indicator}", method = RequestMethod.GET)
	public String getObservation(@PathVariable String country,
			@PathVariable String year, @PathVariable String indicator,
			ModelMap model, final HttpServletRequest request) {
		int intYear = Integer.parseInt(year);
		String[] paths = observationManager
				.getAllObservationsByCountries(Collections.singleton(country),
						Collections.singleton(indicator),
						Collections.singleton(intYear)).iterator().next()
				.getUri().split("/");
		int beginIndex = request.getRequestURI().lastIndexOf(".");
		String redirection = "redirect:/observation/" + paths[paths.length - 1];
		return beginIndex == -1 ? redirection : redirection
				+ request.getRequestURI().substring(beginIndex);
	}

	private void fillModel(ModelMap model, String country, int year,
			String indicator) {
		Map<String, Object> info = observationManager.getObservationForWebpage(
				country, year, indicator);
		for (Map.Entry<String, Object> entry : info.entrySet()) {
			model.addAttribute(entry.getKey(), entry.getValue());
		}
	}
}
