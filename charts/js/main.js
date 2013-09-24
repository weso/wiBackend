$(function() {
	console.log(observations.observations.collection);
	
	//loadCharts();
})

// Indicadores

function loadCharts()
{
	var connector = new D3Connector();

	// Organization comparison

	p = new Params();
	
	p.regions[4] = new Region("United States", [9]);
	p.regions[3] = new Region("Spain", [7]);
	p.regions[2] = new Region("Turkey", [5]);
	p.regions[1] = new Region("Switzerland", [4]);
	p.regions[0] = new Region("Zimbabwe", [1]);
	
	p.indexes = [ "Greece", "Portugal", "Spain", "Holland", "Finland" ];
	
	p.container = "#indicator-organization-comparison-countries";
	p.labels = ["Indicador 1"];
	p.options.groupPadding = 40;
	p.options.barPadding = 20;
	p.options.margins = [10, 0, 40, 0];
	p.options.height = 220;
	p.options.showLabels = true;
	p.options.showXAxisLabel = true;
	p.options.showYAxisLabel = true;
	p.options.showValueOnBar = true;
	
	p.options.colours = ["#aaa", "#aaa", "#444", "#aaa", "#aaa"];

	p.options.showYAxisLabel = false;
	p.options.showLabels = false;
	p.options.showBarLabels = true;
	p.options.mean = 5.2;
	p.options.meanColour = "#aaa";
	p.options.median = 5;
	p.options.medianColour = "#111";

	chart = new connector.drawBarChart(p);
	barCharts.push(chart);
	
		// ELIMINAR
	auxRegions.push(p.regions);
	
	p = new Params();
	
	p.regions[0] = new Region("Spain", [7]);
	p.regions[1] = new Region("European Union", [7.5]);


	p.indexes = [ "Spain", "European Union" ];
	
	p.container = "#indicator-organization-comparison-mean";
	p.labels = ["Indicador 1"];
	p.options.groupPadding = 40;
	p.options.barPadding = 40;
	p.options.margins = [10, 0, 40, 0];
	p.options.height = 220;
	p.options.showLabels = true;
	p.options.showXAxisLabel = true;
	p.options.showYAxisLabel = true;
	p.options.showValueOnBar = true;
	
	p.options.colours = [ "#444", "#aaa" ];

	p.options.showYAxisLabel = false;
	p.options.showLabels = false;
	p.options.showBarLabels = true;

	chart = new connector.drawBarChart(p);
	barCharts.push(chart);
	
		// ELIMINAR
	auxRegions.push(p.regions);
	
	p = new Params();
	
	p.regions[5] = new Region("2007", [10]);
	p.regions[4] = new Region("2008", [9]);
	p.regions[3] = new Region("2009", [9]);
	p.regions[2] = new Region("2010", [8]);
	p.regions[1] = new Region("2011", [8]);
	p.regions[0] = new Region("2012", [8]);
	
	p.indexes = ["2007", "2008", "2009", "2010", "2011", "2012"];
	
	p.container = "#indicator-organization-comparison-progression";
	p.labels = ["Indicador 1"];
	p.options.groupPadding = 0;
	p.options.barPadding = 20;
	p.options.margins = [10, 0, 40, 0];
	p.options.height = 220;
	p.options.showLabels = true;
	p.options.showXAxisLabel = true;
	p.options.showYAxisLabel = true;
	p.options.showValueOnBar = true;
	
	p.options.colours = ["#ccc", "#ccc", "#ccc", "#ccc", "#ccc", "#ccc"];

	p.options.showYAxisLabel = false;
	p.options.showLabels = false;
	p.options.showBarLabels = true;
	/*
	p.options.mean = 6.84;
	p.options.meanColour = "#91bf39";
	p.options.median = 7;
	p.options.medianColour = "#111";
	*/

	chart = new connector.drawBarChart(p);
	barCharts.push(chart);
	
		// ELIMINAR
	auxRegions.push(p.regions);
	
	p = new Params();
	p.container = "#" + chart.svgId;
	p.indexes = ["2007", "2008", "2009", "2010", "2011", "2012"];
	p.options.min = null;
	p.options.max = null;
	p.regions[0] = new Region("Spain", [8, 8, 8, 9, 9, 10]);
	p.options.height = 220;
	p.options.ticks = 3;
	p.options.legend = false;
	p.options.margins = [10, 0, 10, 0];
	p.options.showXAxisLabel = false;
	p.options.colours = ["#666", "#666", "#666", "#666", "#666", "#666"];

	new connector.drawLineChart(p);
	
	p = new Params();
	p.container = "#indicator-organization-comparison-both";
	p.indexes = ["2007", "2008", "2009", "2010", "2011", "2012"];
	p.options.min = null;
	p.options.max = null;
	p.regions[0] = new Region("Spain", [7, 7, 7, 8, 7, 8]);
	p.regions[1] = new Region("EU", [8, 8, 8, 9, 9, 10]);
	p.options.height = 180;
	p.options.ticks = 3;
	p.options.legend = true;
	p.options.margins = [0, 0, 30, 0];
	p.options.showXAxisLabel = true;
	p.options.colours = ["#333", "#888"];
	p.options.legendVerticalPosition = "top";

	new connector.drawLineChart(p);
}