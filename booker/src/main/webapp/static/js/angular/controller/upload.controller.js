'use strict';
app.controller('UploadController', function($scope, MESSAGE, UploadFactory, FlashService) {

	var self = this;

	self.uploadBooks = function() {
		self.dataLoading = true;
		UploadFactory.uploadFile($scope.bookFile, function(response) {
			if (response.success) {
				FlashService.success(MESSAGE.FILE_UPLOADING_SUCCESS);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

});