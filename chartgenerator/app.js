
/**
 * Module dependencies.
 */

var express = require('express');
var routes = require('./routes');
var help = require('./routes/help');
var http = require('http');
var path = require('path');

var app = express();

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', __dirname + '/views');
app.set('view engine', 'jade');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// Error handling
app.use(function(err, req, res, next){
  console.error(err.stack);
  res.send(500, 'Something broke!');
});

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

app.get('/charts/:chart', routes.index);
app.get('/charts/:chart/:first', routes.index);
app.get('/charts/:chart/:first/:second', routes.index);
app.get('/charts/:chart/:first/:second/:third', routes.index);

app.get('/help', help.index);
app.get('*', function(req, res){
  res.redirect('/help');
});

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});

