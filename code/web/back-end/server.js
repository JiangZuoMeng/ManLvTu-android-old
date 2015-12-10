var bodyParser = require('body-parser');
var express = require('express');
var app = express();

// load local modules
var Modals = require('./routes/modals.js');
var modals = new Modals();
modals.initRoute(app)
var DataBase = require('./public/javascripts/database.js');
var database = new DataBase(modals);

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.get('/user', function(req, res) {
    res.render('user.jade');
});

app.get('/travel', function(req, res) {
    res.render('travel.jade');
});

app.get('/travelItem', function(req, res) {
    res.render('travelItem.jade');
});

app.get('/comment', function(req, res) {
    res.render('comment.jade');
});

var server = app.listen(3000, '127.0.0.1', function() {
    console.log('server listening on %s %s', server.address().address, server.address().port);
});
