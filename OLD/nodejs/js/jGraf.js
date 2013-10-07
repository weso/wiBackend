String.format = function(pattern)
{
	for (var i = 1; i < arguments.length; i++)
	{
		var regex = new RegExp('\\{' + (i - 1) + '\\}', 'g');
		pattern = pattern.replace(regex, arguments[i]);
	}
	return pattern;
};

if (typeof(require) != "undefined") {
	var jSVG = require('./jSVG');
	jSVG = jSVG.jSVG;
}

if (typeof(exports) === "undefined")
	exports = new Object();

var jGraf = exports.jGraf = new (function() {
	var defaultOptions = {
		container: "body",
		width: 600,
		height: 400,
		margins: [10, 20, 10, 5],
		backgroundColour: "none",
		serieColours: ["#FCD271", "#FA5882", "#2BBBD8", "#102E37"],
		groupMargin: 4,
		barMargin: 8,
		xAxis: {
			title: "Meses",
			colour: "none",
			margin: 10,
			tickColour: "none",
			values: ["Ene", "Feb", "Mar", "Abr", "May"],
			"font-family": "Helvetica",
			"font-colour": "#666",
			"font-size": "16px"
		},
		yAxis: {
			title: "Values",
			colour: "none",
			margin: 10,
			tickColour: "#ccc",
			"font-family": "Helvetica",
			"font-colour": "#aaa",
			"font-size": "16px"
		},
		series: [{
            name: "Primero",
            values: [-1, 4, 5, 3, 6]
        }, {
            name: "Segundo",
            values: [1, 7, 5, 6, 4]
        }, {
        	name: "Tercero",
        	values: [0, 1, 2, 3, 4]
        }],
        legend: {
	        show: true,
	        itemSize: 1,
	        "font-colour": "#666",
			"font-family": "Helvetica",
			"font-size": "16px"
        }
	};
	
	this.scatterPlot = function(options) {
		// Options and default options
		options = mergeOptionsAndDefaultOptions(options, defaultOptions);	
	
		// SVG creation
		var svg = getSVG(options);
		
		// Size and margins (%)
		var sizes = getSizes(svg, options);
	
		var g = svg.g();
	
		// Background
	
		setBackground(g, sizes, options); 
				
		// X Axis & Y Axis		
		setAxisY(g, sizes, options);
		var sizesX = setAxisX(g, sizes, options);
				
		// Values
		var length = options.series.length;
	
		var maxHeight = sizes.innerHeight - sizes.xAxisMargin;
		var maxWidth = sizes.innerWidth - sizes.yAxisMargin;
		
		var zeroPos = 0;
		
		if (sizes.minValue <= 0 && sizes.maxValue - sizes.minValue != 0)
			zeroPos = sizes.minValue / (sizes.maxValue - sizes.minValue) * maxHeight;
			
		var zeroPosX = 0;
		
		if (sizesX.minValue <= 0)
			zeroPosX = sizesX.xTickWidth * Math.abs(sizesX.minValue);		
	
		for (var i = 0; i < length; i++) {
			var values = options.series[i].values;
			var valueLength = values.length;
		
			for (var j = 0; j < valueLength; j++) {
				var valueX = values[j][0] ? values[j][0] : 0;
				var valueXPrev = 0;
			
				if (j > 0)
					valueXPrev = values[j - 1][0] ? values[j - 1][0] : 0;
				
				if (!valueX)
					valueX = 0;
					
				if (!valueXPrev)
					valueXPrev = 0;			
			
				var valueY = values[j][1] ? values[j][1] : 0;
				var valueYPrev = 0;
				
				if (j > 0)
					valueYPrev = values[j - 1][1] ? values[j - 1][1] : 0;
				
				if (!valueY)
					valueY = 0;
					
				if (!valueYPrev)
					valueYPrev = 0;
				
				//var xPos = sizes.marginLeft + sizes.yAxisMargin + sizes.groupMargin 
				//		+ sizes.xTickWidth / 2 + (sizes.xTickWidth + sizes.groupMargin) * j;

				//var size = sizesX.maxValue - sizesX.minValue;

				var xPos = sizes.marginLeft + sizes.yAxisMargin
						+ zeroPosX
						+ (sizesX.maxValue - sizesX.minValue == 0 ? 0 : 
							(valueX - sizesX.minValue) / (sizesX.maxValue - sizesX.minValue))
						* maxWidth;

				var yPos = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin
						+ zeroPos
						- (sizes.maxValue - sizes.minValue == 0 ? 1 : 
							(valueY - sizes.minValue) / (sizes.maxValue - sizes.minValue)) 
						* maxHeight;

				g.circle({
					cx: xPos,
					cy: yPos,
					r: 5
				}).style(String.format("fill: {0}", options.serieColours[i]));
				
			}
		}
		
		// Legend
		if (options.legend.show)
			showLegend(g, sizes, options);
			
		return svg;
	};
	
	this.pieChart = function(options) {
		// Options and default options
		options = mergeOptionsAndDefaultOptions(options, defaultOptions);	
		
		// SVG creation
		var svg = getSVG(options);
		
		// Size and margins (%)
		var sizes = getSizes(svg, options);
		
		var g = svg.g();

		// Background
	
		setBackground(g, sizes, options);
		
		// Values
		
		//var radius = Math.min(sizes.width - 2 * sizes.yAxisMargin, sizes.height - 2 * sizes.xAxisMargin) / 2;
		var numberOfPies = options.xAxis.values.length; 
		//var cx = sizes.width / 2;
		//var cy = sizes.height / 2;
		
		//radius /= 2;
		
		if (numberOfPies == 1) {
			var radius = Math.min(sizes.innerWidth - 2 * sizes.yAxisMargin, sizes.innerHeight - 2 * sizes.xAxisMargin) / 2;
			var startX = sizes.innerWidth / 2;
			var startY = sizes.innerHeight / 2;
			var xInc = 0;
			var yInc = 0;
			var half = 1;
		}
		else if (numberOfPies == 2) {
			var radius = Math.min(sizes.innerWidth - 2 * sizes.yAxisMargin, sizes.innerHeight - 2 * sizes.xAxisMargin) / 4;
			var startX = sizes.marginLeft + radius;
			var startY = sizes.innerHeight / 2;
			var xInc = sizes.innerWidth - 2 * radius;
			var yInc = 0;
			var half = 2;
		}
		else {
			if (numberOfPies > 8)
				numberOfPies = 8;
		
			var radius = Math.min(sizes.innerWidth, sizes.innerHeight - 2 * sizes.xAxisMargin) / 4;
			var half = Math.ceil(numberOfPies / 2);
			
			var startX = sizes.marginLeft + radius;
			var startY = sizes.marginTop + radius
			var xInc = (sizes.innerWidth - half * radius * 2) / (half - 1) + 2 * radius;
			var yInc = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin - radius;
		}
		
		// It's presented in two rows
		
		for (var i = 0; i < numberOfPies; i++) {		
			if (i < half) {
				var cx = startX + xInc * i;
				var cy = startY;
			}
			else {
				var rest = numberOfPies - half;
				
				var divisor = rest < half ? rest + 1 : rest - 1;
				var margin = (sizes.innerWidth - rest * radius * 2) / (divisor);
				xInc = margin + 2 * radius;
				var pieMarginLeft = rest < half ? margin : 0;
			
				var cx = sizes.marginLeft + pieMarginLeft + radius + xInc * (i % half);
				var cy = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin - radius;
			}
			
			// Label
			var labelY = cy + radius + sizes.xAxisMargin / 2;
			var text = options.xAxis.values[i] ? options.xAxis.values[i] : "";
			
			g.text({
				x: cx,
				y: labelY,
				value: text
			}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: middle;dominant-baseline: middle", 
				options.xAxis["font-colour"],
				options.xAxis["font-family"],
				options.xAxis["font-size"]));
			
			// Pie
			
			var total = 0;
			var numberOfGreaterThanZero = 0;
			
			var length = options.series.length;
			
			for (var j = 0; j < length; j++) {
				var value = Math.abs(options.series[j].values[i]);
				
				if (!value)
					value = 0;
				
				if (value != 0)
					numberOfGreaterThanZero++;
				
				total += value;
			}
				
			var angles = []
		    
		    for(var j = 0; j < length; j++) {
				var value = Math.abs(options.series[j].values[i] / total * Math.PI * 2);
				
				if (!value)
					value = 0;
				
				angles[j] = value; 
		    }

		    if (numberOfGreaterThanZero == 1) {
		    	var colour = "";
		    	
		    	for(var j = 0; j < length; j++)
		    		if (angles[j] != 0) {
		    			colour = options.serieColours[j];
			    		break;
		    		}
		    		
		    	g.circle({
			    	cx: cx,
			    	cy: cy,
			    	r: radius
		    	}).style(String.format("fill: {0}", colour));
		    }
		    else {
			    var startangle = 0;
				
				for (var j = 0; j < length; j++) {
					var endangle = startangle + angles[j];
			
			        // Compute the two points where our wedge intersects the circle
			        // These formulas are chosen so that an angle of 0 is at 12 o'clock
			        // and positive angles increase clockwise.
			        var x1 = cx + radius * Math.sin(startangle);
			        var y1 = cy - radius * Math.cos(startangle);
			        var x2 = cx + radius * Math.sin(endangle);
			        var y2 = cy - radius * Math.cos(endangle);
		        
			        // This is a flag for angles larger than than a half circle
			        var big = 0;
			        
			        if (endangle - startangle > Math.PI) 
			        	big = 1;
			        
			        // We describe a wedge with an <svg:path> element
			        // Notice that we create this with createElementNS()
			        var path = g.path();
			        
			        // This string holds the path details
			        var d = "M " + cx + "," + cy +  // Start at circle center
			            " L " + x1 + "," + y1 +     // Draw line to (x1,y1)
			            " A " + radius + "," + radius +       // Draw an arc of radius r
			            " 0 " + big + " 1 " +       // Arc details...
			            x2 + "," + y2 +             // Arc goes to to (x2,y2)
			            " Z";                       // Close path back to (cx,cy)
			            
			        path.attribute("d", d).style(String.format("fill: {0}", options.serieColours[j]));
			        
			        startangle = endangle;
				}
			}
		}
		
		// Legend
		if (options.legend.show)
			showLegend(g, sizes, options);
			
		return svg;
	}
	
	this.polarChart = function(options) {
		// Options and default options
		options = mergeOptionsAndDefaultOptions(options, defaultOptions);	
		
		// SVG creation
		var svg = getSVG(options);
		
		// Size and margins (%)
		var sizes = getSizes(svg, options);
		
		var g = svg.g();

		// Background
	
		setBackground(g, sizes, options);
		
		// Axis
		
		var radius = Math.min(sizes.innerWidth - 2 * sizes.yAxisMargin, sizes.innerHeight - 2 * sizes.xAxisMargin) / 2;
		var numberOfVertices = Math.max(options.xAxis.values.length, sizes.maxValueLength); 
		var cx = sizes.width / 2;
		var cy = sizes.height / 2;

		// Vertex calculation
		
		var vertices = [];
		
		for (var i = 0; i < numberOfVertices; i++) {
			vertices.push({
				x: cx + radius * Math.cos((2 * Math.PI * i) / numberOfVertices - Math.PI / 2),
				y: cy + radius * Math.sin((2 * Math.PI * i) / numberOfVertices - Math.PI / 2)
			});
		}
		
		// Radius
		
		for (var i = 0; i < numberOfVertices; i++) {
			var vertex = vertices[i];
			
		  	g.line({
		  		x1: cx,
		  		y1: cy,
		  		x2: vertex.x,
		  		y2: vertex.y
		  	}).style(String.format("stroke: {0};", options.yAxis.tickColour));
		}
		
		// Label 
		
		for (var i = 0; i < numberOfVertices; i++) {  	
			var x = cx + (radius + sizes.xAxisMargin / 2) * Math.cos((2 * Math.PI * i) / numberOfVertices - Math.PI / 2);
			var y = cy + (radius + sizes.xAxisMargin / 2) * Math.sin((2 * Math.PI * i) / numberOfVertices - Math.PI / 2);

			var value = options.xAxis.values[i] ? options.xAxis.values[i] : "";

		  	g.text({
				x: x,
				y: y,
				value: value
			}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: middle;dominant-baseline: middle", 
				options.xAxis["font-colour"],
				options.xAxis["font-family"],
				options.xAxis["font-size"]));
		}
		
		// Polygon sides
		
		var maxValue = sizes.maxValue;
		var minValue = sizes.minValue
		
		var ticksY = maxValue - minValue;
		
		var numberOfAxis = getNumberOfAxis(ticksY);
		ticksY = numberOfAxis.step;
		maxValue = numberOfAxis.max;

		var radiusInc = radius / ticksY;
		var tickInc = (maxValue - minValue) / ticksY;
		
		for (var i = 0; i <= ticksY; i++) {
			var polygonVertices = [];
			var polygonRadius = radiusInc * i;
		
			// Label (Vertical Number)
			
			g.text({
				x: cx - sizes.xAxisMargin / 2,
				y: cy - polygonRadius,
				value: minValue + i * tickInc
			}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: end;dominant-baseline: middle", 
				options.yAxis["font-colour"],
				options.yAxis["font-family"],
				options.yAxis["font-size"]));
		
			// Polygon vertex calculation
			
			for (var j = 0; j < numberOfVertices; j++) {
				polygonVertices.push({
					x: cx + polygonRadius * Math.cos((2 * Math.PI * j) / numberOfVertices - Math.PI / 2),
					y: cy + polygonRadius * Math.sin((2 * Math.PI * j) / numberOfVertices - Math.PI / 2)
				});
			}
			
			// Polygon side drawing
			
			for (var j = 0; j < numberOfVertices; j++) {	
				var vertex = polygonVertices[j];
				var vertexPrev = j == 0 ? polygonVertices[numberOfVertices - 1] : polygonVertices[j - 1];
				
			  	g.line({
			  		x1: vertexPrev.x,
			  		y1: vertexPrev.y,
			  		x2: vertex.x,
			  		y2: vertex.y
			  	}).style(String.format("stroke: {0};", options.yAxis.tickColour));
			}
		}
			
		// Values
		
		var zeroRadius = Math.abs(minValue / (maxValue - minValue) * radius);
		var length = options.series.length;
		var polygonVertices = [];
		
		// Vertex calculation
		
		for (var i = 0; i < length; i++) {
		
			polygonVertices[i] = new Array();
		
			for (j = 0; j < numberOfVertices; j++) {
				var value = options.series[i].values[j];
				
				if (!value)
					value = 0;
				
				if (value < 0) {
					var valueRadius = (Math.abs(value) / minValue) * radius;
				}
				else {
					var valueRadius = (value / maxValue) * (radius - zeroRadius) + zeroRadius;
				}
				
				var x = cx + valueRadius * Math.cos((2 * Math.PI * j) / numberOfVertices - Math.PI / 2);
				var y = cy + valueRadius * Math.sin((2 * Math.PI * j) / numberOfVertices - Math.PI / 2);
					
				polygonVertices[i].push({
					x: x,
					y: y
				});
			}
		}	
		
		// Polygon drawing
		
		for (var i = 0; i < length; i++) {
			for (var j = 0; j < numberOfVertices; j++) {
				var vertex = polygonVertices[i][j];
				var vertexPrev = j == 0 ? polygonVertices[i][numberOfVertices - 1] : polygonVertices[i][j - 1];
			
				g.circle({
					cx: vertex.x,
					cy: vertex.y,
					r: 5
				}).style(String.format("fill: {0}", options.serieColours[i]));
				
				g.line({
			  		x1: vertexPrev.x,
			  		y1: vertexPrev.y,
			  		x2: vertex.x,
			  		y2: vertex.y
			  	}).style(String.format("stroke: {0};", options.serieColours[i]));
			}
		}
		
		// Legend
		if (options.legend.show)
			showLegend(g, sizes, options);
			
		return svg;
	};
	
	function getNumberOfAxis(number) {
		while(true) {
			
			if (number % 4 == 0)
				return {
					max: number,
					step: 4	
				};
				
			if (number % 5 == 0)
				return {
					max: number,
					step: 5	
				};
			
			number++;
		}
	};
	
	this.barChart = function(options) {
		// Options and default options
		options = mergeOptionsAndDefaultOptions(options, defaultOptions);	
		
		// SVG creation
		var svg = getSVG(options);
		
		// Size and margins (%)
		var sizes = getSizes(svg, options);
		
		var g = svg.g();

		// Background
	
		setBackground(g, sizes, options); 
				
		// X Axis & Y Axis		
		setAxis(g, sizes, options);
		
		// Values
		var length = sizes.maxValueLength;
		var numberOfSeries = options.series.length;
		
		var barMargin = options.barMargin * sizes.xTickWidth / 100;	
		var groupMargin = options.groupMargin * sizes.xTickWidth / 100;
		
		//var barWidth = sizes.xTickWidth / numberOfSeries - 2 * sizes.barMargin;
		var barWidth = sizes.barWidth;

		var maxHeight = sizes.innerHeight - sizes.xAxisMargin;
		var zeroPos = sizes.minValue / (sizes.maxValue - sizes.minValue) * maxHeight;
		
		for (var i = 0; i < length; i++) {
			for (var j = 0; j < numberOfSeries; j++) {
				var value = options.series[j].values[i];
				
				if (!value)
					value = 0;
				
				var xPos = sizes.marginLeft + sizes.yAxisMargin + sizes.groupMargin * (2 * i + 1) + sizes.barMargin
						+ sizes.xTickWidth * i 
						+ (barWidth + 2 * sizes.barMargin) * j;
						
				if (value >= 0)	{
					var yPos = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin
							+ zeroPos
							- (value / (sizes.maxValue - sizes.minValue)) 
							* maxHeight;
				}
				else {
					var yPos = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin
							+ zeroPos
				}
						
				var height = (Math.abs(value) / (sizes.maxValue - sizes.minValue)) 
						* (sizes.innerHeight - sizes.xAxisMargin);
							
				g.rectangle({
					x: xPos,
					y: yPos,
					width: barWidth,
					height: height
				}).style(String.format("fill: {0}", options.serieColours[j])); 
			}
		}
		
		// Legend
		if (options.legend.show)
			showLegend(g, sizes, options);
			
		return svg;
	};

	this.lineChart = function(options) {
		// Options and default options
		options = mergeOptionsAndDefaultOptions(options, defaultOptions);	
		
		// SVG creation
		var svg = getSVG(options);
		
		// Size and margins (%)
		var sizes = getSizes(svg, options);
		
		var g = svg.g();
	
		// Background
	
		setBackground(g, sizes, options); 
				
		// X Axis & Y Axis		
		setAxis(g, sizes, options);
				
		// Values
		var length = options.series.length;
		var valueLength = sizes.maxValueLength;
	
		var maxHeight = sizes.innerHeight - sizes.xAxisMargin;
		var zeroPos = sizes.minValue / (sizes.maxValue - sizes.minValue) * maxHeight;
	
		for (var i = 0; i < length; i++) {
			for (var j = 0; j < valueLength; j++) {
				var value = options.series[i].values[j];
				var valuePrev = j > 0 ? options.series[i].values[j - 1] : 0;
				
				if (!value)
					value = 0;
					
				if (!valuePrev)
					valuePrev = 0;
				
				var xPos = sizes.marginLeft + sizes.yAxisMargin + sizes.groupMargin 
						+ sizes.xTickWidth / 2 + (sizes.xTickWidth + 2 * sizes.groupMargin) * j;

				var yPos = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin
						+ zeroPos
						- (value / (sizes.maxValue - sizes.minValue)) 
						* maxHeight;

				var xPosPrev = sizes.marginLeft + sizes.yAxisMargin + sizes.groupMargin 
						+ sizes.xTickWidth / 2 + (sizes.xTickWidth + 2 * sizes.groupMargin) * (j - 1);	

				var yPosPrev = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin
						+ zeroPos
						- (valuePrev / (sizes.maxValue - sizes.minValue)) 
						* maxHeight;

				g.circle({
					cx: xPos,
					cy: yPos,
					r: 5
				}).style(String.format("fill: {0}", options.serieColours[i]));
				
				if (j > 0) {
					g.line({
						x1: xPosPrev,
						x2: xPos,
						y1: yPosPrev,
						y2: yPos
					}).style(String.format("stroke: {0}", options.serieColours[i]));
				}
			}
		}
		
		// Legend
		if (options.legend.show)
			showLegend(g, sizes, options);
			
		return svg;
	};
	
	function showLegend(container, sizes, options) {
		var length = options.series.length;
	
		var xPos = sizes.width - sizes.marginRight * 0.2;
	
		for (var i = 0; i < length; i++) {
			var yPos = sizes.marginTop + (sizes.legendItemSize + sizes.barMargin) * 2.5 * i;
		
			container.circle({
				cx: xPos,
				cy: yPos,
				r: sizes.legendItemSize
			}).style(String.format("fill: {0}", options.serieColours[i]));
			
			container.text({
				x: xPos - 2 * sizes.legendItemSize,
				y: yPos,
				value: options.series[i].name
			}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: end;dominant-baseline: middle", 
				options.legend["font-colour"],
				options.legend["font-family"],
				options.legend["font-size"]));
		}
	}
	
	function setBackground(container, sizes, options) {
		container.rectangle({
			x: 0,
			y: 0,
			width: sizes.width,
			height: sizes.height
		}).style(String.format("fill: {0}", options.backgroundColour)); 
	}
	
	function getSVG(options) {
		var svgOptions = {
			container: options.container,
			width: options.width,
			height: options.height	
		};
		
		var svg = jSVG.svg(svgOptions);
		
		return svg;
	}
	
	function mergeOptionsAndDefaultOptions(options, defaultOptions) {
		if (options) {
			if (typeof options === "string")
				options = { container: options };
		
			var auxOptions = clone(defaultOptions);
			
			for (var option in options)
				auxOptions[option] = mergeOptions(auxOptions[option], options[option]);
			
			options = auxOptions;
		}
		else
			options = clone(defaultOptions);
			
		return options;
	};
	
	function mergeOptions(to, from) {
		if (from instanceof Array) {
			return from;
		}
		else if (typeof from === "object") {
			for (var option in from) {
				to[option] = mergeOptions(to[option], from[option]);
			}

			return to;
		}
		else
			return from;
	};
	
	function clone(obj) {
		// Not valid for copying objects that contain methods
	    return JSON.parse(JSON.stringify(obj));
	}
	
	function setAxis(container, sizes, options) {
		setAxisY(container, sizes, options);
		setAxisDiscreteX(container, sizes, options);
	};
	
	function setAxisY(container, sizes, options) {
		// Y Axis

		container.line({
			x1: sizes.marginLeft + sizes.yAxisMargin,
			x2: sizes.marginLeft + sizes.yAxisMargin,
			y1: sizes.marginTop,
			y2: sizes.innerHeight + sizes.marginTop - sizes.xAxisMargin
		}).style(String.format("stroke: {0}", options.yAxis.colour));
	
		// Y Axis Ticks
		
		var length = sizes.ticksY + 1;
		
		// Line
		for (var i = 0; i < length; i++) {
			container.line({
				x1: sizes.marginLeft + sizes.yAxisMargin,
				x2: sizes.marginLeft + sizes.innerWidth,
				y1: sizes.marginTop + i * sizes.yTickHeight,
				y2: sizes.marginTop + i * sizes.yTickHeight
			}).style(String.format("stroke: {0}", options.yAxis.tickColour));
		}
			
		// Y Axis Labels
		for (var i = 0; i < length; i++) {	
			container.text({
				x: sizes.marginLeft + sizes.yAxisMargin / 1.2,
				y: sizes.marginTop + i * sizes.yTickHeight,
				value: (sizes.maxValue - i * sizes.valueInc).toFixed(0)
			}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: end;dominant-baseline: middle", 
				options.yAxis["font-colour"],
				options.yAxis["font-family"],
				options.yAxis["font-size"]));
		}
		
		// Y Axis Title
		var x = sizes.marginLeft;
		var y = sizes.marginTop + (sizes.innerHeight - sizes.xAxisMargin) / 2;
		
		container.text({
			x: x,
			y: y,
			value: options.yAxis.title,
			transform: String.format("rotate(-90 {0}, {1})", x, y)
		}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: middle", 
				options.yAxis["font-colour"],
				options.yAxis["font-family"],
				options.yAxis["font-size"]));
	}

	function setAxisDiscreteX(container, sizes, options) {		
		// X Axis
		
		var maxHeight = sizes.innerHeight - sizes.xAxisMargin;
		var zeroPos = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin	
					+ sizes.minValue / (sizes.maxValue - sizes.minValue) * maxHeight;
	
		container.line({
			x1: sizes.marginLeft + sizes.yAxisMargin,
			x2: sizes.marginLeft + sizes.innerWidth,
			y1: zeroPos,
			y2: zeroPos
		}).style(String.format("stroke: {0}", options.xAxis.colour));
		
		// X Axis Ticks
		
		var length = sizes.maxValueLength;
		
		for (var i = 0; i < length; i++) {
			// Line
			
			var xPos = sizes.marginLeft + sizes.yAxisMargin + sizes.groupMargin 
				+ sizes.xTickWidth / 2 + (sizes.xTickWidth + 2 * sizes.groupMargin) * i;
		
			container.line({
				x1: xPos,
				x2: xPos,
				y1: sizes.marginTop,
				y2: sizes.innerHeight + sizes.marginTop - sizes.xAxisMargin
			}).style(String.format("stroke: {0}", options.xAxis.tickColour));
			
			// Label
			
			var value = options.xAxis.values[i] ? options.xAxis.values[i] : "";
			
			container.text({
				x: xPos,
				y: sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin / 2,
				value: value
			}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: middle", 
				options.xAxis["font-colour"],
				options.xAxis["font-family"],
				options.xAxis["font-size"]));
		}
		
		// X Axis Title
		
		container.text({
			x: sizes.marginLeft + sizes.yAxisMargin + (sizes.innerWidth - sizes.yAxisMargin) / 2,
			y: sizes.marginTop + sizes.innerHeight + sizes.xAxisMargin / 2,
			value: options.xAxis.title
		}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: middle", 
				options.xAxis["font-colour"],
				options.xAxis["font-family"],
				options.xAxis["font-size"]));
	}
	
	// For scatter plot
	function setAxisX(container, sizes, options) {		
		// X Axis
		
		var maxHeight = sizes.innerHeight - sizes.xAxisMargin;
		var divisor = sizes.maxValue - sizes.minValue != 0 ? sizes.maxValue - sizes.minValue : 1;
		var zeroPos = sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin	
					+ sizes.minValue / divisor * maxHeight;
	
		container.line({
			x1: sizes.marginLeft + sizes.yAxisMargin,
			x2: sizes.marginLeft + sizes.innerWidth,
			y1: zeroPos,
			y2: zeroPos
		}).style(String.format("stroke: {0}", options.xAxis.colour));
	
		// X Axis Ticks
		
		var maxAndMinValues = getMaxAndMinValuesAxisX(options);
		var maxValue = maxAndMinValues.max;
		var minValue = maxAndMinValues.min; 	
		var maxValueLength = maxAndMinValues.valueLength;
		
		var ticksX = (maxValue - minValue) / maxAndMinValues.inc;
		var xTickWidth = (sizes.innerWidth - sizes.yAxisMargin) / (ticksX);	
		var incX = maxAndMinValues.inc;
			
		var length = ticksX + 1;
	
		for (var i = 0; i < length; i++) {
			// Line
			
			var xPos = sizes.marginLeft + sizes.yAxisMargin + xTickWidth * i;
		
			container.line({
				x1: xPos,
				x2: xPos,
				y1: sizes.marginTop,
				y2: sizes.innerHeight + sizes.marginTop - sizes.xAxisMargin
			}).style(String.format("stroke: {0}", options.xAxis.tickColour));
			
			// Label
			
			var value = minValue + incX * i;
			
			container.text({
				x: xPos,
				y: sizes.marginTop + sizes.innerHeight - sizes.xAxisMargin / 2,
				value: value
			}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: middle", 
				options.xAxis["font-colour"],
				options.xAxis["font-family"],
				options.xAxis["font-size"]));
		}
		
		// X Axis Title
		
		container.text({
			x: sizes.marginLeft + sizes.yAxisMargin + (sizes.innerWidth - sizes.yAxisMargin) / 2,
			y: sizes.marginTop + sizes.innerHeight + sizes.xAxisMargin / 2,
			value: options.xAxis.title
		}).style(String.format("fill: {0};font-family:{1};font-size:{2};text-anchor: middle", 
				options.xAxis["font-colour"],
				options.xAxis["font-family"],
				options.xAxis["font-size"]));
				
		return {
			maxAndMinValues: maxAndMinValues,
			maxValue: maxValue,
			minValue: minValue, 	
			maxValueLength: maxValueLength,
		
			ticksX: ticksX,
			xTickWidth: xTickWidth,	
			incX: incX,	
		};
	}

	
	function getSizes(svg, options) {
		var width = svg.width(); 
		var height = svg.height();
		var marginTop = height * options.margins[0] / 100;
		var marginRight = width * options.margins[1] / 100;
		var marginBottom = height * options.margins[2] / 100;
		var marginLeft = width * options.margins[3] / 100;
		var yAxisMargin = options.yAxis.margin * width / 100;
		var xAxisMargin = options.xAxis.margin * height / 100;
		var innerWidth = width - marginLeft - marginRight;
		var innerHeight = height - marginTop - marginBottom;
		
		// Max value & min value
		var maxAndMinValues = getMaxAndMinValuesAxisY(options);
		var maxValue = maxAndMinValues.max;
		var minValue = maxAndMinValues.min;
		var maxValueLength = maxAndMinValues.valueLength; 	
		
		var ticksY = (maxValue - minValue) / maxAndMinValues.inc;
		var yTickHeight = ticksY != 0 ? (innerHeight - xAxisMargin) / ticksY : 0;
		//var xTickWidth = (innerWidth - yAxisMargin - (maxAndMinValues.valueLength + 1) 
		//		* groupMargin) / maxAndMinValues.valueLength;
		var xTickWidth = (innerWidth - yAxisMargin) / maxAndMinValues.valueLength;		
		
		var groupMargin = options.groupMargin * xTickWidth / 100;	
		xTickWidth -= 2 * groupMargin;
			
		var barWidth = xTickWidth / options.series.length;	
		var barMargin = options.barMargin * barWidth / 100;	
		barWidth -= 2 * barMargin;			
				
		var valueInc = ticksY != 0 ? (maxValue - minValue) / ticksY : 0;

		var legendItemSize = options.legend.itemSize * width / 100;

		return {
			width : width,
			height : height,
			marginTop : marginTop,
			marginRight : marginRight,
			marginBottom : marginBottom,
			marginLeft : marginLeft,
			
			innerWidth : innerWidth,
			innerHeight : innerHeight,
			
			yAxisMargin: yAxisMargin,
			xAxisMargin: xAxisMargin,
			
			maxValue: maxValue,
			minValue: minValue,
			maxValueLength: maxValueLength,
			
			ticksY: ticksY,
			yTickHeight: yTickHeight,
			xTickWidth: xTickWidth,
			valueInc: valueInc,
			
			barMargin: barMargin,
			groupMargin: groupMargin,
			barWidth: barWidth,
			
			legendItemSize: legendItemSize
		};
	}
	
	function getMaxAndMinValuesAxisY(options) {
		var maxValue = 0, minValue = Number.MAX_VALUE;
		
		var length = options.series.length;
		var valueLength = null;
		var value = null;
		var maxValueLength = 0;
	
		for (var i = 0; i < length; i++) {
			valueLength = options.series[i].values.length;
			
			if (valueLength > maxValueLength)
				maxValueLength = valueLength;
			
			for (var j = 0; j < valueLength; j++) {
				value = options.series[i].values[j];
				
				if (value instanceof Array)
					value = value[1] ? value[1] : 0;
				
				if (value > maxValue)
					maxValue = value;
					
				if (value < minValue)
					minValue = value;
			}
		}
		
		var maxAndMinValues = getNearestNumber(minValue,maxValue);
	
		return { 
			max: maxAndMinValues.max, 
			min: maxAndMinValues.min, 
			valueLength: maxValueLength,
			inc: maxAndMinValues.inc
		};
	}
	
	// For scatter plot
	function getMaxAndMinValuesAxisX(options) {
		var maxValue = 0, minValue = Number.MAX_VALUE;
		
		var length = options.series.length;
		var valueLength = null;
		var value = null;
	
		for (var i = 0; i < length; i++) {
			valueLength = options.series[i].values.length;
			
			for (var j = 0; j < valueLength; j++) {	
				value = options.series[i].values[j][0] ? options.series[i].values[j][0] : 0;
					
				if (value > maxValue)
					maxValue = value;
						
				if (value < minValue)
					minValue = value;
			}
		}
		
		var maxAndMinValues = getNearestNumber(minValue, maxValue);
	
		return { 
			max: maxAndMinValues.max, 
			min: maxAndMinValues.min, 
			inc: maxAndMinValues.inc
		};
	}
	
	function getNearestNumber(minValue, maxValue) {
		var pow = getNearestPow(maxValue - minValue);
		
		return  {
			max: Math.ceil(maxValue / pow) * pow,
			min: Math.ceil(minValue / pow) * pow,
			inc: pow
		}
	}
	
	function getNearestPow(number) {
		var pow = 0;
	
		if (number < 15) {
			pow = 1;
		}
		else if (number < 30) {
			pow = 5;
		}
		else {
			var numberOfDigits = 1 + Math.floor(Math.log(number) / Math.log(10));
			pow = Math.pow(10, numberOfDigits - 1);
		}
		
		return pow;
	}
	
})();