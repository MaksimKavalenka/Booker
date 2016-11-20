'use strict';
app.controller('UploadController', function($scope, MESSAGE, UploadFactory, FlashService) {

	var self = this;
	self.progress = 0;

	self.uploadBooks = function() {
		self.dataLoading = true;
		UploadFactory.uploadFile($scope.bookFile, function(response) {
			var refreshIntervalId = setInterval(getProgress(), 1);
			if (response.success) {
				self.progress = 100;
				FlashService.success(MESSAGE.FILE_UPLOADING_SUCCESS);
			} else {
				FlashService.error(response.message);
			}
			clearInterval(refreshIntervalId);
			self.dataLoading = false;
		});
	};

	function getProgress() {
		UploadFactory.getProgress(function(response) {
			if (response.success) {
				self.progress = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

});