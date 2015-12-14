var express = require('express');
var router = express.Router();
var Database = require('../public/javascripts/database.js');
var database = new Database();

var target_global = 'travelItem';

router.get('/query', function (req, res) {
    var target = { tableName : target_global, request : 'query', condition : 'id = ?', values : [req.query.id ]};
    database.query(target, res);
});

router.get('/queryAll', function (req, res) {
    var target = { tableName : target_global, request : 'queryAll', condition : 'travelId = ?', values : [req.query.travelId ]};
    database.queryAll(target, res);
});

router.get('/queryNearby', function (req, res) {
    var target = { tableName : target_global, request : 'queryNearby',
    condition : '(locationLat >= ? and locationLat <= ?) and (locationLng >= ? and locationLng <= ?)',
    values : [req.query.locationLatLowerBound, req.query.locationLatUpperBound, req.query.locationLngLowerBound, req.query.locationLngUpperBound ]};
    database.queryAll(target, res);
});

router.get('/remove', function (req, res) {
    var target = { id: req.query.id, tableName : target_global, request : 'remove'};
    database.remove(target, res);
});

router.get('/update', function (req, res) {
    var target = { id: req.query.id, tableName : target_global, request : 'update',
        condition : 'travelId = ?, label = ?, time = ?, locationLat = ?, locationLng = ?, like = ?, text = ?, media = ? where id = ?',
        values : [req.query.travelId, req.query.label, req.query.time, req.query.locationLat, req.query.locationLng, req.query.like, req.query.text, req.query.media ]};
    database.update(target, res);
});

router.get('/add', function (req, res) {
    var target = { tableName : target_global, request : 'add',
        condition : '(travelId, label, time, locationLat, locationLng, like, text, media, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)',
        values : [req.query.travelId, req.query.label, req.query.time, req.query.locationLat, req.query.locationLng, req.query.like, req.query.text, req.query.media ]};
    database.insert(target, res);
});

database.db.run("CREATE TABLE IF NOT EXISTS " + target_global +
                "(id INTEGER PRIMARY KEY NOT NULL, travelId INTEGER," +
                "label TEXT, time TEXT, locationLat REAL, locationLng REAL, like INTEGER," +
                "text TEXT, media TEXT)", function (error) {
        if (error) {
            console.log('create '+ target_global +' table failed: ' + error.toString());
        }
});

module.exports = router;
