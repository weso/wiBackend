var fs = require('fs');
var svg2png = require('svg2png');
var extend = require('util')._extend;

var graphSettings = require('../settings/graph.js');

var DataBase = require('../src/db/database.js').DataBase;
var SortedArray = require('../src/util/SortedArray.js').SortedArray;
var jGraf = require('../src/chart/jGraf.min.js').jGraf;

var yearCheck = /^YEAR\(((\d{4})(,\d{4})*)\)$/;
var indicatorCheck = /^INDICATOR\((([\w\s]+)(,[\w\s]+)*)\)$/;
var countryCheck = /^COUNTRY\((([a-zA-Z]{3})(,[a-zA-Z]{3})*)\)$/;

var selectedSheet = "Imputed";

////////////////////////////////////////////////////////////////
//              PARAMETER AND QUERYSTRING PARSING
////////////////////////////////////////////////////////////////

exports.index = function(req, res){
	var chart = req.params.chart;
	var first = req.params.first;
	var second = req.params.second;
	var third = req.params.third;
	
	var params = null;
	
	if ((params = processParams(first, second, third)).success == false) {
		res.redirect('/help');
		return;	
	}
	
	/*
		WHERE CONDITION
	*/
	
	var yearCondition = params.data.years.getArray();
	var indicatorCondition = params.data.indicators.getArray();
	var countryCondition = params.data.countries.getArray();


	new DataBase().find(yearCondition, countryCondition, indicatorCondition, selectedSheet, function(data) {
		data = processData(data);
		generateImage(res, chart, data.series, data.years.getArray(), req.query);
	});
};

////////////////////////////////////////////////////////////////
//                     PROCESS PARAMS
////////////////////////////////////////////////////////////////

function processParams(first, second, third) {
	var data = {
		years: new SortedArray(),
		indicators: new SortedArray(),
		countries: new SortedArray()	
	};

	if (first && !processParam(first, data))
		return {
			success: false
		};
		
	if (second && !processParam(second, data))
		return {
			success: false
		};
		
	if (third && !processParam(third, data))
		return {
			success: false
		};
	
	return {
		success: true,
		data: data
	};	
}

function processParam(parameter, data) {
	var result = yearCheck.exec(parameter);
	
	if (result != null) {
		processYears(result[1], data);
		return true;
	}
	
	result = indicatorCheck.exec(parameter);
	
	if (result != null) {
		processIndicators(result[1], data);
		return true;
	}
	
	result = countryCheck.exec(parameter);
	
	if (result != null) {
		processCountries(result[1], data);
		return true;
	}
	
	return false;
}

function processYears(years, data) {
	insertElements(data.years, years);
	console.log("Years: " + years);
}

function processIndicators(indicators, data) {
	insertElements(data.indicators, indicators);
	console.log("Indicators: " + indicators);
}

function processCountries(countries, data) {
	insertElements(data.countries, countries);
	console.log("Countries: " + countries);
}

function insertElements(array, elements) {
	elements = elements.split(",");

	var length = elements.length;

	for (var i = 0; i < length; i++) {
		array.uniqueInsert(elements[i]);
	}
}

////////////////////////////////////////////////////////////////
//                     PROCESS DATA
////////////////////////////////////////////////////////////////

function processData(data) {
	var length = data.length;

	var previousIndicatorCode = null;
	var previousCountryCode = null;
	var country = null;

	var indicatorList = [];
	var years = new SortedArray();
	var serie = null;

	for (var i = 0; i < length; i++) {
		var observation = data[i];

		var indicatorCode = observation.indicatorCode;
		var countryCode = observation.countryCode;
		var countryName = observation.countryName;
		var year = observation.year;
		var value = observation.value;
		
		if (previousIndicatorCode != indicatorCode) {
			indicatorList.push(serie = []);
			country = null;
		}
		
		if (previousCountryCode != countryCode || !country) {
			country = {
				name: countryName,
				values: []
			};
			
			serie.push(country);
		}
		
		country.values.push(value);
		years.uniqueInsert(year);
		
		previousIndicatorCode = indicatorCode;
		previousCountryCode = countryCode;
	}
	
	return {
		series : indicatorList.length > 0 ? indicatorList[0] : [],
		years: years
	};
}

////////////////////////////////////////////////////////////////
//                     GENERATE GRAPH
////////////////////////////////////////////////////////////////

function generateImage(response, chart, series, values, querySettings) {
		 
	// Default graph setting cloning
	var settings = extend({}, graphSettings);	 
	 
	mergeQuerySettingsAndDefaultSettings(querySettings, settings);
		
	// Assign serie values
	settings.series = series;
	
	// Assign X Axis labels
	if (!settings.xAxis)
		settings.xAxis = new Object();
		
	settings.xAxis.values = values;

	switch(chart.toUpperCase()) {
		case "LINE":
			chart = jGraf.lineChart(settings);
			break;
		case "PIE":
			chart = jGraf.pieChart(settings);
			break;
		case "POLAR":
			chart = jGraf.polarChart(settings);
			break;
		case "BAR":
		default:
			chart = jGraf.barChart(settings);
			break;
	}

	var svg = chart.toString();

	if (querySettings.format && querySettings.format.toLowerCase() == 'svg') {
		response.setHeader("Content-Type", "image/svg+xml");
	    response.write(svg, "binary");
	    response.end();
	    return;
	}
	
	var fileId = guid();
	var SVG = "tmp/" + fileId + ".svg";
	var PNG = "tmp/" + fileId + ".png";
	
	fs.writeFileSync(SVG, svg); 
	console.log("File SVG generated");	
	
	svg2png(SVG, PNG, function (err) {
	    if(err) {
	        console.log(err);
	        
	        deleteFile(SVG);
	        
	        response.setHeader("Content-Type", "text/html");
	        response.end("There was an error.");
	    } else {
	        console.log("The PNG file was saved");
	        
	        var content = fs.readFileSync(PNG);
	        deleteFile(SVG);
	        deleteFile(PNG);
	         
	        response.setHeader("Content-Type", "image/png");
	        response.write(content, "binary");
	        response.end();
	    }
	});
};

function mergeQuerySettingsAndDefaultSettings(querySettings, defaultSettings) {
	for (var param in querySettings) {
		try {
			var value = JSON.parse(querySettings[param]);
	
			var reference = defaultSettings;
		
			// Split by "." to allow object notation
			var splittedParam = param.split(".");
			var length = splittedParam.length - 1;
			
			for (var i = 0; i < length; i++) {
				var referenceName = splittedParam[i];
			
				if (!reference[referenceName])
					reference[referenceName] = {};
					
				reference = reference[referenceName];
			}
			
			var referenceName = splittedParam[i];
			
			if (!reference[referenceName])
				reference[referenceName] = {};
					
			reference[referenceName] = value;
		}
		catch(e) {
			
		}
	}
}

////////////////////////////////////////////////////////////////
//                     AUX FILE FUNCTIONS
////////////////////////////////////////////////////////////////

function deleteFile(file) {
	fs.unlink(file, function (err) {
	  if (err) 
	  	throw err;
	  console.log("Successfully deleted: " + file);
	});
}

function s4() {
	return Math.floor((1 + Math.random()) * 0x10000)
             .toString(16)
             .substring(1);
};

function guid() {
	return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
         s4() + '-' + s4() + s4() + s4();
};
