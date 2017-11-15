(function() {
	var app = angular.module('index', []);
	
	app.controller('IndexController', function($scope, $http){
		var control = this;

		$scope.init = function(){
			$http.get('/hi').
	        then(function(response) {
	        	$scope.user = response.data;
	        });
		};
	});
})();