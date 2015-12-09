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
            if (!req.query.username) {
                res.end('missing arguement');
                return;
            }
            database.get('select id, username, password from user where username = ?', [req.query.username], function (error, row) {
                    res.write('query user:\n');
                    if (error) {
                        res.write('failed\n');
                    } else {
                        if (row) {
                            res.write('id: ' + row.id + '\n');
                            res.write('username: ' + row.username + '\n');
                            res.write('password: ' + row.password);
                        } else {
                            res.write('no such user');
                        }
                    }
                    res.end();
            })
        });
        
        app.get(prefix + 'add', function (req, res) {
            if (!(req.query.username && req.query.password)) {
                res.end('missing arguement');
                return;
            }
            database.run('insert into user (username, password) values (?, ?)', [req.query.username, req.query.password], function (error) {
                res.setHeader('Content-Type', 'text/plain');
                if (error) {
                    res.write('add user failed:\n');
                } else {
                    res.write('add user successfully:\n');
                }
                res.end();
            });
        });
        
        app.get(prefix + 'remove', function (req, res) {
            if (!req.query.id) {
                res.end('missing arguement');
                return;
            }
            database.run('delete from user where id = ?', [req.query.id], function (error) {
                res.write('remove user: ');
                if (error) {
                    res.end('failed');
                } else {
                    res.end('successfully');
                }
            })
        });
        
        app.get(prefix + 'update', function (req, res) {
            if (!(req.query.id && req.query.username && req.query.password)) {
                res.end('missing arguement');
                return;
            }
            database.run('update user set username = ?, password = ? where id = ?', [req.query.username, req.query.password, req.query.id], function (error) {
                res.write('update user: ');
                if (error) {
                    res.end('failed');
                } else {
                    res.end('successfully');
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
