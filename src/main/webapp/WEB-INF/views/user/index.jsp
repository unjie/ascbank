<!DOCTYPE html>
<html lang="en" data-ng-app="app">
<head>
<meta charset="utf-8" /><jsp:include page="../debris/jspVar.jsp" />
<title>Be Angular | Bootstrap Admin Web App with AngularJS</title>
<meta name="description" content="app, web app, responsive, responsive layout, admin, admin panel, admin dashboard, flat, flat ui, ui kit, AngularJS, ui route, charts, widgets, components" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<base href="${__ROOT__}/">
<link rel="stylesheet" href="css/app.min.css" type="text/css" />
</head>
<body ng-controller="AppCtrl">
	<div class="app" id="app" ng-class="{'app-header-fixed':app.settings.headerFixed, 'app-aside-fixed':app.settings.asideFixed, 'app-aside-folded':app.settings.asideFolded, 'app-aside-dock':app.settings.asideDock, 'container':app.settings.container}" ui-view></div>
	<!-- jQuery -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<!-- Angular -->
	<script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
	<script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
	<script src="//cdn.bootcss.com/angular-ui-router/1.0.0-beta.1/angular-ui-router.min.js"></script>
	<script src="//cdn.bootcss.com/oclazyload/1.0.9/ocLazyLoad.min.js"></script>
	<!-- <script src="./js/services/ui-load.js"></script>  -->
	<script type="text/javascript">
		var app = angular.module('app', [ 'ui.router', 'oc.lazyLoad' ]);//, 'ui.load' ]);
		app.run(
						[ '$rootScope', '$state', '$stateParams',
								function($rootScope, $state, $stateParams) {
									$rootScope.$state = $state;
									$rootScope.$stateParams = $stateParams;
								} ])
				.config(
						[
								"$provide",
								"$compileProvider",
								"$controllerProvider",
								"$filterProvider",
								function($provide, $compileProvider,
										$controllerProvider, $filterProvider) {
									app.controller = $controllerProvider.register;
									app.directive = $compileProvider.directive;
									app.filter = $filterProvider.register;
									app.factory = $provide.factory;
									app.service = $provide.service;
									app.constant = $provide.constant;
								} ])
				.config(
						function($stateProvider, $urlRouterProvider) {
							$urlRouterProvider.otherwise('/access/signin');

							$stateProvider
									.state(
											'access',
											{
												url : '/access',
												template : '<div ui-view class="fade-in-right-big smooth"></div>'
											})
									.state(
											'access.signin',
											{
												url : '/signin',
												templateUrl : './user/login.html',
												resolve : {
													deps : [
															'$ocLazyLoad',
															function(
																	$ocLazyLoad) {
																return $ocLazyLoad
																		.load([ '/public/js/controllers/signin.js' ]);
															} ]
												}
											})
									.state(
											'access.signup',
											{
												url : '/signup',
												templateUrl : './user/register.html',
												resolve : {
													deps : [
															'$ocLazyLoad',
															function(
																	$ocLazyLoad) {
																return $ocLazyLoad
																		.load([ '/public/js/controllers/signup.js' ]);
															} ]
												}
											})
									.state('access.forgotpwd', {
										url : '/forgotpwd',
										templateUrl : './user/forgotpwd.html'
									})
									.state(
											'access.404',
											{
												url : '/404',
												templateUrl : '/public/tpl/page_404.html'
											})

						})

				.controller(
						'AppCtrl',
						[
								'$scope',
								'$window',
								function($scope, $window) {
									// add 'ie' classes to html
									var isIE = !!navigator.userAgent
											.match(/MSIE/i);
									isIE
											&& angular.element(
													$window.document.body)
													.addClass('ie');
									isSmartDevice($window)
											&& angular.element(
													$window.document.body)
													.addClass('smart');

									// config
									$scope.app = {
										name : 'Angulr',
										version : '1.3.3',
										// for chart colors
										color : {
											primary : '#7266ba',
											info : '#23b7e5',
											success : '#27c24c',
											warning : '#fad733',
											danger : '#f05050',
											light : '#e8eff0',
											dark : '#3a3f51',
											black : '#1c2b36'
										},
										settings : {
											themeID : 1,
											navbarHeaderColor : 'bg-black',
											navbarCollapseColor : 'bg-white-only',
											asideColor : 'bg-black',
											headerFixed : true,
											asideFixed : false,
											asideFolded : false,
											asideDock : false,
											container : false
										}
									}

									// angular translate
									$scope.lang = {
										isopen : false
									};
									$scope.langs = {
										en : 'English',
										de_DE : 'German',
										it_IT : 'Italian'
									};
									$scope.selectLang = "English";
									$scope.setLang = function(langKey, $event) {
										// set the current lang
										$scope.selectLang = $scope.langs[langKey];
										// You can change the language during runtime

										$scope.lang.isopen = !$scope.lang.isopen;
									};

									function isSmartDevice($window) {
										// Adapted from http://www.detectmobilebrowsers.com
										var ua = $window['navigator']['userAgent']
												|| $window['navigator']['vendor']
												|| $window['opera'];
										// Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
										return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/)
												.test(ua);
									}

								} ]);
	</script>
</body>
</html>