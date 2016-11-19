'use strict';
app.constant('CONTROLLER', (function() {
	return {
		CTRL: 'ctrl',
		BOOKS_CONTROLLER: 'BooksController',
		UPLOAD_CONTROLLER: 'UploadController',
		USERS_CONTROLLER: 'UsersController'
	}
})());

app.constant('MESSAGE', (function() {
	var deletingError = 'Error while deleting ';
	var gettingError = 'Error while getting ';
	return {
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		GETTING_BOOKS_ERROR: gettingError + 'books',
		GETTING_PROGRESS_ERROR: gettingError + 'progress',
		GETTING_USER_ERROR: gettingError + 'user',
		PASSWORDS_ERROR: 'Passwords do not match',
		TAKEN_LOGIN_ERROR: 'This login is already taken',
		FILE_UPLOADING_SUCCESS: 'File has been uploaded successfully',
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
		LOGIN_FORM: formPath + '/login' + htmlExt,
		REGISTER_FORM: formPath + '/register' + htmlExt,
		UPLOAD_BOOKS_FORM: formPath + '/upload.books' + htmlExt,
		BOOKS_CONTENT: contentPath + '/books' + htmlExt,
		BOOK_CONTENT: contentPath + '/book' + htmlExt,
		FOOTER: titlePath + '/footer' + htmlExt,
		HEADER: titlePath + '/header' + htmlExt,
		WELCOME_HEADER: titlePath + '/welcome.header' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt
	}
})());

app.constant('REST', (function() {
	var service = '/service';
	return {
		JSON_EXT: '.json',
		BOOKS: service + '/books',
		UPLOAD: service + '/upload',
		USERS: service + '/users'
	}
})());

app.constant('STATE', (function() {
	var profile = 'profile';
	var topic = 'topic';
	return {
		LOGIN: 'login',
		REGISTER: 'register',
		UPLOAD_BOOKS: 'upload_books',
		BOOKS: 'books',
		BOOK: 'book'
	}
})());

app.constant('TITLE', (function() {
	return {
		LOGIN: 'Login',
		REGISTER: 'Register',
		UPLOAD_BOOKS: 'Upload books',
		BOOKS: 'Books',
		BOOK: 'Book'
	}
})());

app.constant('URL', (function() {
	var loginUrl = '/login';
	var registerUrl = '/register';
	var booksUrl = '/books';
	var bookUrl = '/book';
	var uploadOperation = '/upload';
	var pageKey = '{page:[0-9]{1,}}';
	return {
		HOME: booksUrl + '?page=1',
		LOGIN: loginUrl,
		REGISTER: registerUrl,
		UPLOAD_BOOKS: booksUrl + uploadOperation,
		BOOKS: booksUrl + '?' + pageKey,
		BOOK: bookUrl + '/{id}?' + pageKey
	}
})());