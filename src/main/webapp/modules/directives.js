function footerNavigator() {
	return {

		restrict : 'E',

		replace : true,

		templateUrl : 'modules/zgeneral/d-footerNavigator.html',

		link : function($scope) {
		}
	};
}
appDirectives.directive('footerNavigator', [ footerNavigator ]);
