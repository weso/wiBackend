function processData(data) {
	var observation, year, contryCode, countryName, countryURI, country, value, indicator;
	var years = new SortedArray();
	var countries; // = new HashTable();
	var indicators = new HashTable();

	for (var i = 0; i < data.length; i++) {
		observation = data[i];
	
		year = observation.year;
		value = observation.value;
		countryCode = observation.countryCode;
		countryName = observation.countryName;
		countryURI = observation.countryUri;
		indicatorName = observation.indicator.name;
		
		countries = indicators.getItem(indicatorName);
		
		if (countries == undefined) {
			countries = new HashTable();
			indicators.setItem(indicatorName, countries);
		}
		
		country = countries.getItem(countryCode);
		
		if (country == undefined) {
			country = new Country(countryCode, countryName, countryURI);
			countries.setItem(countryCode, country);
		}
	
		country.data.setItem(year, value);
	
		years.uniqueInsert(year);
	}
		
	return { indicators: indicators, years: years };
}