// @SOURCE:/Users/castrofernandez/Desarrollo/wiBackend/wiLodPortalPlay/conf/routes
// @HASH:eaa2dfa8f730bdcaa1c064a750668f7f49516dd8
// @DATE:Fri Sep 27 12:54:10 CEST 2013

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:37
// @LINE:35
// @LINE:34
// @LINE:32
// @LINE:31
// @LINE:29
// @LINE:28
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:9
// @LINE:6
package controllers {

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:37
// @LINE:35
// @LINE:34
// @LINE:32
// @LINE:31
// @LINE:29
// @LINE:28
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:6
class ReverseApplication {
    

// @LINE:28
def regions(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "region")
}
                                                

// @LINE:32
def subindex(id:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "subindex/" + implicitly[PathBindable[String]].unbind("id", dynamicString(id)))
}
                                                

// @LINE:29
def region(name:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "region/" + implicitly[PathBindable[String]].unbind("name", dynamicString(name)))
}
                                                

// @LINE:24
def observations(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "observation")
}
                                                

// @LINE:26
def observationByCountry(country:String, year:Integer, indicator:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "observation/" + implicitly[PathBindable[String]].unbind("country", dynamicString(country)) + "/" + implicitly[PathBindable[Integer]].unbind("year", year) + "/" + implicitly[PathBindable[String]].unbind("indicator", dynamicString(indicator)))
}
                                                

// @LINE:21
def indicators(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "indicator")
}
                                                

// @LINE:16
def country(code:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "country/" + implicitly[PathBindable[String]].unbind("code", dynamicString(code)))
}
                                                

// @LINE:12
def components(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "component")
}
                                                

// @LINE:31
def subindexes(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "subindex")
}
                                                

// @LINE:35
def weightSchema(id:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "weightSchema/" + implicitly[PathBindable[String]].unbind("id", dynamicString(id)))
}
                                                

// @LINE:37
def compare(countries:String, years:String, indicators:String, format:String = "svg"): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "charts/barchart/" + implicitly[PathBindable[String]].unbind("countries", dynamicString(countries)) + "/" + implicitly[PathBindable[String]].unbind("years", dynamicString(years)) + "/" + implicitly[PathBindable[String]].unbind("indicators", dynamicString(indicators)) + queryString(List(if(format == "svg") None else Some(implicitly[QueryStringBindable[String]].unbind("format", format)))))
}
                                                

// @LINE:13
def component(id:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "component/" + implicitly[PathBindable[String]].unbind("id", dynamicString(id)))
}
                                                

// @LINE:15
def countries(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "country")
}
                                                

// @LINE:25
def observation(id:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "observation/" + implicitly[PathBindable[String]].unbind("id", dynamicString(id)))
}
                                                

// @LINE:19
def dataset(id:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "dataset/" + implicitly[PathBindable[String]].unbind("id", dynamicString(id)))
}
                                                

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                

// @LINE:34
def weightSchemas(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "weightSchema")
}
                                                

// @LINE:22
def indicator(id:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "indicator/" + implicitly[PathBindable[String]].unbind("id", dynamicString(id)))
}
                                                

// @LINE:18
def datasets(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "dataset")
}
                                                
    
}
                          
}
                  


// @LINE:37
// @LINE:35
// @LINE:34
// @LINE:32
// @LINE:31
// @LINE:29
// @LINE:28
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:37
// @LINE:35
// @LINE:34
// @LINE:32
// @LINE:31
// @LINE:29
// @LINE:28
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:6
class ReverseApplication {
    

// @LINE:28
def regions : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.regions",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "region"})
      }
   """
)
                        

// @LINE:32
def subindex : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.subindex",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "subindex/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", encodeURIComponent(id))})
      }
   """
)
                        

// @LINE:29
def region : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.region",
   """
      function(name) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "region/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("name", encodeURIComponent(name))})
      }
   """
)
                        

// @LINE:24
def observations : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.observations",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "observation"})
      }
   """
)
                        

// @LINE:26
def observationByCountry : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.observationByCountry",
   """
      function(country,year,indicator) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "observation/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("country", encodeURIComponent(country)) + "/" + (""" + implicitly[PathBindable[Integer]].javascriptUnbind + """)("year", year) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("indicator", encodeURIComponent(indicator))})
      }
   """
)
                        

// @LINE:21
def indicators : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.indicators",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "indicator"})
      }
   """
)
                        

// @LINE:16
def country : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.country",
   """
      function(code) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "country/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("code", encodeURIComponent(code))})
      }
   """
)
                        

// @LINE:12
def components : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.components",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "component"})
      }
   """
)
                        

// @LINE:31
def subindexes : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.subindexes",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "subindex"})
      }
   """
)
                        

// @LINE:35
def weightSchema : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.weightSchema",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "weightSchema/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", encodeURIComponent(id))})
      }
   """
)
                        

// @LINE:37
def compare : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.compare",
   """
      function(countries,years,indicators,format) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "charts/barchart/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("countries", encodeURIComponent(countries)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("years", encodeURIComponent(years)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("indicators", encodeURIComponent(indicators)) + _qS([(format == null ? null : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("format", format))])})
      }
   """
)
                        

