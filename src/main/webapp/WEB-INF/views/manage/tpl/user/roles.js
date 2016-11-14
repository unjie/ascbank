app.controller('RolesCtrl', ['$scope','$uibModal', '$http', function($scope,$uibModal, $http) {
	
	//$scope.roleSelections=[];
	
	setTimeout(function() {
		//$scope.roles = [];
		$scope.permissions = [];

		/*$http.get('./role/reads').success(function(largeLoad) {
			$scope.roles = largeLoad.data;
		});*/

		$http.get('./permission/reads').success(function(largeLoad) {
			$scope.permissions = largeLoad.data;
		});
	}, 100);
	
	$scope.save = function(roleData) {
		console.log(roleData);
		var data = roleData, url = './role/update', param = {
			method : 'PATCH',
			'url' : url,
			'data' : data
		};
		if (data.id == null) {
			param.method = 'PUT';
			param.url = './role/create';
		}
		$http(param).success(function(largeLoad) {
			if (roleData.id == null && largeLoad.data.id != null) {
				console.log($scope.rolesData);
				$scope.rolesData.push(largeLoad.data);
			}
			$scope.roleData = largeLoad.data;
		});
	}
	
	$scope.add = function(roleData) {
		$scope.roleData = {
			id:null,
			description: '新建角色,请修改说明,以便其他管理组识别区分!',
			roleName: "new Role Name"
		};
	}
	
	
	$scope.permissionsEditor = function(roleData) {
		console.log(roleData);
		if (!roleData || !roleData.id) {
			alert("请先 [选择行/保存数据] 后,再操作!!");
			return;
		}
			
		if (!!roleData.permissions && !$scope.roleData.rolePermissionsData) {
			if(!$scope.roleData.rolePermisonsData)$scope.roleData.rolePermissionsData=[];
			for ( var i in roleData.permissions) {
				$scope.roleData.rolePermissionsData
						.push(roleData.permissions[i].id);
			}
			return;
		}
		if (!!roleData.rolePermissionsData) {

			// 可还原 roleData.permissions 值
			return;
		}
		
		var url = './rolepermission/read/roleid/' + roleData.id;
		$http.get(url).success(
				function(largeLoad) {
					console.log(largeLoad.data);
					roleData.permissions = largeLoad.data;
					$scope.roleData.rolePermissionsData = [];
					for ( var i in largeLoad.data) {
						$scope.roleData.rolePermissionsData
								.push(largeLoad.data[i].permissionId);
					}
					console.log($scope.roleData.rolePermissionsData);
				});
	}
	
	
	
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
        $scope.rolesData = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };
            
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data,url='./role/reads';
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
    $scope.afterSelectionChange = function(rowItem, event) {
		console.log(rowItem);
		console.log(event);
		$scope.roleData = rowItem.entity;
		
	}
    
    
    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('roleData', function (newVal, oldVal) {
        if (newVal !== oldVal &&( !oldVal || newVal.id !== oldVal.id )) {
        	$uibModal.open({
    		    size: 'lg m-n no-padder',
    			templateUrl : 'aside/attrEditorForm.tpl.html',
    			scope:$scope
    		});
        }
    }, true);
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
        data: 'rolesData',
        enablePaging: true,
        showFooter: true,
      //  enableCellEdit: true,
        showFilter:true,
     //   selectedItems: $scope.roleSelections,
        afterSelectionChange : $scope.afterSelectionChange,
        multiSelect: false,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions
    };
}]);