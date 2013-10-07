// @SOURCE:/Users/castrofernandez/Desarrollo/wiBackend/wiLodPortalPlay/conf/routes
// @HASH:eaa2dfa8f730bdcaa1c064a750668f7f49516dd8
// @DATE:Fri Sep 27 12:54:10 CEST 2013


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:9
private[this] lazy val controllers_Assets_at1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        

// @LINE:12
private[this] lazy val controllers_Application_components2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("component"))))
        

// @LINE:13
private[this] lazy val controllers_Application_component3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("component/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:15
private[this] lazy val controllers_Application_countries4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("country"))))
        

// @LINE:16
private[this] lazy val controllers_Application_country5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("country/"),DynamicPart("code", """[^/]+""",true))))
        

// @LINE:18
private[this] lazy val controllers_Application_datasets6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("dataset"))))
        

// @LINE:19
private[this] lazy val controllers_Application_dataset7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("dataset/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:21
private[this] lazy val controllers_Application_indicators8 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("indicator"))))
        

// @LINE:22
private[this] lazy val controllers_Application_indicator9 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("indicator/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:24
private[this] lazy val controllers_Application_observations10 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("observation"))))
        

// @LINE:25
private[this] lazy val controllers_Application_observation11 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("observation/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:26
private[this] lazy val controllers_Application_observationByCountry12 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("observation/"),DynamicPart("country", """[^/]+""",true),StaticPart("/"),DynamicPart("year", """[^/]+""",true),StaticPart("/"),DynamicPart("indicator", """[^/]+""",true))))
        

// @LINE:28
private[this] lazy val controllers_Application_regions13 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("region"))))
        

// @LINE:29
private[this] lazy val controllers_Application_region14 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("region/"),DynamicPart("name", """[^/]+""",true))))
        

// @LINE:31
private[this] lazy val controllers_Application_subindexes15 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("subindex"))))
        

// @LINE:32
private[this] lazy val controllers_Application_subindex16 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("subindex/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:34
private[this] lazy val controllers_Application_weightSchemas17 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("weightSchema"))))
        

