app.controller('PermissionsCtrl', ['$scope', '$http', function($scope, $http) {
	
	$scope.save = function(permissionData) {
		console.log(permissionData);
		var data = permissionData, url = './permission/update', param = {
			method : 'PATCH',
			'url' : url,
			'data' : data
		};
		if (data.id == null) {
			param.method = 'PUT';
			param.url = './permission/create';
		}
		$http(param).success(function(largeLoad) {
			if (permissionData.id == null && largeLoad.data.id != null) {
				console.log($scope.usersData);
				$scope.permissionsData.push(largeLoad.data);
			}
			$scope.permissionData = largeLoad.data;
		});
	}
	$scope.add = function(permissionData) {
		$scope.permissionData = {
			id:null,
			description: '新建权限,请修改说明,以便其他管理组识别区分!',
			entityIds:'',
			name:'',
			permission:''
		};
	}
	//$scope.permissionSelections=[];
	
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
        $scope.permissionsData = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data,url='./permission/reads';
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
    $scope.afterSelectionChange = function(rowItem, event) {
		console.log(rowItem);
		console.log(event);
		$scope.permissionData = rowItem.entity;
		
	}
    $scope.gridOptions = {
        data: 'permissionsData',
        enablePaging: true,
        showFooter: true,
        //enableCellEdit: true,
        showFilter:true,
        //selectedItems: $scope.permissionSelections,
        afterSelectionChange : $scope.afterSelectionChange,
        multiSelect: false,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions
    };
}]);