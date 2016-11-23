app.directive('uiMenu', [ '$templateCache', '$http', '$state', function( $templateCache, $http, $state) {
	$templateCache.put('navigation/menu.html', '<a href="" data-nodrag ng-click="spread(this)"> <i class="fa fa-fw " ng-class="{ \'fa-angle-right\': !node.collapsed, \' fa-angle-down\': node.collapsed }"> </i></a> 		<a herf="" ng-click="open(this)">{{node.title}}</a><ul  class="list-unstyled l-h-2x padder" ng-model="node.children" ng-class="{hidden: !node.collapsed}">				<li ng-repeat="node in node.children"  data-collapsed="true" ng-include="\'navigation/menu.html\'"></li>				</ul>');
	return {
		restrict : 'AC',
		scope : {
			uiTree : '@',
			menus : '@',
			toggle : '&',
			open : '&'
		},
		template : '<div  ><ul class="list-unstyled l-h-2x padder"  ng-model="menusData"><li ng-repeat="node in menusData"  data-collapsed="true" ng-include="\'navigation/menu.html\'"></li></ul></div>',
		link : function(scope, el, attr) {
			// el.addClass('hide');
			// $ocLazyLoad.load(attr.uiTree || 'public/vendor/angular/angular-ui-tree/angular-ui-tree.min.js').then(function() {
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
							sco.node.collapsed = true;
							if (typeof sco.toggle === 'function')
								sco.toggle();
						});

					}, 100);
				} else {
					sco.node.collapsed = !sco.node.collapsed;

					if (typeof sco.toggle === 'function')
						sco.toggle();
				}
			};
			scope.open = attr.open || function(sco) {
				console.log(sco);
				console.log('app.article.read({menuId:' + sco.node.id+'})');
				$state.go('app.article.read',{menuId: sco.node.id});
				//location.href='#/app/article/'+ sco.node.id;
			}

			// });
		}
	};
} ]);