var fs = require('fs');
var svg2png = require('svg2png');

var jGraf = require('../src/chart/jGraf.js').jGraf;

exports.generateGraph = function(response) {
	try {
	  	return generatePNG(response);
	}
	catch(e) {
		console.log("Observations file error: " + e);
		return false;
	}
}

function generatePNG(response) {
	var svg = jGraf.lineChart().toString();
	
	var fileId = guid();
	var SVG = "tmp/" + fileId + ".svg";
	var PNG = "tmp/" + fileId + ".png";

	fs.writeFileSync(SVG, svg); 
	console.log("File SVG generated");	
	
	svg2png(SVG, PNG, function (err) {
	    if(err) {
	        console.log(err);
	        
	        deleteFile(SVG);
	        
	        //response.setHeader("Content-Type", "text/html");
	        //response.end("There was an error.");
	        return false;
	    } else {
	        console.log("The PNG file was saved");
	        
	        var content = fs.readFileSync(PNG);
	        deleteFile(SVG);
	        deleteFile(PNG);
	         
	        //response.setHeader("Content-Type", "image/png");
	        //response.write(content, "binary");
	        //response.end();
	        return true;
	    }
	});
};

function deleteFile(file) {
	fs.unlink(file, function (err) {
	  if (err) 
	  	throw err;
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