'use strict';
app.service('UploadFactory', function($http, MESSAGE, REST) {

	function uploadFile(file, callback) {
		var formData = new FormData();
		formData.append('file', file);
		$http.post(REST.UPLOAD + '/epub', formData, {
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

	return {
		uploadFile: uploadFile
	};

});