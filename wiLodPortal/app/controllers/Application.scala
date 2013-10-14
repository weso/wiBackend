package controllers

import java.net.URL
import java.net.URLEncoder
import java.util.Collection
import java.util.Collections

import scala.collection.JavaConversions.asScalaBuffer
import scala.io.Source

import com.google.gson.Gson
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver

import es.weso.business.impl.ComponentManager
import es.weso.business.impl.CountryGroupManager
import es.weso.business.impl.CountryManager
import es.weso.business.impl.DatasetManager
import es.weso.business.impl.IndicatorManager
import es.weso.business.impl.ObservationManager
import es.weso.business.impl.SubindexManager
import es.weso.business.impl.WeightSchemaManager
import es.weso.wirouter.CountryRouteParser
import es.weso.wirouter.YearRouteParser
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.RequestHeader

object Application extends Controller {

  val gson = new Gson()
  val xstream = new XStream(new DomDriver());
  val componentManager = ComponentManager.getInstance;
  val countryGroupManager = CountryGroupManager.getInstance
  val countryManager = CountryManager.getInstance
  val datasetManager = DatasetManager.getInstance
  val indicatorManager = IndicatorManager.getInstance
  val observationManager = ObservationManager.getInstance
  val subindexManager = SubindexManager.getInstance
  val weightSchemaManager = WeightSchemaManager.getInstance

  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

  def render(obj: Object, format: String)(implicit request: RequestHeader) = {
    if (request.accepts("text/html")) {
      renderChart(obj)
    } else if (request.accepts("text/turtle")) {
      renderTTL(obj)
    } else if (request.accepts("application/rdf+xml")) {
      renderRDF(obj)
    } else if (request.accepts("application/json")) {
      renderJSON(obj)
    } else if (request.accepts("application/xml")) {
      renderXML(obj)
    } else BadRequest // Should return 415
  }

  private def renderChart(obj: Object)(implicit request: RequestHeader) = Ok(views.html.chart("Chart Generator", obj));

  private def renderXML(obj: Object)(implicit request: RequestHeader) = Ok(xstream.toXML(obj));

  private def renderJSON(obj: Object)(implicit request: RequestHeader) = Ok(gson.toJson(obj));

  private def renderTTL(obj: Object)(implicit request: RequestHeader) = {
    try {
      renderLD("text");
    } catch {
      case e: Exception => InternalServerError;
    }
  }

  private def renderRDF(obj: Object)(implicit request: RequestHeader) = {
    try {
      renderLD("xml");
    } catch {
      case e: Exception => InternalServerError
    }
  }

  private def renderLD(output: String)(implicit request: RequestHeader) = {
    // FIXME This is fuseki dependant and dirty...
    println(request.path);
    val relativeUri: String = URLEncoder.encode(
      request.path.substring(request.path.indexOf('/', 0)), "UTF-8");
    val url = "http://localhost:3030/computex/query?query=CONSTRUCT%7B%3Chttp%3A%2F%2Fdata.webfoundation.org%2Fwebindex%2Fv2013" +
      relativeUri +
      "%3E+%3Fp+%3Fl%7DWHERE%7B%3Chttp%3A%2F%2Fdata.webfoundation.org%2Fwebindex%2Fv2013" +
      relativeUri + "%3E+%3Fp+%3Fl%7D&output=" + output;
    val obj = new URL(url)
    val con = obj.openConnection()
    con.setConnectTimeout(4000)
    con.setReadTimeout(2000)
    Ok(Source.fromInputStream(con.getInputStream()).getLines.mkString("\n"));
  }

  def components = Action { implicit request =>
    render(componentManager.getAll(), null)
  }

  def component(id: String) = Action { implicit request =>
    render(componentManager.getOne(id), null)
  }

  def countries() = Action { implicit request =>
    render(countryManager.getAllCountries(), null)
  }

  def country(code: String) = Action { implicit request =>
    render(countryManager.getCountry(code), null)
  }

  def datasets() = Action { implicit request =>
    render(datasetManager.getAll(), null)
  }

  def dataset(id: String) = Action { implicit request =>
    render(datasetManager.getOne(id), null)
  }

  def indicators() = Action { implicit request =>
    render(indicatorManager.getAllIndicators(), null);
  }

  def indicator(id: String) = Action { implicit request =>
    render(indicatorManager.getIndicatorByURI(id), null);
  }

  def observations() = Action { implicit request =>
    render(observationManager.getAllObservations(), null)
  }

  def observation(id: String) = Action { implicit request =>
    render(observationManager.getObservationByURI(id), null)
  }

  def observationByCountry(country: String, year: Int, indicator: String) = Action { implicit request =>
    // TODO Redirect
    render(
      observationManager.getAllObservationsByCountries(
        Collections.singleton(country),
        Collections.singleton(indicator),
        Collections.singleton(year)), null)
  }

  def regions() = Action { implicit request =>
    render(countryGroupManager.getAllCountryGroups(), null)
  }

  def region(name: String) = Action { implicit request =>
    render(countryGroupManager.getCountryGroup(name), null)
  }

  def subindexes() = Action { implicit request =>
    render(subindexManager.getAll(), null)
  }

  def subindex(id: String) = Action { implicit request =>
    render(subindexManager.getOne(id), null)
  }

  def weightSchemas() = Action { implicit request =>
    render(weightSchemaManager.getAll(), null)
  }

  def weightSchema(id: String) = Action { implicit request =>
    render(weightSchemaManager.getOne(id), null)
  }

  def compare(countries: String, years: String, indicators: String) = Action { implicit request =>
    render(observationManager.getAllObservationsByCountries(
      parseCountryCodes(countries), parseObservations(indicators),
      parseYears(years)), null)
  }

  /**
   * Parses a expression to get the country codes contained in that expression
   *
   * @param expression
   *            The expression to be parsed
   * @return The country codes contained in that expression
   */
  private def parseCountryCodes(expression: String): Collection[String] = {
    val countries = new java.util.HashSet[String]();
    val exprs = new CountryRouteParser().parseRoute(expression);
    for (expr <- exprs) {
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
  private def parseObservations(expression: String): Collection[String] = {
    val arr = expression.split(",")
    val observations = new java.util.HashSet[String](arr.length)
    for (expr <- arr) {
      observations.add(expr.trim())
    }
    observations;
  }

  /**
   * Parses a expression to get the years contained in that expression
   *
   * @param expression
   *            The expression to be parsed
   * @return The years contained in that expression
   */
  private def parseYears(expression: String): Collection[Integer] = {
    val years = new java.util.HashSet[Integer]()
    val exprs = new YearRouteParser().parseRoute(expression)
    for (expr <- exprs) {
      years.addAll(expr.getYears());
    }
    years;
  }
}