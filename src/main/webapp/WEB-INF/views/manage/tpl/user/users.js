app.controller('UsersCtrl', [
		'$scope',
		'$http',
		function($scope, $http) {
			// /http://angular-ui.github.io/ui-grid/
			//$scope.userSelections = [];
			//  $aside({	scope : $scope,	template : 'attrEditorForm.tpl.html',	show : true});
			

			setTimeout(function() {
				$scope.roles = [];
				$scope.permissions = [];

				$http.get('./role/reads').success(function(largeLoad) {
					$scope.roles = largeLoad.data;
				});

				$http.get('./permission/reads').success(function(largeLoad) {
					$scope.permissions = largeLoad.data;
				});
			}, 100);

			$scope.add = function(userData) {
				$scope.userData = {
					avatar : null,
					description : "这家伙什么都没留下!  ~_~",
					email : null,
					encrypt : null,
					id : null,
					isOnline : false,
					lastloginip : '127.0.0.1',
					lastlogintime : (new Date().getTime()),
					password : null,
					// repassword:null,
					phone : null,
					realName : null,
					regtime : (new Date().getTime()),
					username : null,
					wechat : null,
					wechatName : null
				};
			}
			
			
			$scope.rolesEditor = function(userData) {
				console.log(userData);
				if (!userData || !userData.id) {
					alert("请先 [选择行/保存数据] 后,再操作!!");
					return;
				}

				if (!!userData.roles) {
					for ( var i in userData.roles) {
						if(!$scope.userData.userRolesData)$scope.userData.userRolesData=[];
						$scope.userData.userRolesData
								.push(userData.roles[i].roleId);
					}
					return;
				}

				if (!!userData.userRolesData) {

					// 可还原 userData.roles 值
					return;
				}

				var url = './userrole/read/userid/' + userData.id;

				$http.get(url).success(
						function(largeLoad) {
							console.log(largeLoad.data);
							userData.roles = largeLoad.data;
							$scope.userData.userRolesData = [];

							for ( var i in largeLoad.data) {
								$scope.userData.userRolesData
										.push(largeLoad.data[i].roleId);
							}
							console.log($scope.userData.userRolesData);
						});

			}

			$scope.permissionsEditor = function(userData) {
				console.log(userData);
				if (!userData || !userData.id) {
					alert("请先 [选择行/保存数据] 后,再操作!!");
					return;
				}

				if (!!userData.permissions) {
					if(!$scope.userData.userRolesData)$scope.userData.userRolesData=[];
					for ( var i in userData.permissions) {
						$scope.userData.userRolesData
								.push(userData.permissions[i].roleId);
					}
					return;
				}
				if (!!userData.userPermissionsData) {

					// 可还原 userData.permissions 值
					return;
				}

				var url = './userpermission/read/userid/' + userData.id;
				$http.get(url).success(
						function(largeLoad) {
							console.log(largeLoad.data);
							userData.permissions = largeLoad.data;
							$scope.userData.userPermissionsData = [];
							for ( var i in largeLoad.data) {
								$scope.userData.userPermissionsData
										.push(largeLoad.data[i].permissionId);
							}
							console.log($scope.userData.userPermissionsData);
						});
			}

			$scope.editorAvatar = function(userData) {
				console.log(userData);

			}

			$scope.saveRoles = function(userRolesData) {
				console.log(userRolesData);

			}

			$scope.savePermissions = function(userPermissionsData) {
				console.log(userPermissionsData);

			}

			$scope.save = function(userData) {
				console.log(userData);
				var data = userData, url = './user/update', param = {
					method : 'PATCH',
					'url' : url,
					'data' : data
				};
				if (data.id == null) {
					param.method = 'PUT';
					param.url = './user/create';
				}
				$http(param).success(function(largeLoad) {
					if (userData.id == null && largeLoad.data.id != null) {
						console.log($scope.usersData);
						$scope.usersData.push(largeLoad.data);
					}
					$scope.userData = largeLoad.data;
				});
			}

			
			$scope.filterOptions = {
					filterText : "",
					useExternalFilter : true 
				};
			$scope.totalServerItems = 0;
			$scope.pagingOptions = {
				pageSizes : [ 250, 500, 1000 ],
				pageSize : 250,
				currentPage : 1
			};
			$scope.setPagingData = function(data, page, pageSize) {
				var pagedData = data.slice((page - 1) * pageSize, page
						* pageSize);
				$scope.usersData = pagedData;

				console.log($scope.usersData);
				$scope.totalServerItems = data.length;
				if (!$scope.$$phase) {
					$scope.$apply();
				}
			};
			$scope.getPagedDataAsync = function(pageSize, page, searchText) {
				setTimeout(function() {
					var data, url = './user/reads';
					if (searchText) {
						var ft = searchText.toLowerCase();
						$http.get(url).success(
								function(largeLoad) {
									data = largeLoad.data
											.filter(function(item) {
												return JSON.stringify(item)
														.toLowerCase().indexOf(
																ft) != -1;
											});
									$scope.setPagingData(data, page, pageSize);
								});
					} else {
						$http.get(url).success(
								function(largeLoad) {
									$scope.setPagingData(largeLoad.data, page,
											pageSize);
								});
					}
				}, 100);
			};

			$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
					$scope.pagingOptions.currentPage);

			$scope.afterSelectionChange = function(rowItem, event) {
				console.log(rowItem);
				console.log(event);
				$scope.userData = rowItem.entity;
				
			}

			$scope.$watch('pagingOptions', function(newVal, oldVal) {
				if (newVal !== oldVal
						&& newVal.currentPage !== oldVal.currentPage) {
					$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
							$scope.pagingOptions.currentPage,
							$scope.filterOptions.filterText);
				}
			}, true);
			$scope.$watch('filterOptions', function(newVal, oldVal) {
				if (newVal !== oldVal) {
					$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
							$scope.pagingOptions.currentPage,
							$scope.filterOptions.filterText);
				}
			}, true);

			$scope.gridOptions = {
				data : 'usersData',
				enablePaging : true,
				showFooter : true,
				// enableCellEdit: true,
				showFilter : true,
			//	selectedItems : $scope.userSelections,
				afterSelectionChange : $scope.afterSelectionChange,
				multiSelect : false,
				totalServerItems : 'totalServerItems',
				pagingOptions : $scope.pagingOptions,
				filterOptions : $scope.filterOptions
			};

		} ]);