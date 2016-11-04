app.controller('MenuCtrl', [
		'$scope',
		'$http',
		'$timeout',
		function($scope, $http, $timeout) {
			$scope.data = [];
			setTimeout(function() {
				var data, url = './menu/stems/root';
				if ($scope.data.length < 1) {
					$http.get(url).success(function(largeLoad) {
						$scope.data = largeLoad.data;
					});
				}
			}, 100);

			$scope.removeSubItem = function(scope) {
				console.log(scope);
				var  nodeData=this.$modelValue;
				if(!(nodeData.id) && !(nodeData.stem)){
					scope.remove();
				}else {
					$http.get("./menu/destroy/"+nodeData.stem	+ nodeData.id + ',').success(function(largeLoad) {
						if(largeLoad.success){
							$http.get("./menu/destroy/"+nodeData.id).success(function(largeLoad) {
								scope.remove();
							});
						}
					});
				}
				
			};
			var newMenu = function(nodeData) {
				return {
					'id' : null,
					'title' : nodeData.title + '.'
							+ (nodeData.children.length + 1),
					'parentId' : nodeData.id,
					'stem' : nodeData.stem
							+ ((!nodeData.id) ? '' : nodeData.id) + ',',
					'children' : [],
					'alias' : null,
					'author' : null,
					'clicks' : 0,
					'description' : null,
					'edittime' : new Date(),
					'isNavigation' : true,
					'isPublish' : true,
					'keyword' : nodeData.title,
					'sort' : 0,
					'style' : "Article",
					'thumb' : null,
					'url' : null
				};
			}
			$scope.newMenuRoot = function() {

				var newData = newMenu({
					id : null,
					title : 'Root ' + (new Date().getTime()),
					stem : '',
					children : []
				});
				$scope.data.push(newData);
				$scope.menuData = newData;
			}
			$scope.newSubItem = function(nodeData) {
				var  newData;
				if (!!nodeData && nodeData.id == null) {
					alert("请保存上级节点后,在添加子节点");
					return;
				}
				if (nodeData.children == null)
					nodeData.children = [];
				newData = newMenu(nodeData);
				nodeData.children.push(newData);
				$scope.menuData = newData;
			};

			$scope.spread = function(scope) {
				console.log(scope);
				if (scope.$modelValue.children == null) {
					setTimeout(function() {
						var data, url = './menu/children/'
								+ scope.$modelValue.id;
						$http.get(url).success(function(largeLoad) {
							scope.$modelValue.children = largeLoad.data;
							scope.toggle();
						});

					}, 100);
				} else {
					scope.toggle();
				}

			};

			$scope.editSubItem = function(scope) {
				console.log(scope);
				$scope.menuData = scope;

			}
			$scope.save = function(menuData) {
				console.log(menuData);
				var data = menuData, url = './menu/update', param = {
					method : 'PATCH',
					'url' : url,
					'data' : data
				};
				if (data.id == null) {
					param.method = 'PUT';
					param.url = './menu/create';
				}
				$http(param).success(
						function(largeLoad) {
							console.log(largeLoad);
							if (largeLoad.success && (!!largeLoad.data)
									&& (!!largeLoad.data.id)) {
								$scope.menuData.id = largeLoad.data.id;
							}
						})
			}

		} ]);