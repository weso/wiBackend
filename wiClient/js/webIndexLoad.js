var URL_BASE = "http://localhost:8080/wiLodPortal";
var COUNTRYCODE = "ESP";
var YEAR = 2009;
var MINYEAR = 2009;
var MAXYEAR = 2011;
var INDICATOR = "A";

var charts = [];

$(function(){
	loadIndicatorInfo(COUNTRYCODE, INDICATOR, YEAR, MINYEAR, MAXYEAR);
});

function loadIndicatorInfo(countryCode, indicator, year, minYear, maxYear) {
	// Get country indicators

	$.ajax({
	  type: "GET",
	  url: URL_BASE + "/observations/" +  countryCode,
	  dataType: "json",
	  contentType: "application/javascript; charset=UTF-8"
	}).done(function ( data ) {
		processCountryData(data, countryCode, indicator);
	});
	
	// Get comparing countries
	
	$.ajax({
	  type: "GET",
	  url: URL_BASE + "/observations/barchart/" +  countryCode + "/" + year + "/" + indicator,
	  dataType: "json",
	  contentType: "application/javascript; charset=UTF-8"
	}).done(function ( data ) {
		processComparingCountries(data, countryCode, indicator);
	});
	
	// Previous and next year
	
	var years = getPreviousAndNextYear(year, minYear, maxYear);
	
	// Previous
	$.ajax({
	  type: "GET",
	  url: URL_BASE + "/observations/barchart/" +  countryCode + "/" + years.previous + "/" + indicator,
	  dataType: "json",
	  contentType: "application/javascript; charset=UTF-8"
	}).done(function ( data ) {
		processPreviousComparingCountries(data, countryCode, indicator);
	});
	
	// Next
	$.ajax({
	  type: "GET",
	  url: URL_BASE + "/observations/barchart/" +  countryCode + "/" + years.next + "/" + indicator,
	  dataType: "json",
	  contentType: "application/javascript; charset=UTF-8"
	}).done(function ( data ) {
		processNextComparingCountries(data, countryCode, indicator);
	});
}

function processCountryData(data, countryCode, indicatorName) {
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
		
		var indicator = value.indicator;
		
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
	
	new YearSelector("year-selector", 15, 2009, 2011);
	//setBasicYearSelector("year-select", minYear, maxYear);
	
	// Year progression
	
	processProgressionBarChart(progressionData, "#indicator-progression-years", countryCode, indicatorName);
}

function processComparingCountries(data, countryCode, indicatorName) {

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

	// Indicator
	var p = new Params();
	p.options.groupPadding = 40;
	p.options.barPadding = 40;
	p.options.margins = [10, 0, 40, 0];
	p.options.height = 280;	
	p.options.showBarLabels = true;
	
	processComparingBarChart(data, p, "#indicator-main-indicator", countryCode, indicatorName);
}

function processPreviousComparingCountries(data, countryCode, indicatorName) {
	var p = new Params();
	p.options.groupPadding = 40;
	p.options.barPadding = 20;
	p.options.margins = [0, 0, 0, 0];
	p.options.height = 150;
	p.options.showBarLabels = false;
	
	processComparingBarChart(data, p, "#indicator-progression-indicator-1");
}

function processNextComparingCountries(data, countryCode, indicatorName) {
	var p = new Params();
	p.options.groupPadding = 40;
	p.options.barPadding = 20;
	p.options.margins = [0, 0, 0, 0];
	p.options.height = 150;
	p.options.showBarLabels = false;
	
	processComparingBarChart(data, p, "#indicator-progression-indicator-2");	
}

function processProgressionBarChart(data, container, countryCode, indicatorName) {
	var p = new Params();
	p.options.groupPadding = 0;
	p.options.barPadding = 20;
	p.options.margins = [10, 0, 40, 0];
	p.options.height = 220;
	p.options.showBarLabels = true;
	
	p.regions = [];
	p.indexes = [];
	p.options.colours = [];
	
	for (var i = 0; i < data.length; i++) {
		var year = data[i].year;
		var value = data[i].value;
		
		var region = new Region(year, [value]);
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

function processComparingBarChart(data, p, container, countryCode, indicatorName) {
	p.regions = [];
	p.indexes = [];
	p.options.colours = [];
	
	var mean = 0;
	var median = 0;
	
	for (var i = 0; i < data.observations.length; i++) {
		var countryName = data.observations[i].country.name;
		var value = data.observations[i].value;
		
		var region = new Region(countryName, [value]);
		var colour = countryName == countryCode ? "#333" : "#91bf39";
		
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