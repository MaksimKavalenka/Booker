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

	function getFacetedQuerySearchResult(query, facets, page, callback) {
		if (!ValidatorService.allNotEmpty(callback, query, facets, page)) {
			return;
		}

		$http.get(REST.SEARCH + '?query=' + query + '&facets=' + facets + '&page=' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_RESULTS_ERROR};
			callback(response);
		});
	}

	function getFacetedSearchResult(facets, page, callback) {
		if (!ValidatorService.allNotEmpty(callback, facets, page)) {
			return;
		}

		$http.get(REST.SEARCH + '?facets=' + facets + '&page=' + page)
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
		getFacetedQuerySearchResult: getFacetedQuerySearchResult,
		getFacetedSearchResult: getFacetedSearchResult,
		getSuggestions: getSuggestions,
		getFacets: getFacets
	};

});