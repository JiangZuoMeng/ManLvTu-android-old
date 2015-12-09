
function Travel() {
    var database;
    this.initRoute = function(app) {
        var prefix = '/travel/';
        app.get(prefix + 'query', function (req, res) {
            var result = { request : 'query', target : 'travel' };
            if (!req.query.id) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.get('select id, userId, name from travel where id = ?', [req.query.id], function (error, row) {
                    if (error) {
                        result.result = 'server failed';
                    } else {
                        if (row) {
                            result.result = 'success';
                            result.data = { id : row.id, userId : row.userId, name : row.name };
                        } else {
                            result.result = 'not exist';
                        }
                    }
                    res.end(JSON.stringify(result));
            })
        });
        
        app.get(prefix + 'add', function (req, res) {
            var result = { request : 'add', target : 'travel' };
            if (!(req.query.userId && req.query.name)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('insert into travel (userId, name) values (?, ?)', [req.query.userId, req.query.name], function (error) {
                if (error) {
                    result.result = 'server failed';
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            });
        });
        
        app.get(prefix + 'remove', function (req, res) {
            var result = { request : 'remove', target : 'travel' };
            if (!req.query.id) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('delete from travel where id = ?', [req.query.id], function (error) {
                if (error) {
                    result.result = 'server failed';
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            })
        });
        
        app.get(prefix + 'update', function (req, res) {
            var result = { request : 'update', target : 'travel' };
            if (!(req.query.id && req.query.userId && req.query.name)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('update travel set userId = ?, name = ? where id = ?', [req.query.userId, req.query.name, req.query.id], function (error) {
                if (error) {
                    result.result = 'server failed: ' + error.toString();
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            });
        });
    }
    
    this.initDataBase = function (database_para) {
        database = database_para;
        database.run("CREATE TABLE IF NOT EXISTS travel "+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, name TEXT)");
    }
}

module.exports = Travel;
