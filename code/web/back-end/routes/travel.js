var express = require('express');
var router = express.Router();
var Database = require('../public/javascripts/database.js');
var database = new Database();

var target_global = 'travel';

router.get('/query', function (req, res) {
    var target = { tableName : target_global, request : 'query', condition : 'id = ?', values : [req.query.id ]};
    database.query(target, res);
});

router.get('/queryAll', function (req, res) {
    var target = { tableName : target_global, request : 'queryAll', condition : 'userId = ?', values : [req.query.userId ]};
    database.queryAll(target, res);
});

router.get('/remove', function (req, res) {
    var target = { id: req.query.id, tableName : target_global, request : 'remove'};
    database.remove(target, res);
});

router.get('/update', function (req, res) {
    var target = { id: req.query.id, tableName : target_global, request : 'update', condition : 'userId = ?, name = ? where id = ?', values : [req.query.userId, req.query.name]};
    database.update(target, res);
});

router.get('/add', function (req, res) {
    var target = { tableName : target_global, request : 'add', condition : '(userId, name, id) values (?, ?, ?)', values : [req.query.userId, req.query.name]};
    database.insert(target, res);
});

database.db.run("CREATE TABLE IF NOT EXISTS " + target_global +
                "(id INTEGER PRIMARY KEY NOT NULL, userId INTEGER, name TEXT)", function (error) {
        if (error) {
            console.log('create '+ target_global +' table failed: ' + error.toString());
        }
});

module.exports = router;