// @LINE:13
def component : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.component",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "component/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", encodeURIComponent(id))})
      }
   """
)
                        

// @LINE:15
def countries : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.countries",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "country"})
      }
   """
)
                        

// @LINE:25
def observation : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.observation",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "observation/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", encodeURIComponent(id))})
      }
   """
)
                        

// @LINE:19
def dataset : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.dataset",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "dataset/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", encodeURIComponent(id))})
      }
   """
)
                        

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

// @LINE:34
def weightSchemas : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.weightSchemas",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "weightSchema"})
      }
   """
)
                        

// @LINE:22
def indicator : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.indicator",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "indicator/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", encodeURIComponent(id))})
      }
   """
)
                        

// @LINE:18
def datasets : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.datasets",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "dataset"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:37
// @LINE:35
// @LINE:34
// @LINE:32
// @LINE:31
// @LINE:29
// @LINE:28
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:9
// @LINE:6
package controllers.ref {


// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:37
// @LINE:35
// @LINE:34
// @LINE:32
// @LINE:31
// @LINE:29
// @LINE:28
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:6
class ReverseApplication {
    

// @LINE:28
def regions(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.regions(), HandlerDef(this, "controllers.Application", "regions", Seq(), "GET", """""", _prefix + """region""")
)
                      

// @LINE:32
def subindex(id:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.subindex(id), HandlerDef(this, "controllers.Application", "subindex", Seq(classOf[String]), "GET", """""", _prefix + """subindex/$id<[^/]+>""")
)
                      

// @LINE:29
def region(name:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.region(name), HandlerDef(this, "controllers.Application", "region", Seq(classOf[String]), "GET", """""", _prefix + """region/$name<[^/]+>""")
)
                      

// @LINE:24
def observations(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.observations(), HandlerDef(this, "controllers.Application", "observations", Seq(), "GET", """""", _prefix + """observation""")
)
                      

// @LINE:26
def observationByCountry(country:String, year:Integer, indicator:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.observationByCountry(country, year, indicator), HandlerDef(this, "controllers.Application", "observationByCountry", Seq(classOf[String], classOf[Integer], classOf[String]), "GET", """""", _prefix + """observation/$country<[^/]+>/$year<[^/]+>/$indicator<[^/]+>""")
)
                      

// @LINE:21
def indicators(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.indicators(), HandlerDef(this, "controllers.Application", "indicators", Seq(), "GET", """""", _prefix + """indicator""")
)
                      

// @LINE:16
def country(code:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.country(code), HandlerDef(this, "controllers.Application", "country", Seq(classOf[String]), "GET", """""", _prefix + """country/$code<[^/]+>""")
)
                      

// @LINE:12
def components(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.components(), HandlerDef(this, "controllers.Application", "components", Seq(), "GET", """""", _prefix + """component""")
)
                      

// @LINE:31
def subindexes(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.subindexes(), HandlerDef(this, "controllers.Application", "subindexes", Seq(), "GET", """""", _prefix + """subindex""")
)
                      

// @LINE:35
def weightSchema(id:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.weightSchema(id), HandlerDef(this, "controllers.Application", "weightSchema", Seq(classOf[String]), "GET", """""", _prefix + """weightSchema/$id<[^/]+>""")
)
                      

// @LINE:37
def compare(countries:String, years:String, indicators:String, format:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.compare(countries, years, indicators, format), HandlerDef(this, "controllers.Application", "compare", Seq(classOf[String], classOf[String], classOf[String], classOf[String]), "GET", """""", _prefix + """charts/barchart/$countries<[^/]+>/$years<[^/]+>/$indicators<[^/]+>""")
)
                      

// @LINE:13
def component(id:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.component(id), HandlerDef(this, "controllers.Application", "component", Seq(classOf[String]), "GET", """""", _prefix + """component/$id<[^/]+>""")
)
                      

// @LINE:15
def countries(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.countries(), HandlerDef(this, "controllers.Application", "countries", Seq(), "GET", """""", _prefix + """country""")
)
                      

// @LINE:25
def observation(id:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.observation(id), HandlerDef(this, "controllers.Application", "observation", Seq(classOf[String]), "GET", """""", _prefix + """observation/$id<[^/]+>""")
)
                      

// @LINE:19
def dataset(id:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.dataset(id), HandlerDef(this, "controllers.Application", "dataset", Seq(classOf[String]), "GET", """""", _prefix + """dataset/$id<[^/]+>""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

// @LINE:34
def weightSchemas(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.weightSchemas(), HandlerDef(this, "controllers.Application", "weightSchemas", Seq(), "GET", """""", _prefix + """weightSchema""")
)
                      

// @LINE:22
def indicator(id:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.indicator(id), HandlerDef(this, "controllers.Application", "indicator", Seq(classOf[String]), "GET", """""", _prefix + """indicator/$id<[^/]+>""")
)
                      

// @LINE:18
def datasets(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.datasets(), HandlerDef(this, "controllers.Application", "datasets", Seq(), "GET", """""", _prefix + """dataset""")
)
                      
    
}
                          
}
        
    