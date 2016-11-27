'use strict';
var ngFileRequired = 'ngFileRequired';
var ngFileModel = 'ngFileModel';
var ngMatch = 'ngMatch';
var ngModel = 'ngModel';
var ngCheckLogin = 'ngCheckLogin';

app.directive(ngCheckLogin, function($timeout, $q, UserFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngCheckLogin = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);
				timer = $timeout(function() {
					UserFactory.checkLogin(modelValue, function(response) {
						if (!response.success) {
							def.resolve();
						} else {
							def.reject();
						}
					});
				}, 1000);
				return def.promise;
			};
		}
	};
});

app.directive(ngMatch, function() {
	return {
		require: ngModel,
		scope: {
			otherModelValue: "=" + ngMatch
		},
		link: function(scope, element, attributes, controller) {
			controller.$validators.ngMatch = function(modelValue) {
				return modelValue == scope.otherModelValue;
			};
			scope.$watch("otherModelValue", function() {
				controller.$validate();
			});
		}
	};
});

app.directive(ngFileRequired, function() {
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			element.bind('change', function() {
				scope.$apply(function() {
					controller.$setViewValue(element.val());
					controller.$render();
				});
			});
		}
	}
});

app.directive(ngFileModel, ['$parse', function($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attributes) {
			var model = $parse(attributes.ngFileModel);
			var isMultiple = attributes.multiple;
			var modelSetter = model.assign;
			element.bind('change', function() {
				var values = [];
				angular.forEach(element[0].files, function(item) {
					var value = {
						name: item.name,
						size: item.size,
						url: URL.createObjectURL(item),
						file: item
					};
					values.push(value);
				});
				scope.$apply(function() {
					if (isMultiple) {
						modelSetter(scope, values);
					} else {
						modelSetter(scope, values[0]);
					}
				});
			});
		}
	};
}]);