package controllers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.chart;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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

	private static Gson gson = new Gson();
	private static XStream xstream = new XStream(new DomDriver());
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
		if (request().accepts("text/html")) {
			return ok(chart.render("Chart Generator", obj));
		}
		if (request().accepts("text/turtle")) {
			return renderTTL();
		}
		if (request().accepts("application/rdf+xml")) {
			return renderRDF();
		}
		if (request().accepts("application/json")) {
			return renderJSON(obj);
		}
		if (request().accepts("application/xml")) {
			return renderXML(obj);
		}
		return badRequest(); // Should return 415
	}

	private static Result renderXML(Object obj) {
		return ok(xstream.toXML(obj));
	}

	private static Result renderJSON(Object obj) {
		return ok(gson.toJson(obj));
	}

	private static Result renderTTL() {
		try {
			return renderLD("text");
		} catch (Exception e) {
			return internalServerError();
		}
	}

	private static Result renderRDF() {
		try {
			return renderLD("xml");
		} catch (Exception e) {
			return internalServerError();
		}
	}

	private static Result renderLD(String output) throws Exception {
		// FIXME This is fuseki dependant and dirty...
		System.out.println(request().path());
		String relativeUri = URLEncoder.encode(
				request().path().substring(request().path().indexOf('/', 0)),
				"UTF-8");
		String url = "http://localhost:3030/computex/query?query=CONSTRUCT%7B%3Chttp%3A%2F%2Fdata.webfoundation.org%2Fwebindex%2Fv2013"
				+ relativeUri
				+ "%3E+%3Fp+%3Fl%7DWHERE%7B%3Chttp%3A%2F%2Fdata.webfoundation.org%2Fwebindex%2Fv2013"
				+ relativeUri + "%3E+%3Fp+%3Fl%7D&output=" + output;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((inputLine = in.readLine()) != null) {
			baos.write(inputLine.getBytes());
			baos.write("\n".getBytes());
		}
		in.close();
		return ok(baos.toString());
	}

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result components() {
		return render(componentManager.getAll(), null);
	}

	public static Result component(String id) {
		return render(componentManager.getOne(id), null);
	}

	public static Result countries() {
		return render(countryManager.getAllCountries(), null);
	}

	public static Result country(String code) {
		return render(countryManager.getCountry(code), null);
	}

	public static Result datasets() {
		return render(datasetManager.getAll(), null);
	}

	public static Result dataset(String id) {
		return render(datasetManager.getOne(id), null);
	}

	public static Result indicators() {
		return render(indicatorManager.getAllIndicators(), null);
	}

	public static Result indicator(String id) {
		return render(indicatorManager.getIndicatorByURI(id), null);
	}

	public static Result observations() {
		return render(observationManager.getAllObservations(), null);
	}

	public static Result observation(String id) {
		return render(observationManager.getObservationByURI(id), null);
	}

	public static Result observationByCountry(String country, int year,
			String indicator) {
		// TODO Redirect
		return render(
				observationManager.getAllObservationsByCountries(
						Collections.singleton(country),
						Collections.singleton(indicator),
						Collections.singleton(year)), null);
	}

	public static Result regions() {
		return render(countryGroupManager.getAllCountryGroups(), null);
	}

	public static Result region(String name) {
		return render(countryGroupManager.getCountryGroup(name), null);
	}

	public static Result subindexes() {
		return render(subindexManager.getAll(), null);
	}

	public static Result subindex(String id) {
		return render(subindexManager.getOne(id), null);
	}

	public static Result weightSchemas() {
		return render(weightSchemaManager.getAll(), null);
	}

	public static Result weightSchema(String id) {
		return render(weightSchemaManager.getOne(id), null);
	}

	public static Result compare(String countries, String years,
			String indicators) {
		return render(observationManager.getAllObservationsByCountries(
				parseCountryCodes(countries), parseObservations(indicators),
				parseYears(years)), null);
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
