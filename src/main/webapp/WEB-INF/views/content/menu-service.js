// A RESTful factory for retreiving menus from 'menus.json'
app.factory('menus', ['$http', function ($http) {
  var path = './menu/reads';
  var menus = $http.get(path).then(function (resp) {
    return resp.data;
  });

  var factory = {};
  factory.all = function () {
    return menus;
  };
  factory.get = function (id) {
    return menus.then(function(menus){
      for (var i = 0; i < menus.length; i++) {
        if (menus[i].id == id) return menus[i];
      }
      return null;
    })
  };
  return factory;
}]);