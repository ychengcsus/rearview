(function() {
    'use strict';

    angular
        .module('rearviewSandiegoApp')
        .controller('AssetsDetailController', AssetsDetailController);

    AssetsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Assets'];

    function AssetsDetailController($scope, $rootScope, $stateParams, previousState, entity, Assets) {
        var vm = this;

        vm.assets = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rearviewSandiegoApp:assetsUpdate', function(event, result) {
            vm.assets = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
