'use strict';
app.controller('BooksController', function($state, STATE, BooksFactory, FlashService, PaginationService) {

	var self = this;

	function init(state) {
		switch (state) {
			case STATE.BOOKS:
				var page = $state.params.page;
				getBooks(page, state);
				break;
			case STATE.BOOK_CUSTOM:
				var id = $state.params.id;
				var page = $state.params.page;
				getBookCustom(id, page);
				break;
			case STATE.BOOK_STANDARD:
				var id = $state.params.id;
				getBookStandard(id);
				break;
		}
	}

	function getBooks(page, state) {
		BooksFactory.getBooks(page, function(response) {
			if (response.success) {
				var resp = response.data;
				self.books = resp.docs;
				PaginationService.setPages(state, resp.page, resp.pagesCount);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getBookCustom(id, page) {
		BooksFactory.getBookCustom(id, page, function(response) {
			if (response.success) {
				var data = response.data;
				$state.current.title = data.title;
				self.book = data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getBookStandard(id) {
		BooksFactory.getBookStandard(id, function(response) {
			if (response.success) {
				var data = response.data;
				$state.current.title = data.title;
				read(data.id, data.file_name);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function read(id, file) {
		self.book = ePub('book/' + id + '/' + file);
		self.book.renderTo('area');
	}

	init($state.current.name);

});