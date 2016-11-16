'use strict';
app.service('ValidatorService', function(MESSAGE) {

	function allNotEmpty() {
		for (var i = 1; i < arguments.length; i++) {
			if (!arguments[i]) {
				var response = {success: false, message: MESSAGE.VALIDATION_ERROR};
				arguments[0](response);
				return false;
			}
		}
		return true;
	}

	return {
		allNotEmpty: allNotEmpty
	};

});