if (typeof(exports) === "undefined")
	exports = new Object();
	
var jSVG = exports.jSVG = new (function() {
	function jSVGElement(elementName) {
		var namespace = "http://www.w3.org/2000/svg";
		var version = "1.1";
		
		var defaultSVGOptions = {
			container: "body",
			width: 500,
			height: 500,
			version: version
		};
		
		var defaultElementOptions = {
			fill: "black",
			stroke: "none",
			"stroke-width": "1",
			x: "0",
			y: "0",
			width: "10",
			height: "10",
			r: "5",
			cx: "5",
			cy: "5",
			x1: 0,
			x2: 10,
			y1: 0,
			y2: 10
		};
		
		var myTag = elementName;
		var myAttributes = {};
		var myStyle = "";
		var myValue = undefined;
		var myChildNodes = [];
	
		this.svg = function(options) {
			if (options) {
				if (typeof options === "string")
					options = { container: options };
				
				//options = mergeOptions(defaultSVGOptions, options);
			}
			else
				options = {};
		
			//this = document.createElementNS(namespace, "svg");
			return createElement(null, "svg", options, defaultSVGOptions);
	
			//setAttributes(this, options);
			//this.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xlink", "http://www.w3.org/1999/xlink");
			
			//var container = options.container;
		
			//if (typeof container === "string")
			//	container = document.querySelector(container);
			
			//container.appendChild(this);
				
			//return this;
		}
		
		this.elementByName = function(name, options) {
			return createElement(this, name, options, defaultElementOptions);
		}	
		
		this.g = function(options) {
			return createElement(this, "g", options, defaultElementOptions);
		}
		
		this.path = function(options) {
			return createElement(this, "path", options, defaultElementOptions);
		}
			
		this.rectangle = function(options) {
			return createElement(this, "rect", options, defaultElementOptions);
		}
		
		this.circle = function(options) {
			return createElement(this, "circle", options, defaultElementOptions);
		}
		
		this.line = function(options) {
			return createElement(this, "line", options, defaultElementOptions);
		}
		
		this.text = function(options) {
			var element = createElement(this, "text", options, defaultElementOptions);
			element.value(options.value);
			
			return element;
		}
	
		this.attribute = function(name, value) {
			myAttributes[name] = value;
			return this;
		}
		
		this.attributes = function(attributes) {
			myAttributes = attributes;
			return this;
		}
		
		this.style = function(value) {
			myStyle = value;
			return this;
		}
		
		this.value = function(value) {
			myValue = value;
			return this;
		}
		
		this.width = function() {
			return myAttributes.width;
		}
		
		this.height = function() {
			return myAttributes.height;
		}
		
		this.appendChild = function(node) {
			myChildNodes.push(node);
		}
		
		function createElement(parent, elementName, attributes, defaultAttributes) {
			var element = new jSVGElement(elementName);
			setAttributes(element, attributes, defaultAttributes);
			
			if (parent)
				parent.appendChild(element);
				
			return element;
		}
	
		function setAttributes(element, attributes, defaultAttributes) {
			attributes = mergeOptions(defaultAttributes, attributes);
			
			element.attributes(attributes);
		}
		
		function mergeOptions(defaultOptions, options) {
			var mergedOptions = cloneObject(defaultOptions);

			if (options) {
				for (var option in options)
					mergedOptions[option] = options[option];
			}
			
			return mergedOptions;
		}
		
		function cloneObject(obj) {
		    // Not valid for copying objects that contain methods
		    return JSON.parse(JSON.stringify(obj));
		}
		
		this.render = function() {
			var element = document.createElementNS(namespace, myTag);
		
			for (var attribute in myAttributes) {
				element.setAttributeNS(null, attribute, myAttributes[attribute]);	
			}
			
			element.setAttributeNS(null, "style", myStyle);
			
			for (var i = 0; i < myChildNodes.length; i++)
				element.appendChild(myChildNodes[i].render());
			
			if (myValue) {
				var textNode = document.createTextNode(myValue);
				element.appendChild(textNode);
			}
			
			return element;
		}
		
		this.toString = function() {
			var attributes = "";
		
			for (var attribute in myAttributes) {
				attributes += String.format(' {0}="{1}"', attribute, myAttributes[attribute]);
			}
			
			var childNodes = "";
			
			for (var i = 0; i < myChildNodes.length; i++)
				childNodes += myChildNodes[i].toString();
		
			return String.format('<{0} xmlns="{1}" {2} style="{3}">{4}{5}</{0}>', 
					myTag, namespace, attributes, myStyle, childNodes, myValue ? myValue : "");
		}
	}
	
	return new jSVGElement();
	
})();