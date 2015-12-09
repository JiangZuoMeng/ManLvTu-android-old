var User = require('./user.js');
var Travel = require('./travel.js');
var TravelItem = require('./travelItem.js');
var Comment = require('./comment.js');

function Modal() {
	// load local modules
	user = new User();
	travel = new Travel();
	travelItem = new TravelItem();
	comment = new Comment();
	
	this.initRoute = function(app) {
		user.initRoute(app);
		travel.initRoute(app);
		travelItem.initRoute(app);
		comment.initRoute(app);
	}
	
	this.initDatabase = function (database) {
		user.initDataBase(database);
		travel.initDataBase(database);
		travelItem.initDataBase(database);
		comment.initDataBase(database);
	}
}

module.exports = Modal;
