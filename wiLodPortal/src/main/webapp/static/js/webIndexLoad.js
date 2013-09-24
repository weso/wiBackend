var URL_BASE = "http://localhost:8080/wiLodPortal";

/*
var COUNTRYCODE = "ESP";
var YEAR = 2009;
var MINYEAR = 2009;
var MAXYEAR = 2011;
var INDICATOR = "A";
*/

var charts = [];

$(function(){
	//loadIndicatorInfo(COUNTRYCODE, INDICATOR, YEAR, MINYEAR, MAXYEAR);

	processCountryData(webIndexData, webIndexData.country, webIndexData.indicator.label, webIndexData.year);
	
	$.ajax({
	  type: "GET",
	  url: URL_BASE + "/observations/" +  webIndexData.country.isoCode3 + "/" + webIndexData.year,
	  dataType: "json",
	  contentType: "application/javascript; charset=UTF-8"
	}).done(function ( data ) {
		processCountryListData(data, webIndexData.country.isoCode3, webIndexData.indicator.label);
	});
});

function loadIndicatorInfo(countryCode, indicator, year, minYear, maxYear) {
	// Get country indicators

	$.ajax({
	  type: "GET",
	  url: URL_BASE + "/observation/" +  countryCode + "/" + year + "/" + indicator,
	  dataType: "json",
	  contentType: "application/javascript; charset=UTF-8"
	}).done(function ( data ) {
		processCountryData(data, countryCode, indicator);
	});
}

function processCountryListData(data, countryCode, indicatorName, year) {
	var indicatorsPerYear = [];
	var maxYear = 0;
	var minYear = Number.MAX_VALUE;
	
	var progressionData = [];

	for (var i = 0; i < data.observations.collection.length; i++)
	{
		var value = data.observations.collection[i];
		var year = value.year;
		
		if (year > maxYear)
			maxYear = year;
			
		if (year < minYear)
			minYear = year;
//console.log(value.indicator.name)		
		var indicator = value.indicator;
if (indicator.name.length > 1) continue;
		// Rebuild URI
		indicator.uri = URL_BASE + "/observation/" +  countryCode + "/" + year + "/" + indicator.name;
	
		if (!indicatorsPerYear[year])
			indicatorsPerYear[year] = [];
			
		indicatorsPerYear[year].push(indicator);
		
		if (indicatorName == indicator.name) {
			var index = 0;
			
			while (index < progressionData.length && year > progressionData[index].year) {
				index++;
			}
			
			progressionData.splice(index, 0, { year : year, value : value.value });
		}
	}

	var accordion = $("#accordion");
	
	var autocompleteTags = [];

	var indicatorList = new Object();
	indicatorList.name = "Indicators";
	indicatorList.indicators = indicatorsPerYear[maxYear];

	new IndicatorList(accordion, [ indicatorList ], autocompleteTags);
}

function processCountryData(data, country, indicator, year) {
/*	var indicatorsPerYear = [];
	var maxYear = 0;
	var minYear = Number.MAX_VALUE;
	
	var progressionData = [];

	for (var i = 0; i < data.observations.length; i++)
	{
		var value = data.observations[i];console.log(value)
		var year = value.year;
		
		if (year > maxYear)
			maxYear = year;
			
		if (year < minYear)
			minYear = year;
		
		var indicator = value.indicator;

		// Rebuild URI
		indicator.uri = URL_BASE + "/observation/" +  countryCode + "/" + year + "/" + indicator.name;
	
		if (!indicatorsPerYear[year])
			indicatorsPerYear[year] = [];
			
		indicatorsPerYear[year].push(indicator);
		
		if (indicator.name == indicatorName) {
			var index = 0;
			
			while (index < progressionData.length && year > progressionData[index].year) {
				index++;
			}
			
			progressionData.splice(index, 0, { year : year, value : value.value });
		}
	}

	var accordion = $("#accordion");
	
	var autocompleteTags = [];

	var indicatorList = new Object();
	indicatorList.name = "Indicators";
	indicatorList.indicators = indicatorsPerYear[maxYear];

	new IndicatorList(accordion, [ indicatorList ], autocompleteTags);
*/	

	// Flag
	var countryFlag = document.getElementById("country-flag");
	countryFlag.className = "f32 " + country.isoCode2.toLowerCase();
	
	// Country name
	document.getElementById("country-title").innerHTML = country.name;
	
	// Region name
	document.getElementById("region-title").innerHTML = country.belongsTo[0].name;
	
	// Map
	// '#world-map', regionSelectedFunction, 'world_mill_en', 'ES', 40.46, -3.75
	var code = country.isoCode2.toUpperCase();

	var map = new jvm.WorldMap({
        container: $("#country-map"),
        map: 'world_merc_en',
        zoomOnScroll: false,
        regionsSelectable: true,
        regionStyle: {
	    initial: {
		        fill: '#c9e1ab'
		    },
		    selected: {
		        fill: '#91bf39'
		    }
		},
        onRegionSelected: function(){},
        backgroundColor: "#FFFFFF"
    });
	
    map.setFocus(code);
    var selectedRegions = new Array(code);
    map.setSelectedRegions(selectedRegions);
	
	//countryFlag.src += 'flags32.png';

	new YearSelector("year-selector", 15, data.indicator.start, data.indicator.end - 1, year, new AfterClick(country.isoCode3, indicator));
	//setBasicYearSelector("year-select", data.indicator.start, data.indicator.end - 1);
	
	// World ranking
	//processRanking(data, countryCode, indicator);
	
	// Country comparison
	processComparingCountries(data, country.isoCode3, indicator);
	
	// Previous year country comparison
	processPreviousComparingCountries(data, country.isoCode3, indicator);
	
	// Following year country comparison
	processNextComparingCountries(data, country.isoCode3, indicator);
	
	// Year progression
	processProgressionBarChart(data, "#indicator-progression-years", country.isoCode3, indicator);
}

