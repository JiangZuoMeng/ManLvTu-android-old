
function Comment() {
    this.initRoute = function(app) {
        var prefix = '/comment/';
        app.get(prefix + 'query', function (req, res) {
            var result = { request : 'query', target : 'comment' };
            if (!req.query.id) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.get('select * from comment where id = ?', [req.query.id], function (error, row) {
                    if (error) {
                        result.result = 'server failed';
                    } else {
                        if (row) {
                            result.result = 'success';
                            result.data = { id : row.id, userId : row.userId, travelItemId : row.travelItemId, text: row.text, time : row.time };
                        } else {
                            result.result = 'not exist';
                        }
                    }
                    res.end(JSON.stringify(result));
            })
        });
        
        app.get(prefix + 'add', function (req, res) {
            var result = { request : 'add', target : 'comment' };
            if (!(req.query.travelItemId && req.query.userId)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('insert into comment (userId, travelItemId, text, time) values (?, ?, ?, ?)',
            [req.query.userId, req.query.travelItemId, req.query.text, req.query.time], function (error) {
                if (error) {
                    result.result = 'server failed';
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            });
        });
        
        app.get(prefix + 'remove', function (req, res) {
            var result = { request : 'remove', target : 'comment' };
            if (!req.query.id) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('delete from comment where id = ?', [req.query.id], function (error) {
                if (error) {
                    result.result = 'server failed';
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            })
        });
        
        app.get(prefix + 'update', function (req, res) {
            var result = { request : 'update', target : 'comment' };
            if (!(req.query.id)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('update comment set userId = ?, travelItemId = ?, text = ?, time = ? where id = ?',
                [req.query.userId, req.query.travelItemId, req.query.text, req.query.time, req.query.id], function (error) {
                if (error) {
                    result.result = 'server failed: ' + error.toString();
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            });
        });
    }
    
    var database;
    this.initDataBase = function (database_para) {
        database = database_para;
        database.run("CREATE TABLE IF NOT EXISTS comment " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, travelItemId INTEGER, " +
                "text TEXT, time TEXT)");
    }
}

module.exports = Comment;
