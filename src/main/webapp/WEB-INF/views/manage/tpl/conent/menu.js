app.controller('MenuCtrl', ['$scope','$http','$timeout',function($scope,$http, $timeout) {

	$scope.data=[];
    setTimeout(function () {
      	var data,url='./menu/stems/root';
  	    	if(	$scope.data.length<1){
  			    $http.get(url).success(function (largeLoad) {
  			    	$scope.data= largeLoad.data;

  			    });    
  	    	}
  	  },100);
    

    $scope.remove = function (scope) {
    	console.log(scope);
        scope.remove();
      };
    $scope.newSubItem = function (scope) {
          var nodeData = scope.$modelValue;
          if(nodeData.children ==null )nodeData.children=[];
          nodeData.children.push({
            id: nodeData.id * 10 + nodeData.children.length+1,
            title: nodeData.title + '.' + (nodeData.children.length + 1),
            children: []
          });
        };
        
    $scope.spread = function (scope) {
    	console.log(scope);
    	 setTimeout(function () {
    	      	var data,url='./menu/children/'+scope.$modelValue.id;
    	  	    	if(scope.$modelValue.children==null){
    	  			    $http.get(url).success(function (largeLoad) {
    	  			    	scope.$modelValue.children= largeLoad.data;
    	  			    });    
    	  	    	}
    	  	  },100);
        scope.toggle();
      };

 
     
}]);