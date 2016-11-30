app.directive('froalaEditor', [
		'$ocLazyLoad',
		'$document',
		function($ocLazyLoad, $document, froalaConfig) {
			return {
				restrict : 'A',
				require : 'ngModel',
				// scope : scope,
				link : function(scope, element, attrs, ngModel) {
					$ocLazyLoad.load([
					// css 样式文件
					'./public/vendor/froala/2.3.4/css/froala_editor.min.css', './public/vendor/froala/2.3.4/css/froala_style.min.css', './public/vendor/froala/2.3.4/css/plugins/char_counter.css', './public/vendor/froala/2.3.4/css/plugins/code_view.css', './public/vendor/froala/2.3.4/css/plugins/colors.css', './public/vendor/froala/2.3.4/css/plugins/emoticons.css', './public/vendor/froala/2.3.4/css/plugins/file.css', './public/vendor/froala/2.3.4/css/plugins/fullscreen.css', './public/vendor/froala/2.3.4/css/plugins/image_manager.css', './public/vendor/froala/2.3.4/css/plugins/image.css', './public/vendor/froala/2.3.4/css/plugins/line_breaker.css', './public/vendor/froala/2.3.4/css/plugins/table.css', './public/vendor/froala/2.3.4/css/plugins/video.css',
					// js 文件
					'./public/vendor/froala/2.3.4/js/froala_editor.min.js',
					// 配置文件
					'./public/vendor/froala/2.3.4/froalaConfig.js', ]).then(function() {
								$ocLazyLoad.load(
										[// 其他插件
										'./public/vendor/froala/2.3.4/js/plugins/align.min.js', './public/vendor/froala/2.3.4/js/plugins/image.min.js', './public/vendor/froala/2.3.4/js/plugins/char_counter.min.js', './public/vendor/froala/2.3.4/js/plugins/code_beautifier.min.js', './public/vendor/froala/2.3.4/js/plugins/code_view.min.js', './public/vendor/froala/2.3.4/js/plugins/colors.min.js', './public/vendor/froala/2.3.4/js/plugins/emoticons.min.js', './public/vendor/froala/2.3.4/js/plugins/entities.min.js', './public/vendor/froala/2.3.4/js/plugins/file.min.js', './public/vendor/froala/2.3.4/js/plugins/font_family.min.js', './public/vendor/froala/2.3.4/js/plugins/font_size.min.js', './public/vendor/froala/2.3.4/js/plugins/fullscreen.min.js', './public/vendor/froala/2.3.4/js/plugins/inline_style.min.js', './public/vendor/froala/2.3.4/js/plugins/line_breaker.min.js', './public/vendor/froala/2.3.4/js/plugins/link.min.js',
												'./public/vendor/froala/2.3.4/js/plugins/lists.min.js', './public/vendor/froala/2.3.4/js/plugins/paragraph_format.min.js', './public/vendor/froala/2.3.4/js/plugins/paragraph_style.min.js', './public/vendor/froala/2.3.4/js/plugins/quote.min.js', './public/vendor/froala/2.3.4/js/plugins/save.min.js', './public/vendor/froala/2.3.4/js/plugins/table.min.js', './public/vendor/froala/2.3.4/js/plugins/video.min.js', './public/vendor/froala/2.3.4/angular-froala/angular-froala.js', './public/vendor/froala/2.3.4/js/plugins/image_manager.min.js' ]).then(function() {
									// element[0].attributes['froala'] = froalaConfig;
									attrs.froala = froalaConfig;
								});
							});
				}
			}

		} ]);