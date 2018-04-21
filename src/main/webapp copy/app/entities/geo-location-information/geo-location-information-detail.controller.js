(function() {
    'use strict';

    angular
        .module('rearviewFinal1App')
        .controller('GeoLocationInformationDetailController', GeoLocationInformationDetailController);

    GeoLocationInformationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GeoLocationInformation'];

    function GeoLocationInformationDetailController($scope, $rootScope, $stateParams, previousState, entity, GeoLocationInformation) {
        var vm = this;

        vm.geoLocationInformation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rearviewFinal1App:geoLocationInformationUpdate', function(event, result) {
            vm.geoLocationInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
