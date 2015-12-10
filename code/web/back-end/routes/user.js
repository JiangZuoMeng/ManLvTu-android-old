/*var express = require('express');
var router = express.Router();
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

module.exports = router;
*/



function User() {
    var database;
    this.initRoute = function (app) {
        var prefix = '/user/';
        app.get(prefix + 'query', function (req, res) {
            var result = { request : 'query', target : 'user' };
            if (!req.query.username) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.get('select id, username, password from user where username = ?', [req.query.username], function (error, row) {
                    if (error) {
                        result.result = 'server failed';
                    } else {
                        if (row) {
                            result.result = 'success';
                            result.data = { id : row.id, username : row.username, password : row.password };
                        } else {
                            result.result = 'not exist';
                        }
                    }
                    res.end(JSON.stringify(result));
            })
        });
        
        app.get(prefix + 'remove', function (req, res) {
            var result = { request : 'remove', target : 'user' };
            if (!req.query.id) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('delete from user where id = ?', [req.query.id], function (error) {
                if (error) {
                    result.result = 'server failed';
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            })
        });
        
        app.get(prefix + 'update', function (req, res) {
            var result = { request : 'update', target : 'user' };
            if (!(req.query.id && req.query.username && req.query.password)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('update user set username = ?, password = ? where id = ?', [req.query.username, req.query.password, req.query.id], function (error) {
                if (error) {
                    result.result = 'server failed';
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            });
        });

        app.get(prefix + 'login', function (req, res) {
            var result = { request : 'login', target : 'user' };
            if (!(req.query.username && req.query.password)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.get('select id, username, password from user where username = ? and password = ?', [req.query.username, req.query.password], function (error, row) {
                if (error) {
                    result.result = 'server failed';
                } else if (row) {
                    result.result = 'success';
                    result.data = { id : row.id, username : row.username };
                } else {
                    result.result = 'not exist';
                }
                res.end(JSON.stringify(result));
            });
        });

        app.get(prefix + 'register', function (req, res) {
            var result = { request : 'register', target : 'user' };
            if (!(req.query.username && req.query.password)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }

            // get user count
            var count;
            database.get('select count from counter where name = ?', ['user'], function (error, row) {
                if (error) {
                    result.result = 'server failed';
                    console.log('database error: ' + error.toString());
                    res.end(JSON.stringify(result));
                    return;
                } else {
                    count = row.count;
                    console.log('user count: ' + row.toString());
                }
            });

            // insert new user into database
            database.run('insert into user (id, username, password) values (?, ?)', [count, req.query.username, req.query.password], function (error) {
                if (error) {
                    result.result = 'server failed';
                    console.log('database error: ' + error.toString());
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            });

            // update user count
            count++;
            database.run('update counter set count = ? where name = ?', [count, 'user'], function (error) {
                if (error) {
                    console.log('update user count failed: ' + error.toString());
                }
            });
        });
    }
    
    this.initDataBase = function (database_para) {
        database = database_para;
        database.run("CREATE TABLE IF NOT EXISTS user "+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
    }
}

module.exports = User;
