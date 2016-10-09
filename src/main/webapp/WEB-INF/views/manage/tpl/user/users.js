app.controller('UsersCtrl', ['$scope', '$http', function($scope, $http) {
	///http://angular-ui.github.io/ui-grid/
	$scope.userSelections=[];
	
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    }; 
    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [250, 500, 1000],
        pageSize: 250,
        currentPage: 1
    };  
    $scope.setPagingData = function(data, page, pageSize){  
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.usersData = pagedData;
        
        console.log($scope.usersData);
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data,url='./user/reads';
            if (searchText) {
                var ft = searchText.toLowerCase();
                $http.get(url).success(function (largeLoad) {    
                    data = largeLoad.data.filter(function(item) {
                        return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                    });
                    $scope.setPagingData(data,page,pageSize);
                });            
            } else {
                $http.get(url).success(function (largeLoad) {
                    $scope.setPagingData(largeLoad.data,page,pageSize);
                });
            }
        }, 100);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.add=function(userData){
    	$scope.userData={avatar: null,
						description: null,
						email: null,
						encrypt: null,
						id: null,
						isOnline: null,
						lastloginip: null,
						lastlogintime: null,
						password: null,
//						repassword:null,
						phone: null,
						realName: null,
						regtime: null,
						username: null,
						wechat: null,
						wechatName: null};
    }
    
    
    $scope.save=function(userData){
    	console.log(userData);
    	var data= userData,url='./user/update',param={method:'PATCH' ,'url':url,'data':data};
    	if(data.id==null){
    		param.method='PUT';
    		param.url= './user/create';
    	}
    	 $http(param).success(function (largeLoad) {    
    		
    		 if(userData.id==null && largeLoad.data.id !=null){
    			 console.log($scope.usersData);
    			 $scope.usersData.push(largeLoad.data);
    		 }
    		 $scope.userData= largeLoad.data;
    	 });
    }
    $scope.afterSelectionChange=function(rowItem, event){
    	console.log(rowItem);
    	console.log(event);
    	$scope.userData=rowItem.entity;
    }
    
    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.gridOptions = {
        data: 'usersData',
        enablePaging: true,
        showFooter: true,
       // enableCellEdit: true,
        showFilter:true,
        selectedItems: $scope.userSelections,
        afterSelectionChange : $scope.afterSelectionChange,
        multiSelect: false,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions
    };
    
    
    
    
    
}]);