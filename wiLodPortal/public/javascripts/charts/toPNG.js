$(function() {
	var processedData = processData(chartData);	

	var container = "#result";
	
	
	loadCharts(container, processedData);
	
	$(container).hide();
})

function Country(code, name, url) {
	this.code = code;
	this.name = name;
	this.url = url;
	this.data = new HashTable();
	
	this.getRegion = function() {
		return new Region(this.code, this.name, this.data.values());
	}
}

function cmpCountry(a, b) {
	if(a.name < b.name) 
		return -1;
	
	if(a.name > b.name) 
		return 1;

		return 0;
}

function turnSVGintoImage(content, container) {
    var canvas = document.createElement('canvas');

	    // Draw svg on canvas
	    canvg(canvas, content);

	    // Change img be SVG representation
	    var theImage = canvas.toDataURL('image/png');
	    
	    var img = document.createElement("img");
	    img.setAttribute('src', theImage);
	    
	    $(container).append(img);
 
	    //showOverlaidImage(theImage);
}

function loadCharts(container, data)
{
	var params = new Params();
	var key;
	
	var indicators = data.indicators.keys();

	for (var ind = 0; ind < indicators.length; ind++) {	
		var indicatorName = indicators.get(ind);
		var orderedCountries = data.indicators.getItem(indicatorName).values();
		orderedCountries.sort(cmpCountry);
		
		var div = document.createElement("div");
		div.className = "indicatorName";
		div.innerHTML = indicatorName;
		$(container).append(div);
		
		div = document.createElement("div");
		div.className = "indicatorContainer";
		div.id = "divSVGContainer_" + (ind + 1);
		$(container).append(div);
		
		for (var i = 0; i < orderedCountries.length; i++)
			params.regions[i] = orderedCountries[i].getRegion();
			
		params.indexes = data.years.getArray();
		
		params.container = "#" + div.id;
		
		var options = new Options(barChartOptions);
		params.options = options;
		
		var connector = new D3Connector();
		
		var svgID = new connector.drawBarChart(params).svgID;
	/*	
		var button = document.createElement("button");
		button.className = "generatePNG";
		button.innerHTML = "Generate PNG image";
		button.svgID = "#" + div.id;
		button.onclick = turnSVGintoImage;
		$(div).append(button);
	*/
	
		turnSVGintoImage($("#" + div.id).html().trim(), "#images");
	}
}