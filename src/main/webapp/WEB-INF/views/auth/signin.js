'use strict';

/* Controllers */
  // signin controller
app.controller('SigninFormController', ['$scope', '$http', '$state', function($scope,  $http, $state) {
	var img="captcha/image.jpg";
    $scope.user = {};
    $scope.authError = null;
    $scope.captcha=img;
    $scope.login = function() {
    	
	    $scope.authError = null;
	     var data = {'password': $scope.user.password,'captcha':$scope.user.captcha};
		     if(/^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g.test($scope.user.email)){
		    	 data.email= $scope.user.email;
		     }else if(/^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/g.test($scope.user.email)){
		    	 data.phone=$scope.user.email;
			}else {
				 data.username=$scope.user.email;
			}
	     
	      
	      // Try to login
	      $http.post('./auth/login', data)
	      .then(function(response) {
	    	 console.log(response);
	    	 var info = response.data;
	    	 console.log(info);
	        if (!info.success ) {
	          $scope.authError = info.message;
	        }else{
	         // $state.go('app.dashboard-v1');
	        	$cookieStore.put('user',info.data);   	
	        	$state.go('app.index');
	        }
	      }, function(x) {
	        $scope.authError = 'Server Error';
	      });
    };
    
    $scope.refresh=function(e){
    	console.log(e);
    	 $scope.captcha= img+"?t="+(new Date().getTime());
    }
    
  }])
;