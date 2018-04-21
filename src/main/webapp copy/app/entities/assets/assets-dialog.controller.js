(function() {
    'use strict';

    angular
        .module('rearviewFinal1App')
        .controller('AssetsDialogController', AssetsDialogController);

    AssetsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Assets'];

    function AssetsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Assets) {
        var vm = this;

        vm.assets = entity;
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
            if (vm.assets.id !== null) {
                Assets.update(vm.assets, onSaveSuccess, onSaveError);
            } else {
                Assets.save(vm.assets, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rearviewFinal1App:assetsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.assetCreationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
