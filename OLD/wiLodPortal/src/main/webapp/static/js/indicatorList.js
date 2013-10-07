function IndicatorList(accordion, indicatorList, autocompleteTags)
{
	var openedTab = [];
	var selectedTitle = [];
	
	init();

	function init()
	{
		for (var i = 0; i < indicatorList.length; i++)
		{
			var section = createIndicatorListSection(indicatorList[i], autocompleteTags, 0, true);
		
			accordion.append(section);
		}
	}

	function createIndicatorListSection (indicator, autocompleteTags, depth, opened)
	{
		if (indicator.indicators)
		{	
			var section  = document.createElement('section');
			section.className = (depth == 0 ? 'indicator-section' : '');
			section.id = indicator.name;
								
			var p = document.createElement('p');
			p.className = "title" + depth;
			section.appendChild(p);
			
			if (opened)
			 p.className += " active-node";
			
			var a = document.createElement('a');
			a.className = 'indicator-link';
			a.href = "#";
			text(a, indicator.name);
			
			a.paragraph = p;
			a.depth = depth;
			
			a.onclick = function() { setAsActive(this, depth); };
			
			p.appendChild(a);
			
			autocompleteTags.push(indicator.title);
			
			var content = document.createElement('div');
			content.className = "list-content";
			section.appendChild(content);
			a.content = content;
			
			if (opened)
				content.style.display = "block";
			
			var nav = document.createElement('nav');
			content.appendChild(nav);
			
			var ul = document.createElement('ul');
			ul.className = "no-bullet";
			nav.appendChild(ul);
			
			var indicators = indicator.indicators;
			indicators.sort(sortIndicators);
			
			for (var i = 0; i < indicators.length; i++)
			{
				var li = document.createElement('li');
				li.className = "list-element" + (depth + 1);
				ul.appendChild(li);
			
				autocompleteTags.push(indicators[i].name);
				
				if (indicators[i].indicators)
				{
					var node = document.createElement('div');
				
					var subSection = createIndicatorListSection (indicators[i], autocompleteTags, depth + 1, false);
					node.appendChild(subSection);
					
					li.appendChild(node)
				}
				else
				{
					var p = document.createElement('paragraph');
					li.appendChild(p);
					
					var a = document.createElement('a');
					a.href = indicators[i].uri;
					a.paragraph = p;
					a.depth = depth + 1;
					text(a, indicators[i].name);
					p.appendChild(a);
					
					a.onclick = function() { setAsActive(this, depth + 1); };
				}
			}
			
			return section;
		}
	}
	
	function setAsActive(element, depth)
	{
		for (var i = element.depth; i < openedTab.length; i++)
			openedTab[i].style.display = "none";
			
		if (element.content)
		{
			openedTab[element.depth] = element.content;
			openedTab[element.depth].style.display = "block";
		}
		
		for (var i = 0; i < selectedTitle.length; i++)
			selectedTitle[i].className = "title" + i;
			
		selectedTitle[element.depth] = element.paragraph;
		selectedTitle[element.depth].className = "title" + depth + " active-node";
	}
	
	function sortIndicators(indicator1, indicator2) {
		if (indicator1.name < indicator2.name)
			return -1;
		if (indicator1.name > indicator2.name)
			return 1;
		return 0;
	}
}