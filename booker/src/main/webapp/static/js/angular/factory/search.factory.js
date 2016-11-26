'use strict';
app.factory('SearchFactory', function($http, MESSAGE, REST, ValidatorService) {

	function getSearchResult(query, page, callback) {
		if (!ValidatorService.allNotEmpty(callback, query, page)) {
			return;
		}

		$http.get(REST.SEARCH + '?query=' + query + '&page=' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_RESULTS_ERROR};
			callback(response);
		});
	}

	function getSuggestions(query, callback) {
		if (!ValidatorService.allNotEmpty(callback, query)) {
			return;
		}

		$http.get(REST.SEARCH + '/suggest?query=' + query)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_RESULTS_ERROR};
			callback(response);
		});
	}

	function getFacets(callback) {
		$http.get(REST.SEARCH + '/facets')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_RESULTS_ERROR};
			callback(response);
		});
	}

	return {
		getSearchResult: getSearchResult,
		getSuggestions: getSuggestions,
		getFacets: getFacets
	};

});