'use strict';
app.controller('BookStatusController', function($state, STATE, BookStatusFactory, FlashService) {

	var self = this;

	self.clear = function() {
		deleteUnsuccessfulBookStatuses();
	};

	function init() {
		switch ($state.current.name) {
			case STATE.SUCCESSFUL_UPLOADS:
				getSuccessfulBookStatuses();
				break;
			case STATE.UNSUCCESSFUL_UPLOADS:
				self.unsuccessful = true;
				getUnsuccessfulBookStatuses();
				break;
		}
	}

	function deleteUnsuccessfulBookStatuses() {
		BookStatusFactory.deleteUnsuccessfulBookStatuses(function(response) {
			if (response.success) {
				self.uploads = undefined;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getSuccessfulBookStatuses() {
		BookStatusFactory.getSuccessfulBookStatuses(function(response) {
			if (response.success) {
				self.uploads = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUnsuccessfulBookStatuses() {
		BookStatusFactory.getUnsuccessfulBookStatuses(function(response) {
			if (response.success) {
				self.uploads = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init();

});