angular.module('CamApp', ['ngRoute'])
    .controller('MainController', function ($scope, $route, $routeParams, $location) {
        $scope.$route = $route;
        $scope.$location = $location;
        $scope.$routeParams = $routeParams;
    })
    .controller('CamdirsController', ['$scope', '$http', CamdirsController])
    .controller('CamviewController', function ($scope, $http, $routeParams) {
        $scope.name = "CamviewController";
        $scope.params = $routeParams;
        $http({method: 'GET', url: '/cam1/list/' + $scope.params.dir}).
        then(function (response) {
            $scope.status = response.status;
            $scope.camfiles = response.data[$scope.params.dir];
        }, function (response) {
            $scope.camfiles = response.data || "Request failed";
            $scope.status = response.status;
        });
    })
    .config(function ($routeProvider, $locationProvider) {
        $routeProvider
            .when('/camview/:dir', {
                templateUrl: 'camview.html',
                controller: 'CamviewController',
                resolve: {
                    // I will cause a 1 second delay
                    delay: function ($q, $timeout) {
                        var delay = $q.defer();
                        $timeout(delay.resolve, 1000);
                        return delay.promise;
                    }
                }
            });
        // configure html5 to get links working on jsfiddle
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
    });

function CamdirsController($scope, $http) {
    //$http.get('/cam1/listdirs').
    //success(function(data) {
    //    $scope.camdirs = data;
    //});

    $http({method: 'GET', url: '/cam1/listdirs'}).
    then(function (response) {
        $scope.status = response.status;
        $scope.camdirs = response.data;
    }, function (response) {
        $scope.camdirs = response.data || "Request failed";
        $scope.status = response.status;
    });
}
