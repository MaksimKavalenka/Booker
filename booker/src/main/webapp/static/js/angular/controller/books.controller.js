'use strict';
app.controller('BooksController', function($state, STATE, BooksFactory, FlashService, PaginationService) {

	var self = this;
	self.books = [];

	function init(state) {
		switch (state) {
			case STATE.BOOKS:
				var page = $state.params.page;
				getBooks(state, page);
				break;
		}
	}

	function getBooks(state, page) {
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

	init($state.current.name);

});