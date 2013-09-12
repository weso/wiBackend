package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.IndicatorManagement;

/**
 * Web services to retrieve {@link es.weso.model.Indicator}s
 * 
 * @author Alejandro Montes García
 * @since 26/08/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/indicator")
public class IndicatorServices {

	private static IndicatorManagement indicatorManager;

	public void setIndicatorManager(IndicatorManagement indicatorManager) {
		IndicatorServices.indicatorManager = indicatorManager;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllObservations(ModelMap model) {
		model.addAttribute("indicators", indicatorManager.getIndicatorsHierarchy());
		return "indicators";
	}

	@RequestMapping(value = "/{uri}", method = RequestMethod.GET)
	public String getObservationByUri(@PathVariable String uri, ModelMap model) {
		model.addAttribute("indicator", indicatorManager.getIndicatorByURI(uri));
		return "indicator";
	}
}
