var express = require('express');
var router = express.Router();
var Database = require('../public/javascripts/database.js');
var database = new Database();

var target_global = 'user';

router.get('/remove', function (req, res) {
    var user = { id: req.query.id, tableName : target_global, request : 'remove'};
    database.remove(user, res);
});

router.get('/update', function (req, res) {
    var user = { id: req.query.id, tableName : target_global, request : 'update', condition : 'username = ?, password = ? where id = ?', values : [req.query.username, req.query.password]};
    database.update(user, res);
});

router.get('/login', function (req, res) {
    var user = { tableName : target_global, request : 'login', condition : 'username = ? and password = ?', values : [req.query.username, req.query.password]};
    database.query(user, res);
});

router.get('/register', function (req, res) {
    var user = { tableName : target_global, request : 'register', condition : '(username, password, id) values (?, ?, ?)', values : [req.query.username, req.query.password]};
    database.insert(user, res);
});

database.db.run("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY NOT NULL, username TEXT, password TEXT)", function (error) {
        if (error) {
            console.log('create user table failed: ' + error.toString());
        }
});

module.exports = router;
