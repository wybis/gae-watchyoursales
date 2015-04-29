appFilters.filter('capitalizeFilter', function() {
	return function(input) {
		input = _.capitalize(input);
		return input;
	};
});

appDirectives.directive('wydHolder', function() {
	return {
		link : function(scope, element, attrs) {
			attrs.$set('data-src', attrs.wydHolder);
			Holder.run({
				images : element[0]
			});
			// Holder.run({
			// images : element.get(0),
			// nocss : true
			// });
		}
	};
});

appDirectives.directive('wydFocusOn', function() {
	return function(scope, elem, attr) {
		return scope.$on('wydFocusOn', function(e, name) {
			if (name === attr.wydFocusOn) {
				return elem[0].focus();
			}
		});
	};
});

function wydFocusService($rootScope, $timeout) {
	return function(name) {
		return $timeout(function() {
			return $rootScope.$broadcast('wydFocusOn', name);
		});
	};
}
appServices.factory('wydFocusService', [ '$rootScope', '$timeout',
		wydFocusService ]);

function generalHttpInterceptor($log, $rootScope, $q, $window) {
	return {
		'request' : function(config) {
			$log.info('xUserId = ' + $rootScope.xUserId);
			if ($rootScope.xUserId) {
				config.headers['X-UserId'] = $rootScope.xUserId;
			}
			return config;
		},

		'requestError' : function(rejection) {
			$log.error(rejection);
			return rejection;
		},

		'response' : function(response) {
			return response;
		},

		'responseError' : function(rejection) {
			$log.error(rejection);
			if (rejection.status == 419) {
				$window.location = 'index-d.html#/signin'
				return;
			}
			return rejection;
		}
	};
}
appServices.factory('generalHttpInterceptor', generalHttpInterceptor);