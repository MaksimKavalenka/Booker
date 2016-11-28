'use strict';
app.run(function($cookies, $rootScope, $state, STATE, UserFactory, FlashService) {

	$rootScope.$state = $state;

	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

	UserFactory.getUser(function(response) {
		if (response.success) {
			if (response.data !== null) {
				$rootScope.user = {id: response.data.id};
			} else {
				$state.go(STATE.LOGIN);
			}
		}
	});

});