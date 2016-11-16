'use strict';
app.service('FlashService', function($rootScope) {

	function clearFlashMessage(delay) {
		var flash = $rootScope.flash;
		if (flash) {
			if (!flash.keepAfterLocationChange) {
				setTimeout(function() {
					$rootScope.$apply(function() {
						delete $rootScope.flash;
					});
				}, delay);
			} else {
				flash.keepAfterLocationChange = false;
			}
		}
	}

	function success(message) {
		$rootScope.flash = {
			message: message,
			type: 'success',
			keepAfterLocationChange: false
		};
		this.clearFlashMessage(3000);
	}

	function error(message) {
		$rootScope.flash = {
			message: message,
			type: 'error',
			keepAfterLocationChange: false
		};
		this.clearFlashMessage(3000);
	}

	return {
		clearFlashMessage: clearFlashMessage,
		success: success,
		error: error
	};

});