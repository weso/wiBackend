package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.chart;
import views.html.chartToPNG;

import com.google.gson.Gson;

import es.weso.business.ComponentManagement;
import es.weso.business.CountryGroupManagement;
import es.weso.business.CountryManagement;
import es.weso.business.DatasetManagement;
import es.weso.business.IndicatorManagement;
import es.weso.business.ObservationManagement;
import es.weso.business.SubindexManagement;
import es.weso.business.WeightSchemaManagement;
import es.weso.business.impl.ComponentManager;
import es.weso.business.impl.CountryGroupManager;
import es.weso.business.impl.CountryManager;
import es.weso.business.impl.DatasetManager;
import es.weso.business.impl.IndicatorManager;
import es.weso.business.impl.ObservationManager;
import es.weso.business.impl.SubindexManager;
import es.weso.business.impl.WeightSchemaManager;
import es.weso.wirouter.CountryRouteParser;
import es.weso.wirouter.YearRouteParser;
import es.weso.wirouter.country.CountryExpr;
import es.weso.wirouter.year.YearExpr;

public class Application extends Controller {

	private static String DEFAULT = "";
	private static Gson gson = new Gson();
	private static ComponentManagement componentManager = ComponentManager
			.getInstance();
	private static CountryGroupManagement countryGroupManager = CountryGroupManager
			.getInstance();
	private static CountryManagement countryManager = CountryManager
			.getInstance();
	private static DatasetManagement datasetManager = DatasetManager
			.getInstance();
	private static IndicatorManagement indicatorManager = IndicatorManager
			.getInstance();
	private static ObservationManagement observationManager = ObservationManager
			.getInstance();
	private static SubindexManagement subindexManager = SubindexManager
			.getInstance();
	private static WeightSchemaManagement weightSchemaManager = WeightSchemaManager
			.getInstance();

	private static Result render(Object obj, String format) {
		if (request().accepts("text/html")) 
			if (format.toUpperCase().equals("PNG"))
				return ok(chartToPNG.render("Chart Generator", obj));			
			else
				return ok(chart.render("Chart Generator", obj));
		
		return renderJSON(obj);
	}

	private static Result renderJSON(Object obj) {
		return ok(gson.toJson(obj));
	}

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result components() {
		return render(componentManager.getAll(), DEFAULT);
	}

	public static Result component(String id) {
		return render(componentManager.getOne(id), DEFAULT);
	}

	public static Result countries() {
		return render(countryManager.getAllCountries(), DEFAULT);
	}

	public static Result country(String code) {
		return render(countryManager.getCountry(code), DEFAULT);
	}

	public static Result datasets() {
		return render(datasetManager.getAll(), DEFAULT);
	}

	public static Result dataset(String id) {
		return render(datasetManager.getOne(id), DEFAULT);
	}

	public static Result indicators() {
		return render(indicatorManager.getAllIndicators(), DEFAULT);
	}

	public static Result indicator(String id) {
		return render(indicatorManager.getIndicatorByURI(id), DEFAULT);
	}

	public static Result observations() {
		return render(observationManager.getAllObservations(), DEFAULT);
	}

	public static Result observation(String id) {
		return render(observationManager.getObservationByURI(id), DEFAULT);
	}

	public static Result observationByCountry(String country, int year,
			String indicator) {
		return render(observationManager.getAllObservationsByCountries(
				Collections.singleton(country),
				Collections.singleton(indicator), Collections.singleton(year)), DEFAULT);
	}

	public static Result regions() {
		return render(countryGroupManager.getAllCountryGroups(), DEFAULT);
	}

	public static Result region(String name) {
		return render(countryGroupManager.getCountryGroup(name), DEFAULT);
	}

	public static Result subindexes() {
		return render(subindexManager.getAll(), DEFAULT);
	}

	public static Result subindex(String id) {
		return render(subindexManager.getOne(id), DEFAULT);
	}

	public static Result weightSchemas() {
		return render(weightSchemaManager.getAll(), DEFAULT);
	}

	public static Result weightSchema(String id) {
		return render(weightSchemaManager.getOne(id), DEFAULT);
	}

	public static Result compare(String countries, String years,
			String indicators, String format) {
		return render(observationManager.getAllObservationsByCountries(
				parseCountryCodes(countries), parseObservations(indicators),
				parseYears(years)), format);
	}

	/**
	 * Parses a expression to get the country codes contained in that expression
	 * 
	 * @param expression
	 *            The expression to be parsed
	 * @return The country codes contained in that expression
	 */
	private static Collection<String> parseCountryCodes(String expression) {
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
	private static Collection<String> parseObservations(String expression) {
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
	private static Collection<Integer> parseYears(String expression) {
		Collection<Integer> years = new HashSet<Integer>();
		Collection<YearExpr> exprs = new YearRouteParser()
				.parseRoute(expression);
		for (YearExpr expr : exprs) {
			years.addAll(expr.getYears());
		}
		return years;
	}

}
