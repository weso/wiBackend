package controllers

import java.util.Collection
import java.util.Collections

import scala.collection.JavaConversions.asScalaBuffer
import scala.concurrent.Await
import scala.concurrent.duration._

import es.weso.wiLodPortal.business.impl.ComponentManager
import es.weso.wiLodPortal.business.impl.CountryGroupManager
import es.weso.wiLodPortal.business.impl.CountryManager
import es.weso.wiLodPortal.business.impl.DatasetManager
import es.weso.wiLodPortal.business.impl.IndicatorManager
import es.weso.wiLodPortal.business.impl.ObservationManager
import es.weso.wiLodPortal.business.impl.SubindexManager
import es.weso.wiLodPortal.business.impl.WeightSchemaManager
import es.weso.wirouter.CountryRouteParser
import es.weso.wirouter.YearRouteParser
import es.weso.wirouter.year.YearExpr
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WS
import play.api.mvc.Action
import play.api.mvc.Controller

object Application extends Controller with Renders {

  val componentManager = ComponentManager.getInstance
  val countryGroupManager = CountryGroupManager.getInstance
  val countryManager = CountryManager.getInstance
  val datasetManager = DatasetManager.getInstance
  val indicatorManager = IndicatorManager.getInstance
  val observationManager = ObservationManager.getInstance
  val subindexManager = SubindexManager.getInstance
  val weightSchemaManager = WeightSchemaManager.getInstance

  val url = "http://localhost:8080/pubby/"
  val Data = "data"
  val Page = "page"

  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
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

  def fallback(path: String) = Action { implicit request =>

    def inner(mimeType: String, mode: String, suffix: Option[String] = None) = {
      val query = new StringBuilder(url).append(mode).append("/").append(path)
      suffix match {
        case Some(suffix) => query.append(suffix)
        case None =>
      }
      println(query.toString)
      val future = WS.url(query.toString).withHeaders("Accept" -> mimeType.toString()).get().map {
        response => Ok("" + response.body).as(mimeType)
      }
      Await.result(future, 60 seconds)
    }

    render {
      case Html() => inner(Html.mimeType, Page)
      case Turtle() => inner(Turtle.mimeType, Data, Some("?output=ttl"))
      case XTurtle() => inner(Turtle.mimeType, Data, Some("?output=ttl"))
      case RdfXML() => inner(RdfXML.mimeType, Data, Some("?output=xml"))
      case Json() => inner(Json.mimeType, Data)
      case Xml() => inner(RdfXML.mimeType, Data, Some("?output=xml"))
      case _ => BadRequest
    }

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
      observations.add(expr.trim)
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
    val exprs: List[YearExpr] = new YearRouteParser().parseRoute(expression).toList
    for (expr <- exprs) {
      years.addAll(expr.getYears())
    }
    years
  }
}