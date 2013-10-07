var http = require("http");
var fs = require('fs');

var svg2png = require('svg2png');

var jSVG = require("./js/jSVG.js");
var jGraf = require("./js/jGraf.js");

function onRequest(request, response) {
  	generatePNG(response);
}

http.createServer(onRequest).listen(8888);

function generatePNG(response) {
	var svg = jGraf.jGraf.lineChart().toString();
	
	var fileId = guid();
	var SVG = "tmp/" + fileId + ".svg";
	var PNG = "tmp/" + fileId + ".png";

	fs.writeFileSync(SVG, svg); 
	console.log("File SVG generated");	
	
	svg2png(SVG, PNG, function (err) {
	    if(err) {
	        console.log(err);
	        
	        deleteFile(SVG);
	        
	        response.writeHead(200, {"Content-Type": "image/png"});
	        res.end("There was an error.");
	    } else {
	        console.log("The PNG file was saved");
	        
	        var content = fs.readFileSync(PNG);
	        deleteFile(SVG);
	        deleteFile(PNG);
	        
	        response.writeHead(200, {"Content-Type": "image/png"}); 
	        response.write(content, "binary");
	        response.end();
	    }
	});
};

function deleteFile(file) {
	fs.unlink(file, function (err) {
	  if (err) throw err;
	  console.log("Successfully deleted: " + file);
	});
}

function s4() {
  return Math.floor((1 + Math.random()) * 0x10000)
             .toString(16)
             .substring(1);
};

function guid() {
  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
         s4() + '-' + s4() + s4() + s4();
};