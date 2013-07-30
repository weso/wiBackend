package es.weso.rest;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.ObservationManagement;
import es.weso.model.ItemCollection;
import es.weso.model.Observation;
import es.weso.wirouter.CountryRouteParser;
import es.weso.wirouter.YearRouteParser;
import es.weso.wirouter.country.CountryExpr;
import es.weso.wirouter.year.YearExpr;

/**
 * Web services to retrieve {@link es.weso.model.Observation observations}
 * 
 * @author Alejandro Montes García
 * @since 03/07/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/observations")
public class ObservationsServices {

	private static ObservationManagement observationManager;

	public void setObservationManager(ObservationManagement observationManager) {
		ObservationsServices.observationManager = observationManager;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getObservations(ModelMap model) {
		model.addAttribute("observations", new ItemCollection<Observation>(
				observationManager.getAllObservations()));
		return "observations";
	}

	@RequestMapping(value = "/{country}", method = RequestMethod.GET)
	public String getObservations(@PathVariable String country, ModelMap model) {
		model.addAttribute(
				"observations",
				new ItemCollection<Observation>(
						observationManager
								.getAllObservationsByCountries(parseCountryCodes(country))));
		return "observations";
	}

	@RequestMapping(value = "/{country}/{year}", method = RequestMethod.GET)
	public String getObservations(@PathVariable String country,
			@PathVariable String year, ModelMap model) {
		model.addAttribute(
				"observations",
				new ItemCollection<Observation>(observationManager
						.getAllObservationsByYear(parseYears(year),
								parseCountryCodes(country))));
		return "observations";
	}

	@RequestMapping(value = "/{country}/{year}/{observation}", method = RequestMethod.GET)
	public String getObservations(@PathVariable String country,
			@PathVariable String year, @PathVariable String observation,
			ModelMap model) {
		model.addAttribute(
				"observations",
				new ItemCollection<Observation>(observationManager
						.getAllObservationsByCountries(
								parseCountryCodes(country),
								parseObservations(observation),
								parseYears(year))));
		return "observations";
	}

	/**
	 * Parses a expression to get the country codes contained in that expression
	 * 
	 * @param expression
	 *            The expression to be parsed
	 * @return The country codes contained in that expression
	 */
	private Collection<String> parseCountryCodes(String expression) {
		Collection<String> countries = new HashSet<String>();
		Collection<CountryExpr> exprs = new CountryRouteParser()
				.parseRoute(expression);
		for (CountryExpr expr : exprs) {
			countries.addAll(expr.getCountryCodes());
		}
		return countries;
	}

	/**
	 * Parses a expression to get the observations contained in that expression
	 * 
	 * @param expression
	 *            The expression to be parsed
	 * @return The observations contained in that expression
	 */
	private Collection<String> parseObservations(String expression) {
		String[] arr = expression.split(",");
		Collection<String> observations = new HashSet<String>(arr.length);
		for (String expr : arr) {
			observations.add(expr.trim());
		}
		return observations;
	}

	/**
	 * Parses a expression to get the years contained in that expression
	 * 
	 * @param expression
	 *            The expression to be parsed
	 * @return The years contained in that expression
	 */
	private Collection<Integer> parseYears(String expression) {
		Collection<Integer> years = new HashSet<Integer>();
		Collection<YearExpr> exprs = new YearRouteParser()
				.parseRoute(expression);
		for (YearExpr expr : exprs) {
			years.addAll(expr.getYears());
		}
		return years;
	}
}
