(function() {
    'use strict';

    angular
        .module('rearviewFinal1App')
        .controller('TrafficMeasurementsDialogController', TrafficMeasurementsDialogController);

    TrafficMeasurementsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TrafficMeasurements'];

    function TrafficMeasurementsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TrafficMeasurements) {
        var vm = this;

        vm.trafficMeasurements = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.trafficMeasurements.id !== null) {
                TrafficMeasurements.update(vm.trafficMeasurements, onSaveSuccess, onSaveError);
            } else {
                TrafficMeasurements.save(vm.trafficMeasurements, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rearviewFinal1App:trafficMeasurementsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.endTime = false;
        vm.datePickerOpenStatus.timestamp = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
