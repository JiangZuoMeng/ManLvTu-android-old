var sqlite3 = require('sqlite3').verbose();
var EventEmitter = require('events').EventEmitter;
var Responser = require('./responser.js');
var responser = new Responser();

function response(error, row, who, res) {
	var result;
	if (error) {
		console.log('database failed: ' + error.toString());
		result = 'server failed';
	} else if (!row) {
		result = 'failed';
	} else {
		result = 'success';
	}
	responser.response(res, who.request, who.tableName, result, row);
}

function Database() {
	this.query = function(who, res) {
		var eventEmitter = new EventEmitter();
		eventEmitter.on('finished', response);

		Database.prototype.db.get('select * from ' + who.tableName + ' where ' + who.condition, who.values, function (error, row) {
			eventEmitter.emit('finished', error, row, who, res);
		});
	}

	this.insert = function(who, res) {
		var eventEmitter = new EventEmitter();
		eventEmitter.on('finished', response);

		eventEmitter.on('reget', function (id) {
			Database.prototype.db.get('select * from ' + who.tableName + ' where id = ?', [id], function (error, row) {
				eventEmitter.emit('finished', error, row, who, res);
			});
		});

		eventEmitter.on('updateCounter', function (count) {
			Database.prototype.db.run('update counter set count = ? where name = ?', [count + 1, who.tableName], function (error) {
				if (error) {
					eventEmitter.emit('finished', error, null, who, res);
					return;
				}
				eventEmitter.emit('reget', count);
			});
		});

		eventEmitter.on('insert', function (count) {
			Database.prototype.db.run('insert into ' + who.tableName + ' ' + who.condition, who.values.concat([count]), function (error) {
				if (error) {
					eventEmitter.emit('finished', error, null, who, res);
					return;
				}
				eventEmitter.emit('updateCounter', count);
			});
		});

		Database.prototype.db.get('select count from counter where name = ?', [who.tableName], function (error, row) {
			if (error) {
				eventEmitter.emit('finished', error, null, who, res);
				return;
			}
			eventEmitter.emit('insert', row.count);
		});
	}

	this.update = function(who, res) {
		var eventEmitter = new EventEmitter();
		eventEmitter.on('finished', response);

		eventEmitter.on('reget', function (id) {
			Database.prototype.db.get('select * from ' + who.tableName + ' where id = ?', [id], function (error, row) {
				eventEmitter.emit('finished', error, row, who, res);
			});
		});

		Database.prototype.db.run('update ' + who.tableName + ' set ' + who.condition, who.values.concat([who.id]), function (error) {
			if (error) {
				eventEmitter.emit('finished', error, null, who, res);
				return;
			}
			eventEmitter.emit('reget', who.id);
		});
	}

	this.remove = function(who, res) {
		var eventEmitter = new EventEmitter();
		eventEmitter.on('finished', response);

		eventEmitter.on('delete', function (row) {
			Database.prototype.db.run('delete from ' + who.tableName + ' where id = ?', [who.id], function(error) {
				eventEmitter.emit('finished', error, row, who, res);
			});
		});
		Database.prototype.db.get('select * from ' + who.tableName + ' where id = ?', [who.id], function (error, row) {
			if (error) {
				eventEmitter.emit('finished', error, null, who, res);
				return;
			}
			eventEmitter.emit('delete', row);
		});
	}
}

// initialize database
function initCounter(database, name) {
	database.run('insert or ignore into counter (name, count) values (?, ?)', [name, 1], function (error) {
	    if (error) {
	        console.log('initialize ' + name + ' counter failed: ' + error.toString());
	    }
	});
}
function createCounterTable(database) {
	database.run("CREATE TABLE IF NOT EXISTS counter (name TEXT PRIMARY KEY NOT NULL, count INTEGER)", function (error) {
		if (error) {
			console.log('create counter table failed: ' + error.toString());
		}
	});
}
Database.prototype.db = new sqlite3.Database('data.sqlite3', function (error) {
	if (error) {
		console.error('Open database failed: ' + error.toString());
		return;
	}
	console.log('Open database success, initializing...');
	var database = Database.prototype.db;
	database.serialize(function () {
		createCounterTable(database);
		initCounter(database, 'user');
		initCounter(database, 'travel');
		initCounter(database, 'travelItem');
		initCounter(database, 'comment');
	});
});

module.exports = Database;
