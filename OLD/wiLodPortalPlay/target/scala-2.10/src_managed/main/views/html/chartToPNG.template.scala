
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object chartToPNG extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Object,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(title: String, json: Object):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import com.google.gson.Gson


Seq[Any](format.raw/*1.31*/("""

"""),format.raw/*4.1*/("""
"""),_display_(Seq[Any](/*5.2*/main("Chart Generator")/*5.25*/ {_display_(Seq[Any](format.raw/*5.27*/("""
    <script>
    	var chartData = """),_display_(Seq[Any](/*7.23*/{Html(new Gson().toJson(json))})),format.raw/*7.54*/(""";
    </script>
    <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*9.50*/routes/*9.56*/.Assets.at("stylesheets/wesCountry.css"))),format.raw/*9.96*/("""">
    <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*10.55*/routes/*10.61*/.Assets.at("images/favicon.png"))),format.raw/*10.93*/("""">
    <script src=""""),_display_(Seq[Any](/*11.19*/routes/*11.25*/.Assets.at("javascripts/vendor/d3.v3.js"))),format.raw/*11.66*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*12.19*/routes/*12.25*/.Assets.at("javascripts/util/SortedArray.js"))),format.raw/*12.70*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*13.19*/routes/*13.25*/.Assets.at("javascripts/util/HashTable.js"))),format.raw/*13.68*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*14.19*/routes/*14.25*/.Assets.at("javascripts/charts/wesCountry/wesCountry.js"))),format.raw/*14.82*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*15.19*/routes/*15.25*/.Assets.at("javascripts/charts/wesCountry/D3Connector.js"))),format.raw/*15.83*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*16.19*/routes/*16.25*/.Assets.at("javascripts/charts/toPNG.js"))),format.raw/*16.66*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*17.19*/routes/*17.25*/.Assets.at("javascripts/vendor/canvg.js"))),format.raw/*17.66*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*18.19*/routes/*18.25*/.Assets.at("javascripts/charts/processWSData.js"))),format.raw/*18.74*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*19.19*/routes/*19.25*/.Assets.at("javascripts/charts/util/overlay.js"))),format.raw/*19.73*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*20.19*/routes/*20.25*/.Assets.at("javascripts/charts/util/scrolling.js"))),format.raw/*20.75*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*21.19*/routes/*21.25*/.Assets.at("javascripts/charts/barChart.options.js"))),format.raw/*21.77*/("""" type="text/javascript"></script>
	<div class="content">
        <div id="result"></div>
        <div id="images"></div>
	</div>
""")))})))}
    }
    
    def render(title:String,json:Object): play.api.templates.HtmlFormat.Appendable = apply(title,json)
    
    def f:((String,Object) => play.api.templates.HtmlFormat.Appendable) = (title,json) => apply(title,json)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 27 13:19:52 CEST 2013
                    SOURCE: /Users/castrofernandez/Desarrollo/wiBackend/wiLodPortalPlay/app/views/chartToPNG.scala.html
                    HASH: 7d63c119707117470ac619ae4bfcfa9b6aaf3645
                    MATRIX: 786->1|937->30|965->61|1001->63|1032->86|1071->88|1142->124|1194->155|1294->220|1308->226|1369->266|1462->323|1477->329|1531->361|1588->382|1603->388|1666->429|1755->482|1770->488|1837->533|1926->586|1941->592|2006->635|2095->688|2110->694|2189->751|2278->804|2293->810|2373->868|2462->921|2477->927|2540->968|2629->1021|2644->1027|2707->1068|2796->1121|2811->1127|2882->1176|2971->1229|2986->1235|3056->1283|3145->1336|3160->1342|3232->1392|3321->1445|3336->1451|3410->1503
                    LINES: 26->1|30->1|32->4|33->5|33->5|33->5|35->7|35->7|37->9|37->9|37->9|38->10|38->10|38->10|39->11|39->11|39->11|40->12|40->12|40->12|41->13|41->13|41->13|42->14|42->14|42->14|43->15|43->15|43->15|44->16|44->16|44->16|45->17|45->17|45->17|46->18|46->18|46->18|47->19|47->19|47->19|48->20|48->20|48->20|49->21|49->21|49->21
                    -- GENERATED --
                */
            