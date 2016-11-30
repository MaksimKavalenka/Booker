'use strict';
app.controller('SearchController', function($state, STATE, SearchFactory, FlashService) {

	var self = this;
	var suggestNumber;
	var nodes;

	self.search = function(keyCode) {
		var query = self.search.query;

		if (keyCode === 13) {
			var facets = getChosenFasets();
			if (facets !== undefined) {
				if (query !== '') {
					$state.go(STATE.SEARCH, {query: query, page: 1, facets: facets, chosenFacets: self.chosenFacets, queryFacets: self.facets});
				} else {
					$state.go(STATE.SEARCH, {query: '', page: 1, facets: facets, chosenFacets: self.chosenFacets, queryFacets: self.facets});
				}
			} else {
				if (query !== '') {
					$state.go(STATE.SEARCH, {query: query, page: 1, facets: undefined, chosenFacets: undefined, queryFacets: self.facets});
				}
			}
		} else if ((keyCode === -1) || (keyCode === 8) || (keyCode === 46) || ((keyCode >= 48) && (keyCode <= 57)) || ((keyCode >= 65) && (keyCode <= 90))) {
			if (query !== '') {
				getSuggestions(self.search.query);
			}
		} else if (keyCode === 40) {
			nodes[suggestNumber].className = 'suggestion';

			++suggestNumber;
			if (nodes.length <= suggestNumber) {
				suggestNumber = 0;
			}

			self.search.query = self.suggestions[suggestNumber];
			nodes[suggestNumber].className += ' suggestion-active';
		} else if (keyCode === 38) {
			nodes[suggestNumber].className = 'suggestion';

			--suggestNumber;
			if (suggestNumber < 0) {
				suggestNumber = nodes.length - 1;
			}

			self.search.query = self.suggestions[suggestNumber];
			nodes[suggestNumber].className += ' suggestion-active';
		}
	};

	self.show = function(show) {
		if (show) {
			if (self.suggestions !== undefined) {
				var divsToShow = document.getElementsByClassName('suggestion');
				for (var i = 0; i < divsToShow.length; i++) {
					divsToShow[i].style.display = 'block';
				}
			} else {
				self.search(-1);
			}
		} else {
			var divsToHide = document.getElementsByClassName('suggestion');
			for (var i = 0; i < divsToHide.length; i++) {
				divsToHide[i].style.display = 'none';
			}
		}
	};

	function init() {
		document.getElementById('suggestions').onmouseover = function() {
			nodes[suggestNumber].className = 'suggestion';
		}

		switch ($state.current.name) {
			case STATE.SEARCH:
				self.search.query = $state.params.query;
				if ($state.params.facets !== undefined) {
					self.facets = $state.params.queryFacets;
					self.chosenFacets = $state.params.chosenFacets;
					self.showFacets = true;
				} else {
					getFacets();
				}
				break;
		}
	}

	function getSuggestions(query) {
		SearchFactory.getSuggestions(query, function(response) {
			if (response.success) {
				self.suggestions = response.data;
				suggestNumber = self.suggestions.length - 1;
				nodes = document.querySelector('.suggestions').children;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getFacets() {
		SearchFactory.getFacets(function(response) {
			if (response.success) {
				self.facets = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getChosenFasets() {
		if (self.chosenFacets !== undefined) {
			var facets = {};

			if (self.chosenFacets.author !== undefined) {
				var author = [];
				for (var i = 0; i < self.facets.author.length; i++) {
					if (self.chosenFacets.author[i]) {
						author.push(self.facets.author[i]);
					}
				}

				if (author.length > 0) {
					facets.author = author;
				}
			}

			if (self.chosenFacets.publisher !== undefined) {
				var publisher = [];
				for (var i = 0; i < self.facets.publisher.length; i++) {
					if (self.chosenFacets.publisher[i]) {
						publisher.push(self.facets.publisher[i]);
					}
				}

				if (publisher.length > 0) {
					facets.publisher = publisher;
				}
			}

			if (self.chosenFacets.uploader !== undefined) {
				var uploader = [];
				for (var i = 0; i < self.facets.uploader.length; i++) {
					if (self.chosenFacets.uploader[i]) {
						uploader.push(self.facets.uploader[i]);
					}
				}

				if (uploader.length > 0) {
					facets.uploader = uploader;
				}
			}

			if (Object.keys(facets).length !== 0) {
				return facets;
			}
		}

		return undefined;
	}

	init();

});