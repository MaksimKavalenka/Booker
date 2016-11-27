'use strict';
app.controller('UploadController', function($scope, UploadFactory) {

	var self = this;

	self.uploadBooks = function() {
		self.filesCount = $scope.bookFiles.length;
		self.filesUploaded = 0;
		self.successfulUploads = 0;
		self.unsuccessfulUploads = 0;
		self.dataLoading = true;

		for (var i = 0; i < self.filesCount; i++) {
			uploadFile($scope.bookFiles[i].file);
		}
	};

	function uploadFile(file) {
		UploadFactory.uploadFile(file, function(response) {
			if (response.success) {
				++self.successfulUploads;
			} else {
				++self.unsuccessfulUploads;
			}
		});
		++self.filesUploaded;

		if (self.filesUploaded === self.filesCount) {
			self.dataLoading = false;
		}
	}

});