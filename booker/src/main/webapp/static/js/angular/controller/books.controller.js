'use strict';
app.controller('BooksController', function($state, STATE, BooksFactory, SearchFactory, FlashService, PaginationService) {

	var self = this;

	self.prevPage = function() {
		var id = $state.params.id;
		var page = self.book.content[0].page - 1;
		if (page > 0) {
			$state.go(STATE.BOOK_CUSTOM, {id: id, page: page});
		}
	};

	self.nextPage = function() {
		var id = $state.params.id;
		var page = self.book.content[self.book.content.length - 1].page + 1;
		if (page <= self.book.pagesCount) {
			$state.go(STATE.BOOK_CUSTOM, {id: id, page: page});
		}
	};

	function init() {
		switch ($state.current.name) {
			case STATE.SEARCH:
				var facets = $state.params.facets;
				var page = $state.params.page;
				var query = $state.params.query;
				if (query !== '') {
					alert(facets);
					if (facets === undefined) {
						getSearchResult(query, page);
					} else {
						getFacetedSearchResult(query, page, angular.toJson(facets));
					}
				}
				break;
			case STATE.BOOKS:
				var page = $state.params.page;
				getBooks(page);
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

	function getBooks(page) {
		BooksFactory.getBooks(page, function(response) {
			if (response.success) {
				var resp = response.data;
				self.books = resp.docs;
				PaginationService.setPages($state.current.name, resp.page, resp.pagesCount);
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
				read(data.id, data.fileName);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getSearchResult(query, page) {
		SearchFactory.getSearchResult(query, page, function(response) {
			if (response.success) {
				var resp = response.data;
				self.results = resp;
				PaginationService.setPages($state.current.name, resp.page, resp.pagesCount);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getFacetedSearchResult(query, page, facets) {
		SearchFactory.getFacetedSearchResult(query, page, facets, function(response) {
			if (response.success) {
				var resp = response.data;
				self.results = resp;
				PaginationService.setPages($state.current.name, resp.page, resp.pagesCount);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function read(id, file) {
		self.book = ePub('book/' + id + '/' + file);
		self.book.renderTo('area');
	}

	init();

});