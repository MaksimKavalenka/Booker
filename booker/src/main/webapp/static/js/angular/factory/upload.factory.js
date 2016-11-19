'use strict';
app.service('UploadFactory', function($http, MESSAGE, REST) {

	function uploadFile(file, callback) {
		var formData = new FormData();
		formData.append('file', file);
		$http.post(REST.UPLOAD, formData, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
		.success(function(response) {
			response = {success: true};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: response.message};
			callback(response);
		});
	}

	function getProgress(callback) {
		$http.post(REST.UPLOAD + '/progress')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_PROGRESS_ERROR};
			callback(response);
		});
	}

	return {
		uploadFile: uploadFile,
		getProgress: getProgress
	};

});