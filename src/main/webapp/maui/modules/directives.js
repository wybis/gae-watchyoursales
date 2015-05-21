function headerBar() {
	return {

		restrict : 'E',

		replace : true,

		templateUrl : 'maui/modules/zgeneral/m-headerBar.html',

		link : function($scope) {
		}
	};
}
appDirectives.directive('headerBar', [ headerBar ]);

function footerBar() {
	return {

		restrict : 'E',

		replace : true,

		templateUrl : 'maui/modules/zgeneral/m-footerBar.html',

		link : function($scope) {
		}
	};
}
appDirectives.directive('footerBar', [ footerBar ]);
