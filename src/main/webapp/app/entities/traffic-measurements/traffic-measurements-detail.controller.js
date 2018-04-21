(function() {
    'use strict';

    angular
        .module('rearviewFinal1App')
        .controller('TrafficMeasurementsDetailController', TrafficMeasurementsDetailController);

    TrafficMeasurementsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TrafficMeasurements'];

    function TrafficMeasurementsDetailController($scope, $rootScope, $stateParams, previousState, entity, TrafficMeasurements) {
        var vm = this;

        vm.trafficMeasurements = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rearviewFinal1App:trafficMeasurementsUpdate', function(event, result) {
            vm.trafficMeasurements = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
