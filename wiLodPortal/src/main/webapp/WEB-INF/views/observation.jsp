<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
        <!--<![endif]-->
	<head>
		<script>
			var webIndexData = {
				country: ${country},
				year: ${year},
				ranking: ${ranking},
				history: ${history},
				observations: ${observations},
				indicator: ${indicator},
				relatedObservations1: ${relatedObservations1},
				relatedObservations2: ${relatedObservations2}
			};
		</script>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Indicator | The Web Index</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width">

		<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

		<link rel="stylesheet" href="<c:url value="/static/css/normalize.css" />">
		<link rel="stylesheet" href="<c:url value="/static/css/jquery-ui.css" />">
		<link rel="stylesheet" href="<c:url value="/static/css/main.css" />">
		<link rel="stylesheet" href="<c:url value="/static/css/foundation.min.css" />">
		<link rel="stylesheet" href="<c:url value="/static/css/app.css" />">
		<link rel="stylesheet" href="<c:url value="/static/css/wesCountry.css" />">
		<link rel="stylesheet" href="<c:url value="/static/css/flags32.css" />">
		<script src="<c:url value="/static/js/vendor/custom.modernizr.js" />"></script>
		<script>
			document.write('<script src=<c:url value="/static/js/vendor/" />' + ('__proto__' in {} ? 'zepto' : 'jquery') + '.js><\/script>');
		</script>
		<script src="<c:url value="/static/js/vendor/jquery.js" />"></script>
		<script src="<c:url value="/static/js/vendor/jquery-ui.js" />"></script>
		<script src="<c:url value="/static/js/vendor/jquery-jvectormap-1.2.2.min.js" />"></script>
		<script src="<c:url value="/static/js/vendor/world.map.js" />"></script>
		<script src="<c:url value="/static/js/vendor/d3.v3.js" />"></script>
		<script src="<c:url value="/static/js/vendor/foundation.min.js" />"></script>
		<script src="<c:url value="/static/js/vendor/jQueryRotateCompressed.js" />"></script>
		<script src="<c:url value="/static/js/plugins.js" />"></script>
		<script src="<c:url value="/static/js/main.js" />" src="../js/main.js"></script>
		<script src="<c:url value="/static/js/yearSelector.js" />"></script>
		<script src="<c:url value="/static/js/indicatorList.js" />"></script>
		<script src="<c:url value="/static/js/webIndexLoad.js" />"></script>
		<script src="<c:url value="/static/js/webindex.js" />"></script>
		<script src="<c:url value="/static/js/wesCountry.js" />"></script>
		<script src="<c:url value="/static/js/D3Connector.js" />"></script>
		<script src="<c:url value="/static/js/vendor/rainbowvis.js" />"></script>
		<script src="<c:url value="/static/js/vendor/colorlegend.js" />"></script>
		<script src="<c:url value="/static/js/vendor/jquery.tipsy.js" />"></script>
		<script>
			$(function() {
				$(document).foundation();
			})
		</script>
	</head>
	<body>
		<section id="global">
			<div class="row" style="display:none">
				<img src="<c:url value="/static/img/header.png" />" style="width:100%" />
			</div>
			<div class="row">
				<div class="small-12 large-2 columns">
					<a id="compare" href="#" class="button expand">Compare countries</a>
				</div>
				<div class="small-12 large-6 columns">
					<div class="large-3 small-3 columns indicator-rank">
						<div class="large-8 small-8 columns rank-header">
							Rank<br/>out of 61
						</div>
						<div id="wi-rank" class="large-4 small-4 columns">
							23
						</div>
					</div>
					<div id="wi-indicator-value" class="large-6 small-6 columns">
						<div class="row collapse">
							<div class="large-7 small-7 columns rank-header">
								Score
							</div>
							<div class="large-5 small-5 columns progress-score">
								71
							</div>
						</div>
						<div class="progress score-bar">
							<span id="score-progress" class="meter" style="width:61%"></span>
						</div>
					</div>
					<div id="wi-indicator-tendency" class="large-3 small-3 columns indicator-rank">
						<div class="large-7 small-7 columns rank-header">
							Tendency
							<br />
							<span class="tendency-score">Upward</span>
						</div>
						<div id="wi-tendency-arrow" class="large-5 small-5 columns">
							<img src="<c:url value="/static/img/arrow-up.png" />" alt="Upward tendency" />
						</div>
					</div>
				</div>
				<div id="country-info" class="small-12 large-4 columns">
					<div class="large-8 small-8 columns">
						<span id="region-title"></span>
						<br />
						<span id="country-title"></span>
					</div>
					<div id="flag" class="large-4 small-4 columns f32">
						<div id="country-flag" alt="Country flag" />
					</div>
				</div>
			</div>
			<div class="row collapse">
				<div id="country-webindex" class="small-12 large-8 columns chart">
				</div>
				<div id="country-extra" class="small-12 large-4 columns">
					<div class="small-6 large-6 columns">
						<div class="row collapse bgline">
							<span class="bgline-text organizations-title">Organizations</span>
						</div>
						<div class="row collapse">
							<div class="small-6 large-6 columns">
								<div class="organization-name margin-left">OECD</div>
							</div>
							<div class="small-6 large-6 columns">
								<div class="organization-name margin-right">NATO</div>
							</div>
						</div>
						<div class="row collapse">
							<div class="small-6 large-6 columns">
								<div class="organization-name margin-left">EUROPE</div>
							</div>
							<div class="small-6 large-6 columns">
								<div class="organization-name margin-right">EU</div>
							</div>
						</div>
						<div class="row collapse">
							<div class="small-6 large-6 columns">
								<div class="organization-name margin-left">EURO</div>
							</div>
							<div class="small-6 large-6 columns">
								<div class="organization-name margin-right">IBERO</div>
							</div>
						</div>
						<div class="row collapse">
							<div class="small-6 large-6 columns">
								<div class="organization-name margin-left">EDA</div>
							</div>
							<div class="small-6 large-6 columns">
								<div class="organization-name margin-right">OSCE</div>
							</div>
						</div>
					</div>
					<div id="country-map" class="small-6 large-6 columns">
					</div>
				</div>
			</div>
			<br />
			<div class="row show-for-small">
				<select id="year-select">
				</select>
			</div>
			<div class="row hide-for-small">
					<div id="year-selector">
				</div>
			</div>
			<br />
			<div class="row bgline">
				<span class="bgline-text">Indicators</span>
			</div>
			<br />
		</section>
		<section id="selectors" class="row">
			<aside id="left-bar" class="small-12 large-3 columns">
				
				<div id="search" class="row collapse">
					<div class="small-9 columns">
						<input id="autocomplete" type="text" placeholder="Search an indicator">
					</div>
					<div class="small-3 columns">
						<div class="postfix magnifier"><img id='magnifier' src="<c:url value="/static/img/magnifier.png" />" alt='Search an indicator' /></div>
					</div>
				</div>
				<div id="indicators-container" class="row collapse">
					<div id='accordion' class='indicator-list'>
					</div>
				</div>
				<div style="display:none">
					<button onclick="indicador_creciente()">Indicador creciente</button>
					<button onclick="indicador_decreciente()">Indicador decreciente</button>
					<button onclick="indicador_constante()">Indicador estable</button>
					<br />
					<button onclick="reloadCharts()">Recargar</button>
				</div>
			</aside>
			<section id="indicator-info" class="large-6 small-12 columns">
				<h3>Creation Of New Services Based On Government Data</h3>
				<p>
					Survey Question: To what extent are Web applications and services in areas such as health, education, security, budgets etc "built" on top of government data (i.e. there has been new and useful information and services derived from the published government data in those fields? [1 = none at all; 10 = extensive applications and services]
				</p>
				<div class="bgline">
					<h4 class="subheader blank">World ranking</h4>
				</div>
				<div class="row collapse">
					<div class="large-3 small-3 columns indicator-rank">
						<div class="large-8 small-8 columns rank-header">
							Rank<br/>out of 61
						</div>
						<div id="indicator-rank" class="large-4 small-4 columns">
							18
						</div>
					</div>
					<div id="indicator-value" class="large-6 small-6 columns">
						<div class="row collapse">
							<div class="large-7 small-7 columns rank-header">
								Score
							</div>
							<div class="large-5 small-5 columns progress-score">
								<span id="indicator-value-text"></span>
							</div>
						</div>
						<div id="indicator-score-bar" class="progress score-bar">
						</div>
					</div>
					<div id="indicator-tendency" class="large-3 small-3 columns indicator-rank">
						<div class="large-7 small-7 columns rank-header">
							Tendency
							<br />
							<span id="tendency-text" class="tendency-score"></span>
						</div>
						<div id="tendency-arrow" class="large-5 small-5 columns">
		
						</div>
					</div>
				</div>
				<div id="indicator-world-position" class="row collapse chart">
				</div>
				<div class="bgline">
					<h4 class="subheader blank">Indicator</h4>
				</div>
				<div id="indicator-main-indicator" class="row collapse chart">
				</div>
				
				<div class="bgline">
					<h4 class="subheader blank">Progression</h4>
				</div>
				<br />
				<div class="row collapse">
					<div class="large-4 small-4 columns progression-text">
						Score <strong>67</strong>
						<br />
						Rank <strong>18</strong> out of 61
					</div>
					<div class="large-1 small-1 columns">
						<div class="progression-year">2010</div>
					</div>
					<div class="large-2 small-2 columns">&nbsp;</div>
					<div class="large-4 small-4 columns progression-text">
						Score <strong>67</strong>
						<br />
						Rank <strong>18</strong> out of 61
					</div>
					<div class="large-1 small-1 columns">
						<div class="progression-year">2011</div>
					</div>
				</div>
				<br />
				<div class="row collapse">
					<div class="large-4 small-4 columns">
						<div id="indicator-progression-indicator-1" class="chart">
						</div>
					</div>
					<div class="large-1 small-1 columns">
						<img src="<c:url value="/static/img/arrow-big-up.png" />" class="big-trend" alt="Upward tendency" />
					</div>
					<div class="large-2 small-2 columns">&nbsp;</div>
					<div class="large-4 small-4 columns">
						<div id="indicator-progression-indicator-2" class="chart">
						</div>
					</div>
					<div class="large-1 small-1 columns">
						<img src="<c:url value="/static/img/arrow-big-down.png" />" class="big-trend" alt="Upward tendency" />
					</div>
				</div>
				<br />
				<div id="indicator-progression-years" class="row collapse chart">
				</div>
			</section>
			<section id="organization-selector" class="large-3 small-12 columns organization-bar">
				<div class="row collapse">
					Organization comparison
				</div>
				<div class="row collapse organization-title">
					European Union
				</div>
				<br />
				<div class="row collapse">
					<div class="large-7 small-7 columns">
						<div class="row collapse">
							<div class="large-7 small-7 columns rank-header">
								Score
							</div>
							<div class="large-5 small-5 columns progress-score">
								<span>70</span>
							</div>
						</div>
						<div class="progress organation-score-bar">
							<span class="organization-meter" style="width:61%"></span>
						</div>
					</div>
					<div class="large-4 small-4 columns indicator-rank">
						<div class="large-7 small-7 columns rank-header">
							Tendency
							<br />
							<strong>UPWARD</strong>
						</div>
						<div class="large-5 small-5 columns">
							<img src="<c:url value="/static/img/arrow-up-gray.png" />" class="organization-arrow" alt="Upward tendency" />
						</div>
					</div>
				</div>
				<br />
				<div class="bgline-organization">
					<h4 class="subheader organization-blank organization-rank-header">Indicator</h4>
				</div>
				<br />
				<div id="indicator-organization-comparison-countries" class="chart organation-bar-chart">
				</div>
				<div id="indicator-organization-comparison-mean" class="chart organation-bar-chart">
				</div>
				<div class="bgline-organization">
					<h4 class="subheader organization-blank organization-rank-header">Progression</h4>
				</div>
				<br />
				<div id="indicator-organization-comparison-progression" class="chart organation-bar-chart">
				</div>
				<div class="bgline-organization">
					<h4 class="subheader organization-blank organization-rank-header">Comparison</h4>
				</div>
				<br />
				<div id="indicator-organization-comparison-both" class="chart organation-bar-chart">
				</div>
			</section>
		</section>
	</body>
</html>