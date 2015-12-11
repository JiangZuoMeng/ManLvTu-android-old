var express = require('express');
var app = express();

// ManLvTu modules
// initialize database
require('./public/javascripts/database.js');
// initialize routers
var topRouter = require('./routes/topRouter.js');
app.use('/', topRouter);

var server = app.listen(3000, '127.0.0.1', function() {
    console.log('server listening on %s %s', server.address().address, server.address().port);
});
