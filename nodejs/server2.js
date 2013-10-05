var http = require("http");
var fs = require('fs');

var svg2png = require('svg2png');

var jSVG = require("./js/jSVG.js");
var jGraf = require("./js/jGraf.js");

function onRequest(request, response) {
	var content = jGraf.jGraf.lineChart().toString();

  	response.writeHead(200, {"Content-Type": "image/svg+xml"}); 
	response.write(content, "binary");
	response.end();
}

http.createServer(onRequest).listen(8888);