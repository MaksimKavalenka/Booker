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

	var loginForm = {
		controller: CONTROLLER.USERS_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.LOGIN_FORM
	};

	var uploadBooksForm = {
		controller: CONTROLLER.UPLOAD_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.UPLOAD_BOOKS_FORM
	};

	var registerForm = {
		controller: CONTROLLER.BOOKS_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.RESULT_CONTENT
	};

	var bookCustomContent = {
		controller: CONTROLLER.BOOKS_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.BOOK_CUSTOM_CONTENT
	};

	var bookStandardContent = {
		controller: CONTROLLER.BOOKS_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.BOOK_STANDARD_CONTENT
	};

	var booksContent = {
		controller: CONTROLLER.BOOKS_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.BOOKS_CONTENT
	};

	var resultsContent = {
		controller: CONTROLLER.BOOKS_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.RESULTS_CONTENT
	};

	var paginationTool = {
		templateUrl: PATH.PAGINATION_TOOL
	};

	var searchTool = {
		controller: CONTROLLER.SEARCH_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.SEARCH_TOOL
	};

	$stateProvider
	.state(STATE.LOGIN, {
		title: TITLE.LOGIN,
		url: URL.LOGIN,
		views: {
			header: welcomeHeader,
			content: loginForm,
			footer: footer
		}
	})

	.state(STATE.REGISTER, {
		title: TITLE.REGISTER,
		url: URL.REGISTER,
		views: {
			header: welcomeHeader,
			content: registerForm,
			footer: footer
		}
	})

	.state(STATE.SEARCH, {
		title: TITLE.SEARCH,
		url: URL.SEARCH,
		params: {
			chosenFacets: undefined,
			facets: undefined,
			page: '1',
			query: '',
			queryFacets: undefined
		},
		views: {
			header: header,
			search: searchTool,
			pagination: paginationTool,
			content: resultsContent,
			footer: footer
		}
	})

	.state(STATE.UPLOAD_BOOKS, {
		title: TITLE.UPLOAD_BOOKS,
		url: URL.UPLOAD_BOOKS,
		views: {
			header: header,
			content: uploadBooksForm,
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
			pagination: paginationTool,
			content: booksContent,
			footer: footer
		}
	})

	.state(STATE.BOOK_CUSTOM, {
		title: TITLE.BOOK,
		url: URL.BOOK_CUSTOM,
		params: {
			page: '1'
		},
		views: {
			header: header,
			content: bookCustomContent,
			footer: footer
		}
	})

	.state(STATE.BOOK_STANDARD, {
		title: TITLE.BOOK,
		url: URL.BOOK_STANDARD,
		views: {
			header: header,
			content: bookStandardContent,
			footer: footer
		}
	});

	$urlRouterProvider.otherwise(URL.HOME);

});