app.directive('ckeditor', [ '$ocLazyLoad', '$document', function($ocLazyLoad, $document) {
	return {
		require : '?ngModel',
		link : function(scope, element, attrs, ngModel) {
			$ocLazyLoad.load([ './public/vendor/ckeditor/4.6.0/ckeditor.js' ]).then(function() {

				$ocLazyLoad.load([ './public/vendor/ckeditor/4.6.0/config.js' ]).then(function() {

					var ckeditor = CKEDITOR.replace(element[0], {

					});
					if (!ngModel) {
						return;
					}
				/*	
				 ckeditor.on('instanceReady', function() {
						ckeditor.setData(ngModel.$viewValue);
					});
					ckeditor.on('pasteState', function() {
						scope.$apply(function() {
							ngModel.$setViewValue(ckeditor.getData());
						});
					});
					ngModel.$render = function(value) {
						ckeditor.setData(ngModel.$viewValue);
					};
			    */
				})

			})

		}
	};
} ]);