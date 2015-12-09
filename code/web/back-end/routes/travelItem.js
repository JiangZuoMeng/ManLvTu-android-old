
function TravelItem () {
    this.initRoute = function(app) {
        var prefix = '/travelItem/';
        app.get(prefix + 'query', function (req, res) {
            res.end('query travelItem');
        });
        
        app.get(prefix + 'add', function (req, res) {
            res.end('add travelItem');
        });
        
        app.get(prefix + 'remove', function (req, res) {
            res.end('remove travelItem');
        });
        
        app.get(prefix + 'update', function (req, res) {
            res.end('update travelItem');
        });
    }
    
    this.initDataBase = function (database) {
        database.run("CREATE TABLE IF NOT EXISTS travelItem " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, travelId INTEGER," +
                "label TEXT, time TEXT, locationLat REAL, locationLng REAL, like INTEGER," +
                "text TEXT, media TEXT)");
    }
}

module.exports = TravelItem;
