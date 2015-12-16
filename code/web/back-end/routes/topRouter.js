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

var fileFinishedDirectory = './data/uploaded/finished/';
var fileTempDirectory = './data/uploaded/tmp/';

router.post('/upload', function(req, res) {
    var form = new formidable.IncomingForm();
    form.uploadDir = fileTempDirectory;

    form.parse(req, function(error, fields, files) {
      var result = { request: 'upload', target: 'file'};
      if (error) {
          result.result = 'error: ' + error.toString();
          console.log(error.toString());
          res.json(result);
          return;
      }
      
      var targetFilePath = fileFinishedDirectory + files.file.name;
      fs.rename(files.file.path, targetFilePath, function (error) {
          if (error) {
              result.result = 'error: ' + error.toString();
              console.log(error.toString());
              res.json(result);
              return;
          }
          result.result = 'success';
          result.data = files.file.name;
          console.log("Saved file: " + result.data);
          res.json(result);
      });
    });

});

router.get('/download', function(req, res) {
    var filename = req.query.filename;
    var sendOptions = { root: fileFinishedDirectory };
    res.sendFile(filename, sendOptions, function (error) {
      if (error) {
        console.log('send file error: ' + error);
        try {
          res.sendStatus(404);
        } catch (error) {
          console.log("error in send file: " + e.toString());
        }
      }
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
