'use strict';
// signup controller
app.controller('InfoController', [ '$scope', '$http', '$cookies', function($scope, $http, $cookies) {
	$scope.user = $cookies.getObject('user');
	
	$http.get('./user/read/'+$scope.user.id).success(function(info){
		if(info.success){
			$scope.user = info.data; 
		}
	});
} ]);
