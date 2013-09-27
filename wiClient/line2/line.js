window.onload = function() {
	var div = document.getElementById("line");
	
	var options = {
		container: div,
		graphBorder: 'none',
		width: 500,
		height: 500
	}
	
	generateLineChart(options);
}

function generateLineChart(options) {
	var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
svg.setAttribute('style', options.graphBorder);
svg.setAttribute('width', options.width);
svg.setAttribute('height', options.height);
svg.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xlink", "http://www.w3.org/1999/xlink");
options.container.appendChild(svg);
	
svg.setAttribute("width", options.width);
svg.setAttribute("height", options.height);
	
var rectangle = wesGraphics.rectangle({
	"id": "rectangle",
	"fill": "red",
	"stroke": "black",
	"stroke-width": "5",
	"x": "100",
	"y": "100",
	"width": "100",
	"height": "50"
});
svg.appendChild(rectangle);
}

var wesGraphics = (function() {
	this.rectangle = function(options) {
		var rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
		rect.setAttributeNS(null, "id", "myrect"); 
		rect.setAttributeNS(null, "fill","red");
		rect.setAttributeNS(null, "stroke","black");
		rect.setAttributeNS(null, "stroke-width","5");
		rect.setAttributeNS(null, "x", "100");
		rect.setAttributeNS(null, "y", "100");
		rect.setAttributeNS(null, "width", "100");
		rect.setAttributeNS(null, "height", "50");
	}
})();