'use strict';
app.constant('CONTROLLER', (function() {
	return {
		CTRL: 'ctrl',
		BOOKS_CONTROLLER: 'BooksController',
		SEARCH_CONTROLLER: 'SearchController',
		UPLOAD_CONTROLLER: 'UploadController',
		USERS_CONTROLLER: 'UsersController'
	}
})());

app.constant('MESSAGE', (function() {
	var deletingError = 'Error while deleting ';
	var gettingError = 'Error while getting ';
	return {
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		FILE_UPLOADING_SUCCESS: 'File has been uploaded successfully',
		GETTING_BOOKS_ERROR: gettingError + 'books',
		GETTING_PROGRESS_ERROR: gettingError + 'progress',
		GETTING_USER_ERROR: gettingError + 'user',
		PASSWORDS_ERROR: 'Passwords do not match',
		TAKEN_LOGIN_ERROR: 'This login is already taken',
		VALIDATION_ERROR: 'All required attributes must be filled'
	}
})());

app.constant('PATH', (function() {
	var path = 'html';
	var contentPath = path + '/content';
	var formPath = path + '/form';
	var infoPath = path + '/info';
	var titlePath = path + '/title';
	var toolPath = path + '/tool';
	var htmlExt = '.html';
	return {
		BOOK_CUSTOM_CONTENT: contentPath + '/book.custom' + htmlExt,
		BOOK_STANDARD_CONTENT: contentPath + '/book.standard' + htmlExt,
		BOOKS_CONTENT: contentPath + '/books' + htmlExt,
		RESULTS_CONTENT: contentPath + '/results' + htmlExt,
		LOGIN_FORM: formPath + '/login' + htmlExt,
		REGISTER_FORM: formPath + '/register' + htmlExt,
		UPLOAD_BOOKS_FORM: formPath + '/upload.books' + htmlExt,
		FOOTER: titlePath + '/footer' + htmlExt,
		HEADER: titlePath + '/header' + htmlExt,
		WELCOME_HEADER: titlePath + '/welcome.header' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt,
		SEARCH_TOOL: toolPath + '/search' + htmlExt
	}
})());

app.constant('REST', (function() {
	var service = '/service';
	return {
		BOOKS: service + '/books',
		UPLOAD: service + '/upload',
		USERS: service + '/users'
	}
})());

app.constant('STATE', (function() {
	var delimiter = '_';
	var book = 'book';
	return {
		BOOK_CUSTOM: book + delimiter + 'custom',
		BOOK_STANDARD: book + delimiter + 'standard',
		BOOKS: 'books',
		LOGIN: 'login',
		REGISTER: 'register',
		SEARCH: 'search',
		UPLOAD_BOOKS: 'upload_books'
	}
})());

app.constant('TITLE', (function() {
	return {
		BOOK: 'Book',
		BOOKS: 'Books',
		LOGIN: 'Login',
		REGISTER: 'Register',
		SEARCH: 'Search',
		UPLOAD_BOOKS: 'Upload books'
	}
})());

app.constant('URL', (function() {
	var booksUrl = '/books';
	var bookUrl = '/book';
	var uploadOperation = '/upload';
	var idKey = '{id}';
	var pageKey = '{page:[0-9]{1,}}';
	var queryKey = '{query}';
	return {
		HOME: booksUrl + '?page=1',
		BOOK_CUSTOM: bookUrl + '/viewer/' + idKey + '?' + pageKey,
		BOOK_STANDARD: bookUrl + '/' + idKey,
		BOOKS: booksUrl + '?' + pageKey,
		LOGIN: '/login',
		REGISTER: '/register',
		SEARCH: '/search?' + queryKey + '&' + pageKey,
		UPLOAD_BOOKS: booksUrl + uploadOperation
	}
})());