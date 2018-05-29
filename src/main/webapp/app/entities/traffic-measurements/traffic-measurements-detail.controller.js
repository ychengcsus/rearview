(function() {
    'use strict';

    angular
        .module('sscappApp')
        .controller('TrafficMeasurementsDetailController', TrafficMeasurementsDetailController);

    TrafficMeasurementsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TrafficMeasurements'];

    function TrafficMeasurementsDetailController($scope, $rootScope, $stateParams, previousState, entity, TrafficMeasurements) {
        var vm = this;

        vm.trafficMeasurements = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sscappApp:trafficMeasurementsUpdate', function(event, result) {
            vm.trafficMeasurements = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
