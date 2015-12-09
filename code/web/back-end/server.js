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

app.get('/', function(req, res) {
    res.render('index.jade');
});

app.get('/login', function(req, res) {
    res.end('login');
});
app.get('/register', function(req, res) {
    res.end('register');
});

var server = app.listen(3000, '192.168.150.1', function() {
    console.log('server listening on %s %s', server.address().address, server.address().port);
});
