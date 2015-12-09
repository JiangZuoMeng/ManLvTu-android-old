
function Comment() {
    this.initRoute = function(app) {
        var prefix = '/comment/';
        app.get(prefix + 'query', function (req, res) {
            res.end('query comment');
        });
        
        app.get(prefix + 'add', function (req, res) {
            res.end('add comment');
        });
        
        app.get(prefix + 'remove', function (req, res) {
            res.end('remove comment');
        });
        
        app.get(prefix + 'update', function (req, res) {
            res.end('update comment');
        });
    }
    
    this.initDataBase = function (database) {
        database.run("CREATE TABLE IF NOT EXISTS comment " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER," +
                "text TEXT, time TEXT)");
    }
}

module.exports = Comment;
