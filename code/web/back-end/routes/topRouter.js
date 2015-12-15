var express = require('express');
var router = express.Router();

var formidable = require('formidable');
var fs = require('fs');

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

router.post('/upload', function(req, res) {
    var form = new formidable.IncomingForm();
    form.uploadDir = './data/uploaded/tmp';

    form.parse(req, function(error, fields, files) {
      console.log(files);
      return;
      var result = { request: 'upload', target: 'file'};
      if (error) {
          result.result = 'error: ' + error.toString();
          console.log(error.toString());
          res.json(result);
          return;
      }
      
      var targetFilePath = './data/uploaded/finished/' + files.file.name;
      fs.rename(files.file.path, targetFilePath, function (error) {
          if (error) {
              result.result = 'error: ' + error.toString();
              console.log(error.toString());
              res.json(result);
              return;
          }
          result.result = 'success';
          result.data = files.file.name;
          res.json(result);
      });
    });

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
