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
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.RequestHeader
import play.api.mvc.Accepting
import play.api.mvc.Result
import es.weso.wirouter.year.YearExpr
import scala.collection.mutable.ListBuffer

trait Renders extends Controller {

  val gson = new Gson()
  val xstream = new XStream(new DomDriver())

  val PlainText = Accepting("text/plain")
  val Html = Accepting("text/html")
  val Turtle = Accepting("text/turtle")
  val XTurtle = Accepting("application/x-turtle")
  val RdfXML = Accepting("application/rdf+xml")
  val Json = Accepting("application/json")
  val Xml = Accepting("application/xml")

  def render(obj: Object, format: String)(implicit request: RequestHeader): Result = {
    render {
      case Html() => renderChart(obj)
      case Turtle() => renderTTL(obj)
      case XTurtle() => renderTTL(obj)
      case RdfXML => renderRDF(obj)
      case Json() => renderJSON(obj)
      case Xml() => renderXML(obj)
      case _ => BadRequest // Should return 415
    }

  }

  private def renderChart(obj: Object)(implicit request: RequestHeader) = Ok(views.html.chart("Chart Generator", obj))

  private def renderXML(obj: Object)(implicit request: RequestHeader) = Ok(xstream.toXML(obj))

  private def renderJSON(obj: Object)(implicit request: RequestHeader) = Ok(gson.toJson(obj))

  private def renderTTL(obj: Object)(implicit request: RequestHeader) = {
    try {
      renderLD("text")
    } catch {
      case e: Exception => InternalServerError
    }
  }

  private def renderRDF(obj: Object)(implicit request: RequestHeader) = {
    try {
      renderLD("xml")
    } catch {
      case e: Exception => InternalServerError
    }
  }

  // FIXME This is fuseki dependant and dirty...
  private def renderLD(output: String)(implicit request: RequestHeader) = {
    println(request.path)
    val relativeUri: String = URLEncoder.encode(
      request.path.substring(request.path.indexOf('/', 0)), "UTF-8")
    val url = new StringBuilder("http://localhost:3030/computex/query?query=CONSTRUCT%7B%3Chttp%3A%2F%2Fdata.webfoundation.org%2Fwebindex%2Fv2013")
      .append(relativeUri).append("%3E+%3Fp+%3Fl%7DWHERE%7B%3Chttp%3A%2F%2Fdata.webfoundation.org%2Fwebindex%2Fv2013").append(relativeUri)
      .append("%3E+%3Fp+%3Fl%7D&output=").append(output).toString;
    val obj = new URL(url)
    val con = obj.openConnection()
    con.setConnectTimeout(4000)
    con.setReadTimeout(2000)
    Ok(Source.fromInputStream(con.getInputStream()).getLines.mkString("\n"));
  }

}