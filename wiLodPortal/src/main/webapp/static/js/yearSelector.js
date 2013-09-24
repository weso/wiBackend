function YearSelector(container, height, minYear, maxYear, selectedYear, callback) {
	selectedYear = selectedYear - minYear;
	var years = [];
	var names = [];
	var width = null;
	
	var moving = false;
	
	function init() {
		container = document.getElementById(container);
		width = container.offsetWidth;

		var divSections = document.createElement("div");
		container.appendChild(divSections);
		
		var divNames = document.createElement("div");
		container.appendChild(divNames);
		
		divSections.className = divNames.className = "slider-year-container"
		
		divSections.style.width = divNames.style.width = width + "px";
		divSections.style.height = height + "px";
		
		var numYears = maxYear - minYear + 1;
		
		var yearWidth = parseInt(width / numYears);
		var lastYearWidth = width - (yearWidth * (numYears - 1));
		
		// Slider sections
		for (var i = 0; i < numYears; i++) {
			var year = document.createElement("div");
			var name = document.createElement("div");
			
			year.href = name.href = "#";
			
			year.className = i != selectedYear ?  "slider-year" : "slider-year-selected";
			name.className = i != selectedYear ?  "slider-year-name" : "slider-year-name-selected";
			year.onclick = name.onclick = yearOnClick;
			
			name.innerHTML = minYear + i;
			
			divSections.appendChild(year);
			divNames.appendChild(name);
			
			var _width = i < numYears - 1 ? yearWidth : lastYearWidth;
			
			year.style.width = name.style.width = _width + "px";
			year.style.height = height + "px";
			
			years[i] = year;
			names[i] = name;
			
			year.year = name.year = i;
			
			year.onmousedown = yearOnMouseDown;
			year.onmouseup = yearOnMouseUp;
			year.onmousemove = yearOnMouseMove;
		}
		
		$(divSections).mouseleave(function() {
			moving = false;
		});
	}
	
	function yearOnMouseMove() {	
		if (moving && this.year != selectedYear) {
			changeYear(this.year);
		}
	}

	function yearOnMouseDown() {
		if (this.year == selectedYear)
			moving = true;
	}
	
	function yearOnMouseUp() {
		moving = false;
	}
	
	function yearOnClick() {
		changeYear(this.year);
		
		event.stopPropagation();
	}
	
	function changeYear(year) {
		var previousSelected = selectedYear;
		selectedYear = year;
		
		years[previousSelected].className = "slider-year";
		years[selectedYear].className = "slider-year-selected";
		
		names[previousSelected].className = "slider-year-name";
		names[selectedYear].className = "slider-year-name-selected";
		
		callback.load(minYear + year);
	}
	
	init();
}

/*
function setBasicYearSelector(container, minYear, maxYear) {
	var yearSelector = document.getElementById(container);
	
	for (var i = minYear; i <= maxYear; i++)
	{
		var option = document.createElement("option");
		yearSelector.appendChild(option);
		
		text(option, i);
	}
}

function YearSelector(container, minYear, maxYear, selectedYear)
{
	var selected = null;
	var slider = null;
	
	selectYear(selectedYear ? selectedYear : maxYear);
	init();
	
	function selectYear(year)
	{
		if (selected)
			selected.className = "year-name";
			
		selected = document.getElementById(container + "-" + year);
		
		if (selected)
			selected.className = "year-name-selected";
	}
	
	function selectYearByName(year)
	{
		selectYear(year);
		$(slider).slider('value', year);
	}
	
	function init()
	{
		var parent = document.getElementById(container);
		parent.className = "year-selector";
		
		var div = document.createElement("div");
		div.className = "year-selector-wrapper";
		parent.appendChild(div);
		
		slider = document.createElement("div");
		slider.className = "year-selector-slider";
		div.appendChild(slider);
	
		$(slider).slider({
			value : selectedYear,
			min : minYear,
			max : maxYear,
			step : 1,
			slide : function(event, ui) {
				selectYear(ui.value);
			}
		});
		
		var years = document.createElement("section");
		years.className = "available-years";
		parent.appendChild(years);
		
		var columnWidth = 12 / (maxYear - minYear + 1);
		
		for (var i = minYear; i <= maxYear; i++)
		{
			var div = document.createElement("div");
			div.className = "small-" + columnWidth + " large-" + columnWidth + " columns centered";
			years.appendChild(div);
			
			var strong = document.createElement("strong");
			strong.id = container + "-" + i;
			text(strong, i);
			strong.year = i;
			strong.className = "year-name";
			div.appendChild(strong);
			
			strong.onclick = function() {
				selectYearByName(this.year);
			}
			
			if (i == selectedYear)
			{
				strong.className = "year-name-selected";
				selected = strong;
			}
		}
	}
}
*/