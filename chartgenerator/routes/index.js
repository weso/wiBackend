var fs = require('fs');
var svg2png = require('svg2png');

var graphSettings = "settings/graph.json";

var DataBase = require('../src/db/database.js').DataBase;
var SortedArray = require('../src/util/SortedArray.js').SortedArray;
var HashTable = require('../src/util/HashTable.js').HashTable;
var jGraf = require('../src/chart/jGraf.js').jGraf;

var yearCheck = /^YEAR\(((\d{4})(,\d{4})*)\)$/;
var indicatorCheck = /^INDICATOR\(((\w+)(,\w+)*)\)$/;
var countryCheck = /^COUNTRY\((([a-zA-Z]{3})(,[a-zA-Z]{3})*)\)$/;

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
	
	var where = {
		$and: [
			{ "year" : { $in: params.data.years.getArray() } },
			{ "indicatorCode" : { $in: params.data.indicators.getArray() } },
			{ "countryCode" : { $in: params.data.countries.getArray() } },
		]	
	};

	new DataBase().find(where , function(data) {
		data = processData(data);
		generatePNG(res, chart, data.series, data.years.getArray());
	});
};

function showHelp(response) {
	response.redirect("/help");
}

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

function Country(code, name) {
	var data = new HashTable();
	
	this.name = function() {
		return name;
	}
	
	this.insertValue = function(year, value) {
		data.setItem(year, value);
	}
	
	this.toObject = function() {
		return {
			name: name,
			values: data.values()
		};
	}
}

function cmpCountry(a, b) {
	if(a.name() < b.name()) 
		return -1;
	
	if(a.name() > b.name()) 
		return 1;

		return 0;
}

function processData(data) {
	var length = data.length;

	var indicatorTable = new HashTable();
	var years = new SortedArray();
	
	for (var i = 0; i < length; i++) {
		var observation = data[i];
		
		var indicatorCode = observation.indicatorCode;
		var countryCode = observation.countryCode;
		var countryName = observation.countryName;
		var year = observation.year;
		var value = observation.value;
		
		var countriesPerThatIndicator = indicatorTable.getItem(indicatorCode);
		
		if (countriesPerThatIndicator == undefined) {
			countriesPerThatIndicator = new HashTable();
			indicatorTable.setItem(indicatorCode, countriesPerThatIndicator);
		}
		
		var country = countriesPerThatIndicator.getItem(countryCode);
		
		if (country == undefined) {
			country = new Country(countryCode, countryName);
			countriesPerThatIndicator.setItem(countryCode, country);
		}
		
		country.insertValue(year, value);
	
		years.uniqueInsert(year);
	}
	
	// Order data
	
	var indicators = indicatorTable.keys();
	var series = new Array();

	var indicatorsLength = indicators.length;
	indicatorsLength = indicatorsLength > 1 ? 1 : indicatorsLength;

	for (var ind = 0; ind < indicatorsLength; ind++) {	
		var indicatorCode = indicators[ind];
		var orderedCountries = indicatorTable.getItem(indicatorCode).values();
		orderedCountries.sort(cmpCountry);
		
		var length = orderedCountries.length;
		
		for (var i = 0; i < length; i++)
			series.push(orderedCountries[i].toObject());
	}

	return {
		series : series,
		years: years
	};
}

////////////////////////////////////////////////////////////////
//                     GENERATE GRAPH
////////////////////////////////////////////////////////////////

function generatePNG(response, chart, series, values) {
	fs.readFile(graphSettings, 'utf8', function (err, data) {
		if (err) {
			console.log('Graph setting read error: ' + err);
		    return;
		}
		 
		var settings = JSON.parse(data);
		settings.series = series;
		
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
			case "SCATTER":
				chart = jGraf.scatterPlot(settings);
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
	});
};

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
