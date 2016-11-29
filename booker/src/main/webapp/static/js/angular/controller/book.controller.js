'use strict';
app.controller('BookController', function($state, STATE, BookFactory, SearchFactory, FlashService, PaginationService) {

	var self = this;

	self.prevPage = function() {
		var id = $state.params.id;
		var page = self.book.content[0].page - 1;
		if (page > 0) {
			$state.go(STATE.BOOK_CUSTOM, {id: id, page: page, content: undefined});
		}
	};

	self.nextPage = function() {
		var id = $state.params.id;
		var page = self.book.content[self.book.content.length - 1].page + 1;
		if (page <= self.book.pagesCount) {
			$state.go(STATE.BOOK_CUSTOM, {id: id, page: page, content: undefined});
		}
	};

	function init() {
		PaginationService.setPages(undefined, 0, 0);

		switch ($state.current.name) {
			case STATE.SEARCH:
				var chosenFacets = $state.params.chosenFacets;
				var facets = $state.params.facets;
				var page = $state.params.page;
				var query = $state.params.query;
				var queryFacets = $state.params.queryFacets;
				if (facets !== undefined) {
					if (query !== '') {
						getFacetedQuerySearchResult(query, facets, page);
					} else {
						getFacetedSearchResult(facets, page);
					}
				} else {
					if (query !== '') {
						getSearchResult(query, page);
					}
				}
				break;
			case STATE.BOOKS:
				var page = $state.params.page;
				getBooks(page);
				break;
			case STATE.BOOK_CUSTOM:
				var content = $state.params.content;
				var id = $state.params.id;
				var page = $state.params.page;
				getBookCustom(id, page, content);
				break;
			case STATE.BOOK_STANDARD:
				var id = $state.params.id;
				getBookStandard(id);
				break;
		}
	}

	function getBooks(page) {
		BookFactory.getBooks(page, function(response) {
			if (response.success) {
				var resp = response.data;
				self.books = resp.docs;
				PaginationService.setPages($state.current.name, resp.page, resp.pagesCount);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getBookCustom(id, page, content) {
		BookFactory.getBookCustom(id, page, function(response) {
			if (response.success) {
				var data = response.data;
				$state.current.title = data.title;
				self.book = data;
				if (content !== undefined) {
					for (var i = 0; i < self.book.content.length; i++) {
						if (self.book.content[i].page == page) {
							self.book.content[i].content = content;
						}
					}
				}
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getBookStandard(id) {
		BookFactory.getBookStandard(id, function(response) {
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

	function getFacetedQuerySearchResult(query, facets, page) {
		SearchFactory.getFacetedQuerySearchResult(query, angular.toJson(facets), page, function(response) {
			if (response.success) {
				var resp = response.data;
				self.results = resp;
				PaginationService.setPages($state.current.name, resp.page, resp.pagesCount);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getFacetedSearchResult(facets, page) {
		SearchFactory.getFacetedSearchResult(angular.toJson(facets), page, function(response) {
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