app.controller('MenuCtrl', ['$scope','$http','$timeout',function($scope,$http, $timeout) {

	//var addData=[];
	
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
          var nodeData = scope.$modelValue,newData;
          if(nodeData.children ==null )nodeData.children=[];
          
          newData={
                  'id': null,
                  'title': nodeData.title + '.' + (nodeData.children.length + 1),
                  'parentId': nodeData.id,
                  'stem': nodeData.stem+','+nodeData.id,
                  'children': [],
                  'alias':null,
                  'author':null,
                  'clicks':null,
                  'description':null,
                  'edittime':null,
                  'isNavigation':true,
                  'isPublish':true,
                  'keyword':null,
                  'sort':0,
                  'style':"Article",
                  'thumb':null,
                  'url':null
                };
          
       //   addData.push(newData);
          nodeData.children.push(newData);
          
          $scope.menuData=newData;
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

      $scope.editSubItem= function (scope) {
    	  console.log(scope);
    	  $scope.menuData=scope.$modelValue;
    	 
      }
      $scope.save= function(menuData){
	      	console.log(menuData);
	    	var data= menuData,url='./menu/update',param={method:'PATCH' ,'url':url,'data':data};
	    	if(data.id==null){
	    		param.method='PUT';
	    		param.url= './menu/create';
	    	}
		   $http(param).success(function (largeLoad) {
			   console.log(largeLoad);
			   $scope.menuData=largeLoad.data;
		   })
    	  
      }
      
     
}]);