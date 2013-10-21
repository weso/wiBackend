var SortedArray = require('../util/SortedArray.js').SortedArray;
var HashTable = require('../util/HashTable.js').HashTable;

exports.DataBase = DataBase; 

var jsonFile = "../../settings/observations/webindex_0.1.json"; 
var jsonData = require(jsonFile); 

function parseData(json) {
	console.log("Reading file: " + jsonFile)
	
	var indicators = new HashTable();
	
	var yearList = new SortedArray();
	var countryList = new SortedArray();
	var indicatorList = new SortedArray();
	
	var countryNameList = new HashTable();

	var length = json.length;
	
	for (var i = 0; i < length; i++) {
		var element = json[i];
		
		var year = element.year;
		var countryCode = element.countryCode;
		var countryName = element.countryName;
		var indicatorCode = element.indicatorCode;
		var value = element.value;
		var sheet = element["sheet-type"];
	
		yearList.uniqueInsert(year);
		countryList.uniqueInsert(countryCode);
		indicatorList.uniqueInsert(indicatorCode);
		countryNameList.setItem(countryCode, countryName);
	
		var indicatorTable = indicators.getItem(indicatorCode);
	
		if (!indicatorTable) {
			indicatorTable = new HashTable();
			indicators.setItem(indicatorCode, indicatorTable);
		}
		
		var countryTable = indicatorTable.getItem(countryCode);
		
		if (!countryTable) {
			countryTable = new HashTable();
			indicatorTable.setItem(countryCode, countryTable);
		}			
	
		var yearTable = countryTable.getItem(year);
		
		if (!yearTable) {
			yearTable = new HashTable();
			countryTable.setItem(year, yearTable);
		}		

		yearTable.setItem(sheet, value);
	}
	
	console.log("End of file reading");
	
	return {
		indicators: indicators,
		yearList: yearList,
		countryList: countryList,
		indicatorList: indicatorList,
		countryNameList: countryNameList
	}
}
 
var processedData = parseData(jsonData); 
 
function DataBase() {
	var observations = [];

	this.find = function(years, countries, indicators, sheet, callback) {
		if (years.length == 0)
			years = processedData.yearList.getArray();
			
		if (countries.length == 0)	
			countries = processedData.countryList.getArray();
			
		if (indicators.length == 0)
			indicators = processedData.indicatorList.getArray();
	
		var indicatorLength = indicators.length;
		
		for (var i = 0; i < indicatorLength; i++) {
			var indicator = indicators[i];
			var indicatorTable = processedData.indicators.getItem(indicator);
		
			if (!indicatorTable)
				continue;
			
			var countryLength = countries.length;
			
			for (var j = 0; j < countryLength; j++) {
				var country = countries[j];
				var countryTable = indicatorTable.getItem(country);
				
				if (!countryTable)
					continue;
					
				var yearLength = years.length;	
					
				for (var k = 0; k < yearLength; k++) {
					var year = years[k];
					var yearTable = countryTable.getItem(year);
					
					if (!yearTable)
						continue;
						
					var value = yearTable.getItem(sheet);	
					var countryName = processedData.countryNameList.getItem(country);
					
					var observation = {
						year: year,
						countryCode: country,
						countryName: countryName,
						indicatorCode: indicator,
						sheet: sheet,
						value: value
					}
					
					observations.push(observation);
				}
			}
		}
	
		callback.call(this, observations);
	}
} 
