'use strict';
app.config(function($stateProvider, $urlRouterProvider, CONTROLLER, PATH, STATE, TITLE, URL) {

	var footer = {
		templateUrl: PATH.FOOTER
	};

	var header = {
		templateUrl: PATH.HEADER
	};

	var welcomeHeader = {
		templateUrl: PATH.WELCOME_HEADER
	};

	var search = {
		templateUrl: PATH.SEARCH_TOOL
	};

	var pagination = {
		templateUrl: PATH.PAGINATION_TOOL
	};

	$stateProvider
	.state(STATE.LOGIN, {
		title: TITLE.LOGIN,
		url: URL.LOGIN,
		views: {
			header: welcomeHeader,
			content: {
				controller: CONTROLLER.USERS_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.LOGIN_FORM
			},
			footer: footer
		}
	})

	.state(STATE.REGISTER, {
		title: TITLE.REGISTER,
		url: URL.REGISTER,
		views: {
			header: welcomeHeader,
			content: {
				controller: CONTROLLER.USERS_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.REGISTER_FORM
			},
			footer: footer
		}
	})

	.state(STATE.UPLOAD_BOOKS, {
		title: TITLE.UPLOAD_BOOKS,
		url: URL.UPLOAD_BOOKS,
		views: {
			header: header,
			content: {
				controller: CONTROLLER.UPLOAD_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.UPLOAD_BOOKS_FORM
			},
			footer: footer
		}
	})

	.state(STATE.BOOKS, {
		title: TITLE.BOOKS,
		url: URL.BOOKS,
		params: {
			page: '1'
		},
		views: {
			header: header,
			pagination: pagination,
			content: {
				controller: CONTROLLER.BOOKS_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.BOOKS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.BOOK, {
		title: TITLE.BOOK,
		url: URL.BOOK,
		views: {
			header: header,
			content: {
				controller: CONTROLLER.BOOKS_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.BOOK_CONTENT
			},
			footer: footer
		}
	});

	$urlRouterProvider.otherwise(URL.HOME);

});