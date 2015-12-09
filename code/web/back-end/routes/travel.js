
function Travel() {
    this.initRoute = function(app) {
        var prefix = '/travel/';
        app.get(prefix + 'query', function (req, res) {
            res.end('query travel');
        });
        
        app.get(prefix + 'add', function (req, res) {
            res.end('add travel');
        });
        
        app.get(prefix + 'remove', function (req, res) {
            res.end('remove travel');
        });
        
        app.get(prefix + 'update', function (req, res) {
            res.end('update travel');
        });
    }
    
    this.initDataBase = function (database) {
        database.run("CREATE TABLE IF NOT EXISTS travel "+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, name TEXT)");
    }
}

module.exports = Travel;
