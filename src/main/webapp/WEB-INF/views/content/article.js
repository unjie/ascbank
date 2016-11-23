app.controller('ArticleCtrl', [ '$scope', '$http', '$stateParams', '$templateCache', function($scope, $http, $stateParams, $templateCache) {
	console.log($stateParams.menuId);
	// setTimeout(function(){
	$http.get('./menu/read/' + $stateParams.menuId).success(function(menuInfo) {
		if (menuInfo.success) {
			$http.get('./article/read/menuid/' + $stateParams.menuId).success(function(articleInfo) {
				if (articleInfo.success ) {
					if(articleInfo.data){
						$templateCache.put('./article/context/' + $stateParams.menuId, articleInfo.data.context);
						$scope.url = './article/context/' + $stateParams.menuId;
						menuInfo.data.article = articleInfo.data;
						$scope.menuData = menuInfo.data;
					}else{
						$scope.menuData = menuInfo.data;
						alert("未添加文本内容!!!");
					}

				}
			});
		}
	});
	// },100)

} ]);