// @LINE:35
private[this] lazy val controllers_Application_weightSchema18 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("weightSchema/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:37
private[this] lazy val controllers_Application_compare19 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("charts/barchart/"),DynamicPart("countries", """[^/]+""",true),StaticPart("/"),DynamicPart("years", """[^/]+""",true),StaticPart("/"),DynamicPart("indicators", """[^/]+""",true))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """component""","""controllers.Application.components()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """component/$id<[^/]+>""","""controllers.Application.component(id:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """country""","""controllers.Application.countries()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """country/$code<[^/]+>""","""controllers.Application.country(code:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """dataset""","""controllers.Application.datasets()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """dataset/$id<[^/]+>""","""controllers.Application.dataset(id:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """indicator""","""controllers.Application.indicators()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """indicator/$id<[^/]+>""","""controllers.Application.indicator(id:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """observation""","""controllers.Application.observations()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """observation/$id<[^/]+>""","""controllers.Application.observation(id:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """observation/$country<[^/]+>/$year<[^/]+>/$indicator<[^/]+>""","""controllers.Application.observationByCountry(country:String, year:Integer, indicator:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """region""","""controllers.Application.regions()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """region/$name<[^/]+>""","""controllers.Application.region(name:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """subindex""","""controllers.Application.subindexes()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """subindex/$id<[^/]+>""","""controllers.Application.subindex(id:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """weightSchema""","""controllers.Application.weightSchemas()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """weightSchema/$id<[^/]+>""","""controllers.Application.weightSchema(id:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """charts/barchart/$countries<[^/]+>/$years<[^/]+>/$indicators<[^/]+>""","""controllers.Application.compare(countries:String, years:String, indicators:String, format:String ?= "svg")""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:9
case controllers_Assets_at1(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        

// @LINE:12
case controllers_Application_components2(params) => {
   call { 
        invokeHandler(controllers.Application.components(), HandlerDef(this, "controllers.Application", "components", Nil,"GET", """""", Routes.prefix + """component"""))
   }
}
        

// @LINE:13
case controllers_Application_component3(params) => {
   call(params.fromPath[String]("id", None)) { (id) =>
        invokeHandler(controllers.Application.component(id), HandlerDef(this, "controllers.Application", "component", Seq(classOf[String]),"GET", """""", Routes.prefix + """component/$id<[^/]+>"""))
   }
}
        

// @LINE:15
case controllers_Application_countries4(params) => {
   call { 
        invokeHandler(controllers.Application.countries(), HandlerDef(this, "controllers.Application", "countries", Nil,"GET", """""", Routes.prefix + """country"""))
   }
}
        

// @LINE:16
case controllers_Application_country5(params) => {
   call(params.fromPath[String]("code", None)) { (code) =>
        invokeHandler(controllers.Application.country(code), HandlerDef(this, "controllers.Application", "country", Seq(classOf[String]),"GET", """""", Routes.prefix + """country/$code<[^/]+>"""))
   }
}
        

// @LINE:18
case controllers_Application_datasets6(params) => {
   call { 
        invokeHandler(controllers.Application.datasets(), HandlerDef(this, "controllers.Application", "datasets", Nil,"GET", """""", Routes.prefix + """dataset"""))
   }
}
        

// @LINE:19
case controllers_Application_dataset7(params) => {
   call(params.fromPath[String]("id", None)) { (id) =>
        invokeHandler(controllers.Application.dataset(id), HandlerDef(this, "controllers.Application", "dataset", Seq(classOf[String]),"GET", """""", Routes.prefix + """dataset/$id<[^/]+>"""))
   }
}
        

// @LINE:21
case controllers_Application_indicators8(params) => {
   call { 
        invokeHandler(controllers.Application.indicators(), HandlerDef(this, "controllers.Application", "indicators", Nil,"GET", """""", Routes.prefix + """indicator"""))
   }
}
        

// @LINE:22
case controllers_Application_indicator9(params) => {
   call(params.fromPath[String]("id", None)) { (id) =>
        invokeHandler(controllers.Application.indicator(id), HandlerDef(this, "controllers.Application", "indicator", Seq(classOf[String]),"GET", """""", Routes.prefix + """indicator/$id<[^/]+>"""))
   }
}
        

// @LINE:24
case controllers_Application_observations10(params) => {
   call { 
        invokeHandler(controllers.Application.observations(), HandlerDef(this, "controllers.Application", "observations", Nil,"GET", """""", Routes.prefix + """observation"""))
   }
}
        

// @LINE:25
case controllers_Application_observation11(params) => {
   call(params.fromPath[String]("id", None)) { (id) =>
        invokeHandler(controllers.Application.observation(id), HandlerDef(this, "controllers.Application", "observation", Seq(classOf[String]),"GET", """""", Routes.prefix + """observation/$id<[^/]+>"""))
   }
}
        

// @LINE:26
case controllers_Application_observationByCountry12(params) => {
   call(params.fromPath[String]("country", None), params.fromPath[Integer]("year", None), params.fromPath[String]("indicator", None)) { (country, year, indicator) =>
        invokeHandler(controllers.Application.observationByCountry(country, year, indicator), HandlerDef(this, "controllers.Application", "observationByCountry", Seq(classOf[String], classOf[Integer], classOf[String]),"GET", """""", Routes.prefix + """observation/$country<[^/]+>/$year<[^/]+>/$indicator<[^/]+>"""))
   }
}
        

// @LINE:28
case controllers_Application_regions13(params) => {
   call { 
        invokeHandler(controllers.Application.regions(), HandlerDef(this, "controllers.Application", "regions", Nil,"GET", """""", Routes.prefix + """region"""))
   }
}
        

// @LINE:29
case controllers_Application_region14(params) => {
   call(params.fromPath[String]("name", None)) { (name) =>
        invokeHandler(controllers.Application.region(name), HandlerDef(this, "controllers.Application", "region", Seq(classOf[String]),"GET", """""", Routes.prefix + """region/$name<[^/]+>"""))
   }
}
        

// @LINE:31
case controllers_Application_subindexes15(params) => {
   call { 
        invokeHandler(controllers.Application.subindexes(), HandlerDef(this, "controllers.Application", "subindexes", Nil,"GET", """""", Routes.prefix + """subindex"""))
   }
}
        

// @LINE:32
case controllers_Application_subindex16(params) => {
   call(params.fromPath[String]("id", None)) { (id) =>
        invokeHandler(controllers.Application.subindex(id), HandlerDef(this, "controllers.Application", "subindex", Seq(classOf[String]),"GET", """""", Routes.prefix + """subindex/$id<[^/]+>"""))
   }
}
        

// @LINE:34
case controllers_Application_weightSchemas17(params) => {
   call { 
        invokeHandler(controllers.Application.weightSchemas(), HandlerDef(this, "controllers.Application", "weightSchemas", Nil,"GET", """""", Routes.prefix + """weightSchema"""))
   }
}
        

// @LINE:35
case controllers_Application_weightSchema18(params) => {
   call(params.fromPath[String]("id", None)) { (id) =>
        invokeHandler(controllers.Application.weightSchema(id), HandlerDef(this, "controllers.Application", "weightSchema", Seq(classOf[String]),"GET", """""", Routes.prefix + """weightSchema/$id<[^/]+>"""))
   }
}
        

// @LINE:37
case controllers_Application_compare19(params) => {
   call(params.fromPath[String]("countries", None), params.fromPath[String]("years", None), params.fromPath[String]("indicators", None), params.fromQuery[String]("format", Some("svg"))) { (countries, years, indicators, format) =>
        invokeHandler(controllers.Application.compare(countries, years, indicators, format), HandlerDef(this, "controllers.Application", "compare", Seq(classOf[String], classOf[String], classOf[String], classOf[String]),"GET", """""", Routes.prefix + """charts/barchart/$countries<[^/]+>/$years<[^/]+>/$indicators<[^/]+>"""))
   }
}
        
}

}
     