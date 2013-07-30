package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.CountryGroupManagement;
import es.weso.business.CountryManagement;

/**
 * Web services to retrieve {@link es.weso.model.CountryGroup CountryGroups}
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/groups")
public class CountryGroupServices {

	private static CountryGroupManagement countryGroupManager;
	private static CountryManagement countryManager;

	public void setCountryGroupManager(
			CountryGroupManagement countryGroupManager) {
		CountryGroupServices.countryGroupManager = countryGroupManager;
	}

	public void setCountryManager(CountryManagement countryManager) {
		CountryGroupServices.countryManager = countryManager;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getCountryGroups(ModelMap model) {
		model.addAttribute("organizations",
				countryGroupManager.getAllCountryGroupsByType(false));
		model.addAttribute("regions",
				countryGroupManager.getAllCountryGroupsByType(true));
		return "countryGroups";
	}

	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public String getAllRegions(ModelMap model) {
		model.addAttribute("regions",
				countryGroupManager.getAllCountryGroupsByType(true));
		return "continents";
	}

	@RequestMapping(value = "/regions/{name}", method = RequestMethod.GET)
	public String getCountriesFromRegion(@PathVariable String name, ModelMap model) {
		model.addAttribute("countries",
				countryManager.getCountriesFrom(name));
		return "countries";
	}

	@RequestMapping(value = "/organizations", method = RequestMethod.GET)
	public String getAllOrganizations(ModelMap model) {
		model.addAttribute("organizations",
				countryGroupManager.getAllCountryGroupsByType(false));
		return "organizations";
	}

	@RequestMapping(value = "/organizations/{name}", method = RequestMethod.GET)
	public String getCountriesFromOrganization(@PathVariable String name, ModelMap model) {
		model.addAttribute("countries",
				countryManager.getCountriesFrom(name));
		return "countries";
	}

}
