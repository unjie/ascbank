var app = angular.module('app', [    'ngAnimate',
                                      'ngCookies',
                                      'ngResource',
                                      'ngSanitize',
                                      'ngTouch',
                                      'ngStorage',
                                      'ui.router',
                                      'ui.bootstrap']);

/*app.config(['$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
            function ($controllerProvider,   $compileProvider,   $filterProvider,   $provide) {
                
                // lazy controller, directive and service
               app.controller = $controllerProvider.register;
                app.directive  = $compileProvider.directive;
                app.filter     = $filterProvider.register;
                app.factory    = $provide.factory;
                app.service    = $provide.service;
                app.constant   = $provide.constant;
                app.value      = $provide.value;
            }
          ])
          .config(['$translateProvider', function($translateProvider){
            // Register a loader for the static files
            // So, the module will search missing translation tables under the specified urls.
            // Those urls are [prefix][langKey][suffix].
            $translateProvider.useStaticFilesLoader({
              prefix: 'public/l10n/',
              suffix: '.js'
            });
            // Tell the module what language to use by default
            $translateProvider.preferredLanguage('en');
            // Tell the module to store the language in the local storage
            $translateProvider.useLocalStorage();
		}])*/




app.controller('MainCtrl', function($scope) {
	
	
	
})

.controller('TypeaheadDemoCtrl', ['$scope', '$http', function($scope, $http) {
	    $scope.selected = undefined;
	    $scope.states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Dakota', 'North Carolina', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'];
	    // Any function returning a promise object can be used to load values asynchronously
	    $scope.getLocation = function(val) {
	      return $http.get('http://maps.googleapis.com/maps/api/geocode/json', {
	        params: {
	          address: val,
	          sensor: false
	        }
	      }).then(function(res){
	        var addresses = [];
	        angular.forEach(res.data.results, function(item){
	          addresses.push(item.formatted_address);
	        });
	        return addresses;
	      });
	    };
}])

