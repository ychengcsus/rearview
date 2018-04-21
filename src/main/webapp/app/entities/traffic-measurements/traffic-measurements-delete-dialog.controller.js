(function() {
    'use strict';

    angular
        .module('rearviewFinal1App')
        .controller('TrafficMeasurementsDeleteController',TrafficMeasurementsDeleteController);

    TrafficMeasurementsDeleteController.$inject = ['$uibModalInstance', 'entity', 'TrafficMeasurements'];

    function TrafficMeasurementsDeleteController($uibModalInstance, entity, TrafficMeasurements) {
        var vm = this;

        vm.trafficMeasurements = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TrafficMeasurements.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
