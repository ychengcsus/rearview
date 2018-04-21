(function() {
    'use strict';

    angular
        .module('rearviewFinal1App')
        .controller('GeoLocationInformationDialogController', GeoLocationInformationDialogController);

    GeoLocationInformationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GeoLocationInformation'];

    function GeoLocationInformationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GeoLocationInformation) {
        var vm = this;

        vm.geoLocationInformation = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.geoLocationInformation.id !== null) {
                GeoLocationInformation.update(vm.geoLocationInformation, onSaveSuccess, onSaveError);
            } else {
                GeoLocationInformation.save(vm.geoLocationInformation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rearviewFinal1App:geoLocationInformationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
