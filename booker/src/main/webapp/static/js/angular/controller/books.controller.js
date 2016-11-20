'use strict';
app.controller('BooksController', function($state, STATE, BooksFactory, FlashService, PaginationService) {

	var self = this;
	self.books = [];
	self.book;

	function init(state) {
		switch (state) {
			case STATE.BOOKS:
				var page = $state.params.page;
				getBooks(page, state);
				break;
			case STATE.BOOK:
				var id = $state.params.id;
				getBook(id);
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

	function getBook(id) {
		BooksFactory.getBook(id, function(response) {
			if (response.success) {
				var data = response.data;
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