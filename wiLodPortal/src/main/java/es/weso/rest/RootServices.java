package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.CountryGroupManagement;
import es.weso.business.IndicatorManagement;

/**
 * 
 * @author Alejandro Montes García
 * @since 29/08/2013
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class RootServices {
	
	private static CountryGroupManagement countryGroupManager;
	private static IndicatorManagement indicatorManager;

	public void setCountryGroupManager(
			CountryGroupManagement countryGroupManager) {
		RootServices.countryGroupManager = countryGroupManager;
	}
	
	public void setIndicatorManager(
			IndicatorManagement indicatorManager) {
		RootServices.indicatorManager = indicatorManager;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap model) {
		model.addAttribute("groups",
				countryGroupManager.getAllCountryGroupsByType(false));
		model.addAttribute("regions",
				countryGroupManager.getAllCountryGroupsByType(true));
		model.addAttribute("indicators", indicatorManager.getIndicatorsHierarchy());
		return "index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index2(ModelMap model) {
		return index(model);
	}

}
