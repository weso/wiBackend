var mongodb = require('mongodb');
var settings = require('../../settings/application.js');
 
var mongodbHost = settings.mongodbHost;
var mongodbPort = settings.mongodbPort; 
var mongodbDataBase = settings.mongodbDataBase;
var mongodbCollection = "observations";
 
exports.DataBase = DataBase; 
 
function DataBase() {
	var host = mongodbHost;
	var port = mongodbPort;
	var database = mongodbDataBase;
	var collectionName = mongodbCollection;

	var server = new mongodb.Server(host, port, {});
	var database = new mongodb.Db(database, server, {safe: true});

	this.find = function(where, sort, callback) {
		database.open(function (error, client) {
			if (error) 
				throw error;

			var collection = new mongodb.Collection(client, collectionName);

			collection.find(where).sort(sort).toArray(function(err, data) {
				if (err)
					throw err;
					
				callback.call(this, data);
				
				database.close();	
			});
		});
	}
} 
