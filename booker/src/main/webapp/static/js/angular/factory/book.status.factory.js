'use strict';
app.factory('BookStatusFactory', function($http, MESSAGE, REST, ValidatorService) {

	function createBookStatus(fileName, status, message, callback) {
		if (!ValidatorService.allNotEmpty(callback, fileName, status)) {
			return;
		}

		var bookStatus = {
			fileName: fileName,
			status: status,
			message: message
		};

		$http.post(REST.BOOK_STATUS + '/create', bookStatus)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_BOOK_STATUS_ERROR};
			callback(response);
		});
	}

	function deleteUnsuccessfulBookStatuses(callback) {
		$http.delete(REST.BOOK_STATUS + '/unsuccessful')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.DELETING_BOOK_STATUS_ERROR};
			callback(response);
		});
	}

	function getSuccessfulBookStatuses(callback) {
		$http.get(REST.BOOK_STATUS + '/successful')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_BOOK_STATUS_ERROR};
			callback(response);
		});
	}

	function getUnsuccessfulBookStatuses(callback) {
		$http.get(REST.BOOK_STATUS + '/unsuccessful')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_BOOK_STATUS_ERROR};
			callback(response);
		});
	}

	return {
		createBookStatus: createBookStatus,
		deleteUnsuccessfulBookStatuses: deleteUnsuccessfulBookStatuses,
		getSuccessfulBookStatuses: getSuccessfulBookStatuses,
		getUnsuccessfulBookStatuses: getUnsuccessfulBookStatuses
	};

});