var sqlite3 = require('sqlite3').verbose();

function DataBase(modals) {
	var database = new sqlite3.Database('data.sqlite3', function (error) {
				if (error) {
					console.error('database opened failed with error: ' + error);
					return;
				}
			
				console.log("database open successfully, initializing table...");
				modals.initDatabase(database);
				console.log("initialized database.");
			});
}

module.exports = DataBase;