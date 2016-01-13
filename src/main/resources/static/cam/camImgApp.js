angular.module('CamImgApp', ['ngRoute'])
    .controller('MainController', function ($scope, $route, $routeParams, $location) {
        $scope.$route = $route;
        $scope.$location = $location;
        $scope.$routeParams = $routeParams;
    })
    .controller('CamdirsController', ['$scope', '$http', CamdirsController])
    .controller('CamviewController', ['$scope', '$http', '$routeParams', '$interval', '$filter', CamviewController])
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

function CamviewController($scope, $http, $routeParams, $interval, $filter) {
    //$scope.name = "CamviewController";
    $scope.params = $routeParams;
    var sliderInterval;

    function filterImagesAndSort(camfiles) {
        var imageFiles = $filter('filter')(camfiles, '.jpg');
        return $filter('orderBy')(imageFiles, '+');
    }

    $http({method: 'GET', url: '/cam1/list/' + $scope.params.dir}).
    then(function (response) {
        $scope.status = response.status;
        $scope.camfiles = response.data[$scope.params.dir];
        $scope.camimages = filterImagesAndSort($scope.camfiles);
        $scope.startSlider();
    }, function (response) {
        $scope.camfiles = response.data || "Request failed";
        $scope.status = response.status;
    });

    $scope.currentIndex = 0;

    $scope.setCurrentSlideIndex = function (index) {
        $scope.currentIndex = index;
    };

    $scope.isCurrentSlideIndex = function (index) {
        return $scope.currentIndex === index;
    };

    $scope.prevSlide = function () {
        $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.camimages.length - 1;
    };

    $scope.nextSlide = function () {
        $scope.currentIndex = ($scope.currentIndex < $scope.camimages.length - 1) ? ++$scope.currentIndex : 0;
    };


    $scope.stopSlider = function () {
        if (angular.isDefined(sliderInterval)) {
            $interval.cancel(sliderInterval);
            sliderInterval = undefined;
        }
    };

    $scope.startSlider = function () {
        if (!angular.isDefined(sliderInterval)) {
            sliderInterval = $interval(function () {
                $scope.nextSlide();
            }, 100);
        }
    };

    $scope.isSliderStarted = function () {
        return angular.isDefined(sliderInterval);
    };

    $scope.$on('$destroy', function () {
        // Make sure that the interval is destroyed too
        $scope.stopSlider();
    });
}