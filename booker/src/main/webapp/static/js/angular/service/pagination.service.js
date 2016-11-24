'use strict';
app.service('PaginationService', function($rootScope) {

	function setPages(state, page, count) {
		$rootScope.pages = [];
		if (count <= 1) {
			return;
		}

		var currentPage = parseInt(page);
		var from = ((currentPage <= 3) || (count <= 5)) ? 1 : (((currentPage + 2) >= count) ? (count - 4) : (currentPage - 2));
		var to = (currentPage <= 3) ? 5 : (currentPage + 2);
		to = (to <= count) ? to : count;

		for (var i = from; i <= to; i++) {
			var type = 'default';
			if (currentPage == i) {
				type = 'active';
			}
			$rootScope.pages.push({
				number: i,
				link: state + '({page:' + i + '})',
				type: type
			});
		}
	}

	return {
		setPages: setPages
	};

});