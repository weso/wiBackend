var mongodb = require('mongodb');
 
var mongodbHost = "127.0.0.1";
var mongodbPort = 27017; 
var mongodbDataBase = "webindex";
var mongodbCollection = "observations";
 
exports.DataBase = DataBase; 
 
function DataBase() {
	var host = mongodbHost;
	var port = mongodbPort;
	var database = mongodbDataBase;
	var collectionName = mongodbCollection;

	var server = new mongodb.Server(host, port, {});
	var database = new mongodb.Db(database, server, {safe: true});

	this.find = function(where, callback) {
		database.open(function (error, client) {
			if (error) 
				throw error;

			var collection = new mongodb.Collection(client, collectionName);

			collection.find(where).toArray(function(err, data) {
				if (err)
					throw err;
					
				callback.call(this, data);
				
				database.close();	
			});
		});
	}
} 
