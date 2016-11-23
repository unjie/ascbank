// 主控制器 实现
app.controller('AppCtrl', [ '$scope', '$window', '$cookies', '$state', function($scope, $window, $cookies , $state) {
	// add 'ie' classes to html
	var u=$cookies.getObject('user'), isIE = !!navigator.userAgent.match(/MSIE/i);
	isIE && angular.element($window.document.body).addClass('ie');
	isSmartDevice($window) && angular.element($window.document.body).addClass('smart');
	
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
		},
		user:u
	}
	
	if(!$scope.app.user){
		location.href='./index.html#/access/signin';
	}
	
	// angular translate
	$scope.lang = {
		isopen : false
	};
	$scope.langs = {
		en : 'English',
		cn_ZH : '中文',
//		de_DE : 'German',
//		it_IT : 'Italian'
	};
	$scope.selectLang = "English";
	$scope.setLang = function(langKey, $event) {
		// 设置当前的
		$scope.selectLang = $scope.langs[langKey];
		// 你可以在运行时更改语言
		$scope.lang.isopen = !$scope.lang.isopen;
	};

	function isSmartDevice($window) {
		// Adapted from
		// http://www.detectmobilebrowsers.com
		var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
		// 检查 iOs, Android, Blackberry, Opera
		// 迷你，和窗户的移动设备
		return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
	}

} ]);