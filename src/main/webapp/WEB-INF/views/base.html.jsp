<!DOCTYPE html>
<html lang="en" data-ng-app="app">
<head>
  <meta charset="utf-8" /><jsp:include page="./debris/jspVar.jsp" />
  <title>Be Angular | Bootstrap Admin Web App with AngularJS</title>
  <meta name="description" content="app, web app, responsive, responsive layout, admin, admin panel, admin dashboard, flat, flat ui, ui kit, AngularJS, ui route, charts, widgets, components" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <base href="${__ROOT__}/">
  <link rel="stylesheet" href="css/app.min.css" type="text/css" />
</head>
<body ng-controller="AppCtrl">
	<div class="app" id="app" ng-class="{'app-header-fixed':app.settings.headerFixed, 'app-aside-fixed':app.settings.asideFixed, 'app-aside-folded':app.settings.asideFolded, 'app-aside-dock':app.settings.asideDock, 'container':app.settings.container}"  ui-view></div>
	<!-- jQuery -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<!-- Angular -->
	<script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
	<script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
	<script src="//cdn.bootcss.com/angular-ui-router/1.0.0-beta.1/angular-ui-router.min.js"></script>
  	<script src="//cdn.bootcss.com/oclazyload/1.0.9/ocLazyLoad.min.js"></script>
	<!-- <script src="./js/services/ui-load.js"></script>  -->
	
 
	<script type="text/javascript">
		var app=angular.module('app', [ 'ui.router' ,'oc.lazyLoad' ]);//, 'ui.load' ]);
		app.run(['$rootScope', '$state', '$stateParams',
	      function ($rootScope,   $state,   $stateParams) {
	          $rootScope.$state = $state;
	          $rootScope.$stateParams = $stateParams;        
	      }
	    ])
	    .config(["$provide","$compileProvider","$controllerProvider","$filterProvider",
                function($provide,$compileProvider,$controllerProvider,$filterProvider){
			    	app.controller = $controllerProvider.register;
			    	app.directive = $compileProvider.directive;
			    	app.filter = $filterProvider.register;
			    	app.factory = $provide.factory;
			    	app.service  =$provide.service;
			    	app.constant = $provide.constant;
                }])
		.config(function ($stateProvider, $urlRouterProvider) {
		     $urlRouterProvider.otherwise('/access/signin');
		     
		     $stateProvider 
		     .state('access', {
                 url: '/access',
                 template: '<div ui-view class="fade-in-right-big smooth"></div>'
             })
             .state('access.signin', {
                 url: '/signin',
                 templateUrl: './user/login.html',
               	 resolve: {
                	 deps: ['$ocLazyLoad',
                       function( $ocLazyLoad ){
                         return $ocLazyLoad.load( ['js/controllers/signin.js'] );
                     }] 
                 }
             })
             .state('access.signup', {
                 url: '/signup',
                 templateUrl: './user/register.html',
                 resolve: {
                	 deps: ['$ocLazyLoad',
                       function( $ocLazyLoad ){
                         return $ocLazyLoad.load( ['js/controllers/signup.js'] );
                     }] 
                 }
             })
            .state('access.forgotpwd', {
                url: '/forgotpwd',
                templateUrl: './user/forgotpwd.html'
            })
            .state('access.404', {
                  url: '/404',
                  templateUrl: 'tpl/page_404.html'
              })
              
		})

	.controller('AppCtrl', ['$scope','$window',
	    function( $scope ,$window) {
	      // add 'ie' classes to html
	      var isIE = !!navigator.userAgent.match(/MSIE/i);
	      isIE && angular.element($window.document.body).addClass('ie');
	      isSmartDevice( $window ) && angular.element($window.document.body).addClass('smart');
	
	      // config
	      $scope.app = {
	        name: 'Angulr',
	        version: '1.3.3',
	        // for chart colors
	        color: {
	          primary: '#7266ba',
	          info:    '#23b7e5',
	          success: '#27c24c',
	          warning: '#fad733',
	          danger:  '#f05050',
	          light:   '#e8eff0',
	          dark:    '#3a3f51',
	          black:   '#1c2b36'
	        },
	        settings: {
	          themeID: 1,
	          navbarHeaderColor: 'bg-black',
	          navbarCollapseColor: 'bg-white-only',
	          asideColor: 'bg-black',
	          headerFixed: true,
	          asideFixed: false,
	          asideFolded: false,
	          asideDock: false,
	          container: false
	        }
	      }
	
	  
	      // angular translate
	      $scope.lang = { isopen: false };
	      $scope.langs = {en:'English', de_DE:'German', it_IT:'Italian'};
	      $scope.selectLang = "English";
	      $scope.setLang = function(langKey, $event) {
	        // set the current lang
	        $scope.selectLang = $scope.langs[langKey];
	        // You can change the language during runtime
	        
	        $scope.lang.isopen = !$scope.lang.isopen;
	      };
	
	      function isSmartDevice( $window )
	      {
	          // Adapted from http://www.detectmobilebrowsers.com
	          var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
	          // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
	          return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
	      }
	
	  }])
		;
	</script>

	<!-- 
  <script src="vendor/angular/angular-animate/angular-animate.js"></script>
  <script src="vendor/angular/angular-cookies/angular-cookies.js"></script>
  <script src="vendor/angular/angular-resource/angular-resource.js"></script>
  <script src="vendor/angular/angular-sanitize/angular-sanitize.js"></script>
  <script src="vendor/angular/angular-touch/angular-touch.js"></script>
  Vendor
  <script src="vendor/angular/angular-ui-router/angular-ui-router.js"></script> 
  <script src="vendor/angular/ngstorage/ngStorage.js"></script>
  bootstrap
  <script src="vendor/angular/angular-bootstrap/ui-bootstrap-tpls.js"></script>
  lazyload
  <script src="vendor/angular/oclazyload/ocLazyLoad.js"></script>
    translate
  <script src="vendor/angular/angular-translate/angular-translate.js"></script>
  <script src="vendor/angular/angular-translate/loader-static-files.js"></script>
  <script src="vendor/angular/angular-translate/storage-cookie.js"></script>
  <script src="vendor/angular/angular-translate/storage-local.js"></script>
  
  <script src="js/app.min.js"></script>
  

  <script src="js/services/ui-load.js"></script>
  <script src="js/filters/fromNow.js"></script>
  <script src="js/directives/setnganimate.js"></script>
  <script src="js/directives/ui-butterbar.js"></script>
  <script src="js/directives/ui-focus.js"></script>
  <script src="js/directives/ui-fullscreen.js"></script>
  <script src="js/directives/ui-jq.js"></script>
  <script src="js/directives/ui-module.js"></script>
  <script src="js/directives/ui-nav.js"></script>
  <script src="js/directives/ui-scroll.js"></script>
  <script src="js/directives/ui-shift.js"></script>
  <script src="js/directives/ui-toggleclass.js"></script>
  <script src="js/directives/ui-validate.js"></script> 

  <script src="js/controllers/bootstrap.js"></script> 
  -->
	<!-- Lazy loading -->
</body>
</html>