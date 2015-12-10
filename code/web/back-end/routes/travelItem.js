
function TravelItem () {
    this.initRoute = function(app) {
        var prefix = '/travelItem/';
        app.get(prefix + 'query', function (req, res) {
            var result = { request : 'query', target : 'travelItem' };
            if (!req.query.id) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.get('select * from travelItem where id = ?', [req.query.id], function (error, row) {
                    if (error) {
                        result.result = 'server failed';
                    } else {
                        if (row) {
                            result.result = 'success';
                            result.data = { id : row.id, travelId : row.travelId, label : row.label,
                                locationLat:  row.locationLat, locationLng: row.locationLng, like: row.like,
                                text: row.text, media: row.media };
                        } else {
                            result.result = 'not exist';
                        }
                    }
                    res.end(JSON.stringify(result));
            })
        });
        
        app.get(prefix + 'add', function (req, res) {
            var result = { request : 'add', target : 'travelItem' };
            if (!(req.query.travelId)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('insert into travelItem (travelId, label, time, locationLat, locationLng, like, text, media) values (?, ?, ?, ?, ?, ?, ?, ?)',
            [req.query.travelId, req.query.label, req.query.time, req.query.locationLat,
            req.query.locationLng, req.query.like, req.query.text, req.query.media], function (error) {
                if (error) {
                    result.result = 'server failed';
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            });
        });
        
        app.get(prefix + 'remove', function (req, res) {
            var result = { request : 'remove', target : 'travelItem' };
            if (!req.query.id) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('delete from travelItem where id = ?', [req.query.id], function (error) {
                if (error) {
                    result.result = 'server failed';
                } else {
                    result.result = 'success';
                }
                res.end(JSON.stringify(result));
            })
        });
        
        app.get(prefix + 'update', function (req, res) {
            var result = { request : 'update', target : 'travelItem' };
            if (!(req.query.id)) {
                result.result = 'invalid request';
                res.end(JSON.stringify(result));
                return;
            }
            database.run('update travelItem set travelId = ?, label = ?, time = ?, locationLat = ?,' + 
                'locationLng = ?, like = ?, text = ?, media = ? where id = ?',
                [req.query.travelId, req.query.label, req.query.time, req.query.locationLat,
                req.query.locationLng, req.query.like, req.query.text, req.query.media, req.query.id], function (error) {
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
        database.run("CREATE TABLE IF NOT EXISTS travelItem " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, travelId INTEGER," +
                "label TEXT, time TEXT, locationLat REAL, locationLng REAL, like INTEGER," +
                "text TEXT, media TEXT)");
    }
}

module.exports = TravelItem;
