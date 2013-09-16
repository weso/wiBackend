package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.CountryGroupManagement;

/**
 * Web services to retrieve {@link es.weso.model.CountryGroup CountryGroups}
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/region")
public class RegionServices {

	private static CountryGroupManagement countryGroupManager;

	public void setCountryGroupManager(
			CountryGroupManagement countryGroupManager) {
		RegionServices.countryGroupManager = countryGroupManager;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllRegions(ModelMap model) {
		model.addAttribute("regions",
				countryGroupManager.getAllCountryGroups());
		return "regions";
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getCountriesFromRegion(@PathVariable String name, ModelMap model) {
		model.addAttribute("region",
				countryGroupManager.getCountryGroup(name));
		return "region";
	}

}
