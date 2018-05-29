(function() {
    'use strict';

    angular
        .module('sscappApp')
        .controller('GeoLocationInformationDeleteController',GeoLocationInformationDeleteController);

    GeoLocationInformationDeleteController.$inject = ['$uibModalInstance', 'entity', 'GeoLocationInformation'];

    function GeoLocationInformationDeleteController($uibModalInstance, entity, GeoLocationInformation) {
        var vm = this;

        vm.geoLocationInformation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GeoLocationInformation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
