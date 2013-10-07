package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.CountryManagement;
import es.weso.model.Country;
import es.weso.model.ItemCollection;

/**
 * Web services to retrieve {@link es.weso.model.Country countries}
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/country")
public class CountryServices {

	private static CountryManagement countryManager;

	public void setCountryManager(CountryManagement countryManager) {
		CountryServices.countryManager = countryManager;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllCountries(ModelMap model) {
		model.addAttribute("countries", new ItemCollection<Country>(
				countryManager.getAllCountries()));
		return "countries";
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public String getCountry(@PathVariable String code, ModelMap model) {
		model.addAttribute("country", countryManager.getCountry(code));
		return "country";
	}

}
