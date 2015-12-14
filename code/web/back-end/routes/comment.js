var express = require('express');
var router = express.Router();
var Database = require('../public/javascripts/database.js');
var database = new Database();

var target_global = 'comment';

router.get('/query', function (req, res) {
    var user = { tableName : target_global, request : 'query', condition : 'id = ?', values : [req.query.id ]};
    database.query(user, res);
});

router.get('/queryAll', function (req, res) {
    var user = { tableName : target_global, request : 'queryAll', condition : 'travelItemId = ?', values : [req.query.travelItemId ]};
    database.queryAll(user, res);
});

router.get('/remove', function (req, res) {
    var user = { id: req.query.id, tableName : target_global, request : 'remove'};
    database.remove(user, res);
});

router.get('/update', function (req, res) {
    var user = { id: req.query.id, tableName : target_global, request : 'update',
        condition : 'userId = ?, travelItemId = ?, text = ?, time = ? where id = ?',
        values : [req.query.userId, req.query.travelItemId, req.query.text, req.query.time ]};
    database.update(user, res);
});

router.get('/add', function (req, res) {
    var user = { tableName : target_global, request : 'add',
        condition : '(userId, travelItemId, text, time, id) values (?, ?, ?, ?, ?)',
        values : [req.query.userId, req.query.travelItemId, req.query.text, req.query.time ]};
    database.insert(user, res);
});

database.db.run("CREATE TABLE IF NOT EXISTS " + target_global +
                "(id INTEGER PRIMARY KEY NOT NULL, userId INTEGER, travelItemId INTEGER, " +
                "text TEXT, time TEXT)", function (error) {
        if (error) {
            console.log('create '+ target_global +' table failed: ' + error.toString());
        }
});

module.exports = router;
