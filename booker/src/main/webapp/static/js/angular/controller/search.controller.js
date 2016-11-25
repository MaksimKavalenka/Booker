'use strict';
app.controller('SearchController', function($state, STATE, BooksFactory, FlashService) {

	var self = this;

	self.search = function(keyCode) {
		var query = self.search.query;
		if (query !== '') {
			if (keyCode === 13) {
				$state.go(STATE.SEARCH, {query: query});
			} else if ((keyCode === -1) || (keyCode === 8) || (keyCode === 46) || ((keyCode >= 48) && (keyCode <= 57)) || ((keyCode >= 65) && (keyCode <= 90))) {
				getSuggestions(self.search.query);
			}
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
		switch ($state.current.name) {
			case STATE.SEARCH:
				self.search.query = $state.params.query;
				break;
		}
	}

	function getSuggestions(query) {
		BooksFactory.getSuggestions(query, function(response) {
			if (response.success) {
				self.suggestions = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init();

});