function processComparingCountries(data, countryCode, indicator) {

/*	
	var p = new Params();

	for (var i = 0; i < graphData.length; i++)
		p.regions[i] = new Region(graphData[i].code, [graphData[i].value]);	

	p.container = "#indicator-world-position";
	p.labels = ["Indicador 1"];
	p.options.groupPadding = 0;
	p.options.barPadding = 1;
	p.options.margins = [10, 0, 40, 30];
	p.options.height = 150;
	p.options.showLabels = true;
	p.options.showXAxisLabel = true;
	p.options.showYAxisLabel = true;
	
	var rainbow = new Rainbow();
	rainbow.setSpectrum('#343465', '#269e45', '#deb722', '#932b2f');
	rainbow.setNumberRange(0, graphData.length);

	p.options.colours = rainbow.getColours(); 
	p.options.colours[17] = "#333";
	p.options.ticks = 2;
	p.options.showLabels = false;
	
	var chart = new connector.drawBarChart(p);
	barCharts.push(chart);
	
	// ELIMINAR
	auxRegions.push(p.regions);
*/

	// World ranking

	// Indicator
	var p = new Params();
	p.options.groupPadding = 40;
	p.options.barPadding = 40;
	p.options.margins = [10, 0, 40, 0];
	p.options.height = 280;	
	p.options.showBarLabels = true;
	
	processComparingBarChart(data, p, "#indicator-main-indicator", countryCode, indicator);
}

function processRanking(data, countryCode, indicator) {
	var p = new Params();
	p.options.groupPadding = 0;
	p.options.barPadding = 1;
	p.options.margins = [10, 0, 40, 30];
	p.options.height = 150;
	p.options.showBarLabels = false;
	
	//processComparingBarChart(data, p, "#indicator-world-position", countryCode, indicator);
	
	//	var p = new Params();
	p.options.groupPadding = 0;
	p.options.barPadding = 20;
	p.options.margins = [10, 0, 40, 0];
	p.options.height = 220;
	p.options.showBarLabels = true;
	
	p.regions = [];
	p.indexes = [];
	p.options.colours = [];
	
	var rainbow = new Rainbow();
	rainbow.setSpectrum('#343465', '#269e45', '#deb722', '#932b2f');
	rainbow.setNumberRange(0, graphData.length);

	var rainbowColours = rainbow.getColours(); 
	
	for (var i = 0; i < data.ranking.length; i++) {
		var _countryCode = data.ranking[i].countryCode;
		var year = data.ranking[i].year;
		var value = data.ranking[i].value;
		var url = URL_BASE + "/observation/" +  data.ranking[i].countryCode + "/" + year + "/" + indicator;
		
		var region = new Region(year, [value], [url]);
		var colour =  _countryCode == countryCode ? "#333" : rainbowColours[i];

		// Data is inserted in order
		
		p.regions.push(region);
		p.indexes.push(year);
		p.options.colours.push(colour);
	}
	
	var connector = new D3Connector();
	
	p.container = "#indicator-world-position";
	p.labels = ["Indicador 1"];

	p.options.showLabels = true;
	p.options.showXAxisLabel = true;
	p.options.showYAxisLabel = true;
	p.options.showValueOnBar = true;

	p.options.showYAxisLabel = false;
	p.options.showLabels = false;

	var chart = new connector.drawBarChart(p);
	charts.push(chart);
}

