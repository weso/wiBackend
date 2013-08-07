var URL_BASE = "http://localhost:8080/wiLodPortal";

$(function(){
	$.ajax({
	  type: "GET",
	  url: URL_BASE + "/observations/ESP",
	  dataType: "json",
	  contentType: "application/javascript; charset=UTF-8"
	}).done(function ( data ) {
		processCountryData(data);
	});
});

function processCountryData(data) {
	var indicatorsPerYear = [];
	var maxYear = 0;
	var minYear = Number.MAX_VALUE;

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
	}

	var accordion = $("#accordion");
	
	var autocompleteTags = [];

	var indicatorList = new Object();
	indicatorList.name = "Indicators";
	indicatorList.indicators = indicatorsPerYear[maxYear];

	new IndicatorList(accordion, [ indicatorList ], autocompleteTags);
	
	new YearSelector("year-selector", minYear, maxYear, maxYear);
	setBasicYearSelector("year-select", minYear, maxYear)
}

