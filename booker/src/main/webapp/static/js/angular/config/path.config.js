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
		controller: CONTROLLER.USER_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.LOGIN_FORM
	};

	var uploadBooksForm = {
		controller: CONTROLLER.UPLOAD_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.UPLOAD_BOOKS_FORM
	};

	var registerForm = {
		controller: CONTROLLER.USER_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.REGISTER_FORM
	};

	var bookCustomContent = {
		controller: CONTROLLER.BOOK_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.BOOK_CUSTOM_CONTENT
	};

	var bookStandardContent = {
		controller: CONTROLLER.BOOK_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.BOOK_STANDARD_CONTENT
	};

	var booksContent = {
		controller: CONTROLLER.BOOK_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.BOOKS_CONTENT
	};

	var resultsContent = {
		controller: CONTROLLER.BOOK_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.RESULTS_CONTENT
	};

	var uploadsContent = {
		controller: CONTROLLER.BOOK_STATUS_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.UPLOADS_CONTENT
	};

	var facetedSearchTool = {
		controller: CONTROLLER.SEARCH_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.FACETED_SEARCH_TOOL
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
			page: '1',
			query: ''
		},
		views: {
			header: header,
			search: searchTool,
			pagination: paginationTool,
			content: resultsContent,
			footer: footer
		}
	})

	.state(STATE.FACETED_SEARCH, {
		title: TITLE.FACETED_SEARCH,
		url: URL.FACETED_SEARCH,
		params: {
			chosenFacets: undefined,
			facets: undefined,
			page: '1',
			queryFacets: undefined
		},
		views: {
			header: header,
			search: facetedSearchTool,
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

	.state(STATE.SUCCESSFUL_UPLOADS, {
		title: TITLE.SUCCESSFUL_UPLOADS,
		url: URL.SUCCESSFUL_UPLOADS,
		views: {
			header: header,
			content: uploadsContent,
			footer: footer
		}
	})

	.state(STATE.UNSUCCESSFUL_UPLOADS, {
		title: TITLE.UNSUCCESSFUL_UPLOADS,
		url: URL.UNSUCCESSFUL_UPLOADS,
		views: {
			header: header,
			content: uploadsContent,
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
			content: undefined,
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