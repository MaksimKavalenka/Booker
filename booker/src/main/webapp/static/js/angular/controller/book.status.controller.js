'use strict';
app.controller('BookStatusController', function($state, STATE, BookStatusFactory, FlashService) {

	var self = this;

	self.clear = function() {
	};

	function init() {
		switch ($state.current.name) {
			case STATE.SUCCESSFUL_UPLOADS:
				getSuccessfulBookStatuses();
				break;
			case STATE.UNSUCCESSFUL_UPLOADS:
				getUnsuccessfulBookStatuses();
				break;
		}
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