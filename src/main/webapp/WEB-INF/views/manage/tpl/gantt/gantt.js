app.controller('GanttCtrl', ['$scope', '$http', function($scope, $http) {
	$scope.data =[];
	
	 setTimeout(function () {
         var data,url='./gantt/stems/root';
    
             $http.get(url).success(function (largeLoad) {    
            	 $scope.data = largeLoad.data.filter(function(item) {
                     return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                 });
             });            
    
     }, 100);
	
}]);

