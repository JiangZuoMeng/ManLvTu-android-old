/*var express = require('express');
var router = express.Router();
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

module.exports = router;
*/



function Counter() {
    var database;
    this.initDataBase = function (database_para) {
        database = database_para;
        database.run("CREATE TABLE IF NOT EXISTS counter "+
                "(name TEXT PRIMARY KEY, count INTEGER)", function (error) {
                    if (error) {
                        console.log('create counter table failed: ' + error.toString());
                    }
                });
        database.run('insert or ignore into counter (name, count) values (?, ?)', ['user', 1], function (error) {
            if (error) {
                console.log('initialize user counter failed: ' + error.toString());
            }
        });
        database.run('insert or ignore into counter (name, count) values (?, ?)', ['travel', 1], function (error) {
            if (error) {
                console.log('initialize travel counter failed: ' + error.toString());
            }
        });
        database.run('insert or ignore into counter (name, count) values (?, ?)', ['travelItem', 1], function (error) {
            if (error) {
                console.log('initialize travelItem counter failed: ' + error.toString());
            }
        });
        database.run('insert or ignore into counter (name, count) values (?, ?)', ['comment', 1], function (error) {
            if (error) {
                console.log('initialize comment counter failed: ' + error.toString());
            }
        });
    }
}

module.exports = Counter;
