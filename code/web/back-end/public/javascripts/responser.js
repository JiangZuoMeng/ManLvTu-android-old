function Responser () {
	this.response = function (res, request_para, target_para, result_para, data_para) {
		var result = {request : request_para, target : target_para, result : result_para, data : data_para};
		res.json(result);
	}
}

module.exports = Responser;
