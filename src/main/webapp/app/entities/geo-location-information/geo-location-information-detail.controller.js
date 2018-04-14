(function() {
    'use strict';

    angular
        .module('rearviewSandiegoApp')
        .controller('GeoLocationInformationDetailController', GeoLocationInformationDetailController);

    GeoLocationInformationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GeoLocationInformation'];

    function GeoLocationInformationDetailController($scope, $rootScope, $stateParams, previousState, entity, GeoLocationInformation) {
        var vm = this;

        vm.geoLocationInformation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rearviewSandiegoApp:geoLocationInformationUpdate', function(event, result) {
            vm.geoLocationInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