function processPreviousComparingCountries(data, countryCode, indicator) {
	var p = new Params();
	p.options.groupPadding = 40;
	p.options.barPadding = 20;
	p.options.margins = [0, 0, 0, 0];
	p.options.height = 150;
	p.options.showBarLabels = false;
	
	processComparingBarChart(data, p, "#indicator-progression-indicator-1", countryCode, indicator);
}

function processNextComparingCountries(data, countryCode, indicator) {
	var p = new Params();
	p.options.groupPadding = 40;
	p.options.barPadding = 20;
	p.options.margins = [0, 0, 0, 0];
	p.options.height = 150;
	p.options.showBarLabels = false;
	
	processComparingBarChart(data, p, "#indicator-progression-indicator-2", countryCode, indicator);	
}

function processProgressionBarChart(data, container, countryCode, indicator) {
	var p = new Params();
	p.options.groupPadding = 0;
	p.options.barPadding = 20;
	p.options.margins = [10, 0, 40, 0];
	p.options.height = 220;
	p.options.showBarLabels = true;
	
	p.regions = [];
	p.indexes = [];
	p.options.colours = [];
	
	for (var i = 0; i < data.history.length; i++) {
		var year = data.history[i].year;
		var value = data.history[i].value;
		var url = URL_BASE + "/observation/" +  data.history[i].countryCode + "/" + year + "/" + indicator;
		
		var region = new Region(year, [value], [url]);
		var colour = "#c9e1ab";

		// Data is inserted in order
		
		p.regions.push(region);
		p.indexes.push(year);
		p.options.colours.push(colour);
	}
	
	var connector = new D3Connector();
	
	p.container = container;
	p.labels = ["Indicador 1"];

	p.options.showLabels = true;
	p.options.showXAxisLabel = true;
	p.options.showYAxisLabel = true;
	p.options.showValueOnBar = true;

	p.options.showYAxisLabel = false;
	p.options.showLabels = false;

	var chart = new connector.drawBarChart(p);
	charts.push(chart);
	
	// ELIMINAR
	//auxRegions.push(p.regions);	
}

function processComparingBarChart(data, p, container, countryCode, indicator) {
	p.regions = [];
	p.indexes = [];
	p.options.colours = [];
	
	var mean = 0;
	var median = 0;
	
	for (var i = 0; i < data.observations.length && i < 5; i++) {
		var _countryCode = data.observations[i].countryCode;
		var countryName = data.observations[i].countryName;
		var value = data.observations[i].value;
		var year = data.observations[i].year;
		var url = URL_BASE + "/observation/" +  _countryCode + "/" + year + "/" + indicator;
		
		var region = new Region(countryName, [value], [url]);
		var colour = _countryCode == countryCode ? "#333" : "#91bf39";
		
		mean += value;
		
		// Data is inserted in order
		
		var index = 0;
		
		while (index < p.regions.length && p.regions[index].data[0] < value) {
			index++;
		}
		
		p.regions.splice(index, 0, region);
		p.indexes.splice(index, 0, countryName);
		p.options.colours.splice(index, 0, colour);
	}
	
	// Mean calculation
	mean /= data.observations.length;
	mean = mean.toFixed(2);
	
	// Median calculation
	var half = Math.floor(data.observations.length / 2);
 
    if (data.observations.length % 2)
        median = p.regions[half].data[0];
    else
        median = (p.regions[half - 1].data[0] + p.regions[half].data[0]) / 2.0;
	
	var connector = new D3Connector();
	
	p.container = container;
	p.labels = ["Indicador 1"];

	p.options.showLabels = true;
	p.options.showXAxisLabel = true;
	p.options.showYAxisLabel = true;
	p.options.showValueOnBar = true;

	p.options.showYAxisLabel = false;
	p.options.showLabels = false;
	p.options.mean = mean;
	p.options.meanColour = "#91bf39";
	p.options.median = median;
	p.options.medianColour = "#111";

	var chart = new connector.drawBarChart(p);
	charts.push(chart);
	
	// ELIMINAR
	//auxRegions.push(p.regions);	
}

function getPreviousAndNextYear(year, minYear, maxYear) {
	var previous = year, next = year;
	
	if (year == minYear) {
		previous = year + 1;
		next = year + 2;
	} 
	else if (year = maxYear) {
		previous = year - 2;
		next = year - 1;
	} else {
		previous = year - 1;
		next = year + 1;
	}
	
	return { previous : previous, next : next };
}

/*
	Click redirection
*/

function AfterClick(countryCode, indicator) {
	this.load = function(year) {
		var url = URL_BASE + "/observation/" +  countryCode + "/" + year + "/" + indicator;
		window.location.href = url;
	}
}