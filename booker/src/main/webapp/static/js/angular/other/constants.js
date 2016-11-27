'use strict';
app.constant('CONTROLLER', (function() {
	return {
		CTRL: 'ctrl',
		BOOK_STATUS_CONTROLLER: 'BookStatusController',
		BOOKS_CONTROLLER: 'BooksController',
		SEARCH_CONTROLLER: 'SearchController',
		UPLOAD_CONTROLLER: 'UploadController',
		USERS_CONTROLLER: 'UsersController'
	}
})());

app.constant('MESSAGE', (function() {
	var creatingError = 'Error while creating ';
	var gettingError = 'Error while getting ';
	return {
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		CREATING_BOOK_STATUS_ERROR: creatingError + 'book status',
		FILE_UPLOADING_SUCCESS: 'File has been uploaded successfully',
		GETTING_BOOK_STATUS_ERROR: gettingError + 'book status',
		GETTING_BOOKS_ERROR: gettingError + 'books',
		GETTING_RESULTS_ERROR: gettingError + 'results',
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
		UPLOADS_CONTENT: contentPath + '/uploads' + htmlExt,
		LOGIN_FORM: formPath + '/login' + htmlExt,
		REGISTER_FORM: formPath + '/register' + htmlExt,
		UPLOAD_BOOKS_FORM: formPath + '/upload.books' + htmlExt,
		FOOTER: titlePath + '/footer' + htmlExt,
		HEADER: titlePath + '/header' + htmlExt,
		WELCOME_HEADER: titlePath + '/welcome.header' + htmlExt,
		FACETED_SEARCH_TOOL: toolPath + '/faceted.search' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt,
		SEARCH_TOOL: toolPath + '/search' + htmlExt
	}
})());

app.constant('REST', (function() {
	var service = '/service';
	return {
		BOOK_STATUS: service + '/book-status',
		BOOKS: service + '/books',
		SEARCH: service + '/search',
		UPLOAD: service + '/upload',
		USERS: service + '/users'
	}
})());

app.constant('STATE', (function() {
	var delimiter = '_';
	var book = 'book';
	var search = 'search';
	return {
		LOGIN: 'login',
		REGISTER: 'register',
		SEARCH: search,
		FACETED_SEARCH: 'faceted' + delimiter + search,
		UPLOAD_BOOKS: 'upload_books',
		SUCCESSFUL_UPLOADS: 'successful_uploads',
		UNSUCCESSFUL_UPLOADS: 'unsuccessful_uploads',
		BOOKS: 'books',
		BOOK_CUSTOM: book + delimiter + 'custom',
		BOOK_STANDARD: book + delimiter + 'standard'
	}
})());

app.constant('TITLE', (function() {
	return {
		LOGIN: 'Login',
		REGISTER: 'Register',
		UPLOAD_BOOKS: 'Upload books',
		SUCCESSFUL_UPLOADS: 'Successful uploads',
		UNSUCCESSFUL_UPLOADS: 'Unsuccessful uploads',
		BOOKS: 'Books',
		BOOK: 'Book',
		FACETED_SEARCH: 'Faceted search',
		SEARCH: 'Search'
	}
})());

app.constant('URL', (function() {
	var booksUrl = '/books';
	var bookUrl = '/book';
	var searchUrl = '/search';
	var uploadsUrl = '/uploads';
	var uploadOperation = '/upload';
	var idKey = '{id}';
	var pageKey = '{page:[0-9]{1,}}';
	var queryKey = '{query}';
	return {
		HOME: booksUrl + '?page=1',
		LOGIN: '/login',
		REGISTER: '/register',
		SEARCH: searchUrl + '?' + queryKey + '&' + pageKey,
		FACETED_SEARCH: searchUrl + '/faceted?' + pageKey,
		UPLOAD_BOOKS: booksUrl + uploadOperation,
		SUCCESSFUL_UPLOADS: uploadsUrl + '/successful',
		UNSUCCESSFUL_UPLOADS: uploadsUrl + '/unsuccessful',
		BOOKS: booksUrl + '?' + pageKey,
		BOOK_CUSTOM: bookUrl + '/viewer/' + idKey + '?' + pageKey,
		BOOK_STANDARD: bookUrl + '/' + idKey
	}
})());