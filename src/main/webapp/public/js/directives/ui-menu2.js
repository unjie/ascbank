app.directive('uiMenu', [ '$ocLazyLoad', '$templateCache', '$http',function($ocLazyLoad, $templateCache, $http) {
	$templateCache.put('navigation/menu.html', '<a href="" data-nodrag ng-click="spread(this)"> <i class="fa fa-fw " ng-class="{ \'fa-angle-right\': collapsed, \' fa-angle-down\': !collapsed }"> </i></a> 		<a herf="" ng-click="open(this)">{{node.title}}</a><ul ui-tree-nodes="" class="list-unstyled l-h-2x padder" ng-model="node.children" ng-class="{hidden: collapsed}">				<li ng-repeat="node in node.children" ui-tree-node data-collapsed="true" ng-include="\'navigation/menu.html\'"></li>				</ul>');
	return {
		restrict : 'AC',
		//require : 'uitree',
		scope : {
			uiTree : '@',
			menus : '@',
			toggle : '&',
			open : '&'
		},
		template : '<div  ui-tree ><ul class="list-unstyled l-h-2x padder" ui-tree-nodes ng-model="menusData"><li ng-repeat="node in menusData" ui-tree-node data-collapsed="true" ng-include="\'navigation/menu.html\'"></li></ul></div>',
		link : function(scope, el, attr) {
			//el.addClass('hide');
		//	$ocLazyLoad.load(attr.uiTree || 'public/vendor/angular/angular-ui-tree/angular-ui-tree.min.js').then(function() {
				scope.menusData = [] || attr.menus;
				if (scope.menusData.length < 1) {
					setTimeout(function() {
						var data, url = './menu/stems/root';
						$http.get(url).success(function(largeLoad) {
							scope.menusData = largeLoad.data;
						});
					}, 100);
				}
				scope.spread = attr.toggle || function(sco) {
					console.log(sco);
					if (sco.node.children == null) {
						setTimeout(function() {
							var data, url = './menu/children/' + sco.node.id;
							$http.get(url).success(function(largeLoad) {
								sco.node.children = largeLoad.data;
								if(typeof sco.toggle === 'function' )
									sco.toggle();
							});

						}, 100);
					} else {
						if(typeof sco.toggle === 'function' )
							sco.toggle();
					}
				};
				scope.open = attr.open || function(sco) {
					console.log(sco);
					$state.go('app.article.' + sco.$modelValue.id);
				}

	//		});
		}
	};
} ]);