app.controller('ArticleMenuCtrl', [ '$scope', '$http', '$state', function($scope, $http, $state) {
	$scope.menusData = [];
	setTimeout(function() {
		var data, url = './menu/stems/root';
		if ($scope.menusData.length < 1) {
			$http.get(url).success(function(largeLoad) {
				$scope.menusData = largeLoad.data;
			});
		}
	}, 100);

	$scope.spread = function(scope) {
		console.log(scope);
		if (scope.node.children == null) {
			setTimeout(function() {
				var data, url = './menu/children/' + scope.node.id;
				$http.get(url).success(function(largeLoad) {
					scope.node.children = largeLoad.data;
					scope.toggle();
				});

			}, 100);
		} else {
			scope.toggle();
		}

	};

	$scope.open = function(scope) {
		console.log(scope);
		$state.go('app.article.' + scope.$modelValue.id);
	}
} ]);


/*
<div ui-tree class="col-xs-6" ng-controller="ArticleMenuCtrl" >
	<script type="text/ng-template" id="navigation/menu.html">
			<a href="" data-nodrag ng-click="spread(this)"> <i class="fa fa-fw " ng-class="{ 'fa-angle-right': collapsed, ' fa-angle-down': !collapsed }"> </i></a> 
			<a herf="" ng-click="open(this)">{{node.title}}</a>
		<ul ui-tree-nodes="" class="list-unstyled l-h-2x padder" ng-model="node.children" ng-class="{hidden: collapsed}">
			<li ng-repeat="node in node.children" ui-tree-node data-collapsed="true" ng-include="'navigation/menu.html'"></li>
		</ul>
	</script>
	<ul class="list-unstyled l-h-2x padder" ui-tree-nodes ng-model="menusData">
		<li ng-repeat="node in menusData" ui-tree-node data-collapsed="true" ng-include="'navigation/menu.html'"></li>
	</ul>
</div>
*/