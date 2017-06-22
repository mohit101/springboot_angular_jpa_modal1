
var app = angular.module('auth', ['ui.router', 'ngCookies']);
app.run(['$rootScope', '$cookies',
    function($rootScope, $cookies) {
        $rootScope.email = $cookies.get("emailIdISession");
        $rootScope.username = $cookies.get("usernameSession");
        console.log($rootScope.email);
        console.log($rootScope.username);

    }]);
app.config(function($stateProvider, $urlRouterProvider, $httpProvider) {
    $urlRouterProvider.otherwise("/index");
    $stateProvider
            .state('index', {
                title: "index",
                url: '/index',
                views: {
                    '':
                            {
                                templateUrl: 'htmlpages/login.html',
                                controller: 'registerCtrl'
                            }
                }
            })
            .state('home', {
                title: "home",
                url: '/home',
                views: {
                    '':
                            {
                                templateUrl: 'htmlpages/home.html',
                                controller: 'registerCtrl'
                            }
                }
            })
            .state('dashboard', {
                title: "dashboard",
                url: '/dashboard',
                views: {
                    '':
                            {
                                templateUrl: 'htmlpages/homepage2.html',
                                controller: 'registerCtrl'
                            }
                }
            })
            .state('register', {
                title: "register",
                url: '/register',
                views: {
                    '':
                            {
                                templateUrl: 'htmlpages/register.html',
                                controller: 'registerCtrl'
                            }
                }
            })
    $httpProvider.interceptors.push(function($cookies, $rootScope, $location) {
        return {
            request: function(config) {
                if ($cookies.get("emailIdISession") !== null) {
                    console.log("exist");
                    var authToken = $cookies.get("emailIdISession");
                    console.log(authToken);
                    config.headers['X-AUTH-TOKEN'] = authToken;
                    return config;
//                    console.log(config.headers['X-AUTH-TOKEN']);
                }
            }

        }
    });
});
//----------------------------------------------------------------  
app.controller('registerCtrl', function($scope, $http, $window, $location, $rootScope, $cookies) {
    $scope.user = {};
    $scope.register = function() {
        $http.defaults.useXDomain = true;
        console.log("Success");
        console.log($scope.user.uname);
        var config = {
            headers: {
                'Content-Type': 'application/json;',
                'Access-Control-Allow-Origin': '*'
            }
        }
        $http.post('http://localhost:8089/SpringBootRestApi/api2/registerUser/', $scope.user, config)
                .then(
                        function(response) {
                            console.log("Success1");
                        },
                        function(response) {
                            console.log("Fail");
                        }
                );
    }

    $scope.login = function() {
        $http.defaults.useXDomain = true;
        console.log("Success");
        var config = {
            headers: {
                'Content-Type': 'application/json;',
                'Access-Control-Allow-Origin': '*'
            }
        }
        $http.post('http://localhost:8089/SpringBootRestApi/api2/login/', $scope.user, config)
                .then(
                        function(response) {
                            console.log("login success");
                            $rootScope.message = {};
                            $rootScope.message = response.data
                            $cookies.put('usernameSession', $scope.message.uname);
                            $cookies.put('emailIdISession', $scope.message.email);
                            $window.location.href = '#/home';
                        },
                        function(response) {
                            console.log("Fail");
                        }
                )
    };

    $scope.dashboard = function() {
        var config = {
            headers: {
                'Content-Type': 'application/json;',
                'Access-Control-Allow-Origin': '*'
            }
        }
        $http.get('http://localhost:8089/SpringBootRestApi/api2//getUser/', $scope.user, config)
                .then(
                        function(response) {
                            $window.location.href = '#/dashboard';
                            console.log("Success");
                        },
                        function(response) {
                            console.log("Fail");
                        }
                );

    }
    $scope.goToRegister = function() {
        $window.location.href = '#/register';
//        $window.location.href = '#/homepage2';

    }
    $scope.logout = function() {
        var cookies = $cookies.getAll();
        angular.forEach(cookies, function(v, k) {
            $cookies.remove(k);
        });
        $location.path("#/login");

    }
}

);