var express = require('express');
var router = express.Router();

router.get('/user', function(req, res) {
    res.render('user.jade');
});

router.get('/travel', function(req, res) {
    res.render('travel.jade');
});

router.get('/travelItem', function(req, res) {
    res.render('travelItem.jade');
});

router.get('/comment', function(req, res) {
    res.render('comment.jade');
});

var user = require('./user.js');
router.use('/user', user);
var travel = require('./travel.js');
router.use('/travel', travel);
var travelItem = require('./travelItem.js');
router.use('/travelItem', travelItem);
var comment = require('./comment.js');
router.use('/comment', comment);

module.exports = router;
