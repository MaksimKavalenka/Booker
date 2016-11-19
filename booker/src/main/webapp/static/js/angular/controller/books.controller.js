'use strict';
app.controller('BooksController', function($state, STATE, BooksFactory, FlashService, PaginationService) {

	var self = this;
	self.books = [];
	self.book;
	self.content = [];

	function init(state) {
		switch (state) {
			case STATE.BOOKS:
				var page = $state.params.page;
				getBooks(page, state);
				break;
			case STATE.BOOK:
				self.book = ePub("http://92f8aa73.ngrok.io/cover/123.epub");
				self.book.renderTo("area");
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

	function getBook(id, page) {
		BooksFactory.getBook(id, page, function(response) {
			if (response.success) {
				self.content = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name);

});