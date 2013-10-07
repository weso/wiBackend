
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
object main extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(title: String)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.32*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*7.17*/title)),format.raw/*7.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*8.54*/routes/*8.60*/.Assets.at("stylesheets/main.css"))),format.raw/*8.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*9.59*/routes/*9.65*/.Assets.at("images/favicon.png"))),format.raw/*9.97*/("""">
        <script src=""""),_display_(Seq[Any](/*10.23*/routes/*10.29*/.Assets.at("javascripts/vendor/jquery.js"))),format.raw/*10.71*/("""" type="text/javascript"></script>
        <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,400" rel="stylesheet" type="text/css">
    </head>
    <body>
    	<div class="wrapper">
    		<header class="mainHeader">
    			<img alt="World Wide Web Foundation" src=""""),_display_(Seq[Any](/*16.51*/routes/*16.57*/.Assets.at("images/W3F-logo.png"))),format.raw/*16.90*/("""" />
    			<div class="right">
    				<h1>DATA PORTAL</h1>
    				<h2>"""),_display_(Seq[Any](/*19.14*/title)),format.raw/*19.19*/("""</h2>
    			</div>
    			<div class="border"></div>
    		</header>
    		<div class="content">
        		"""),_display_(Seq[Any](/*24.12*/content)),format.raw/*24.19*/("""
    		</div>
    		<footer class="mainFooter">
    			&copy; 2013 World Wide Web Foundation.
    		</footer>
    	</div>
    </body>
</html>
"""))}
    }
    
    def render(title:String,content:Html): play.api.templates.HtmlFormat.Appendable = apply(title)(content)
    
    def f:((String) => (Html) => play.api.templates.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 27 12:46:00 CEST 2013
                    SOURCE: /Users/castrofernandez/Desarrollo/wiBackend/wiLodPortalPlay/app/views/main.scala.html
                    HASH: 1683a2d36c75b0d58aef12f8c92fdc84ed89d103
                    MATRIX: 778->1|902->31|990->84|1016->89|1113->151|1127->157|1182->191|1278->252|1292->258|1345->290|1406->315|1421->321|1485->363|1808->650|1823->656|1878->689|1988->763|2015->768|2160->877|2189->884
                    LINES: 26->1|29->1|35->7|35->7|36->8|36->8|36->8|37->9|37->9|37->9|38->10|38->10|38->10|44->16|44->16|44->16|47->19|47->19|52->24|52->24
                    -- GENERATED --
                */
            