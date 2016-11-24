'use strict';
app.controller('UsersController', function($rootScope, $state, STATE, UsersFactory, FlashService) {

	var self = this;
	var currentLogin = "";

	self.login = function() {
		self.dataLoading = true;
		UsersFactory.authentication(self.user.login, self.user.password, function(response) {
			if (response.success) {
				$rootScope.user = {id: response.data.id};
				$state.go(STATE.BOOKS);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.logout = function() {
		$rootScope.user = null;
		UsersFactory.logout();
		switch ($state.current.name) {
			case STATE.UPLOAD_BOOKS:
				$state.go(STATE.BOOKS);
				break;
			default:
				$state.reload();
		}
	};

	self.register = function() {
		self.dataLoading = true;
		UsersFactory.createUser(self.user.login, self.user.password, self.user.confirmPassword, function(response) {
			if (response.success) {
				self.login();
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.validateLogin = function() {
		var id = 'login';
		var flag = (currentLogin === document.getElementById(id).value);
		if (flag) {
			document.getElementById(id).className = 'ng-valid';
		}
		return flag;
	};

});