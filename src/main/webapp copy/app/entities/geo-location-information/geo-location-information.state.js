(function() {
    'use strict';

    angular
        .module('rearviewFinal1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('geo-location-information', {
            parent: 'entity',
            url: '/geo-location-information?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GeoLocationInformations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/geo-location-information/geo-location-informations.html',
                    controller: 'GeoLocationInformationController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('geo-location-information-detail', {
            parent: 'geo-location-information',
            url: '/geo-location-information/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GeoLocationInformation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/geo-location-information/geo-location-information-detail.html',
                    controller: 'GeoLocationInformationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GeoLocationInformation', function($stateParams, GeoLocationInformation) {
                    return GeoLocationInformation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'geo-location-information',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('geo-location-information-detail.edit', {
            parent: 'geo-location-information-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/geo-location-information/geo-location-information-dialog.html',
                    controller: 'GeoLocationInformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GeoLocationInformation', function(GeoLocationInformation) {
                            return GeoLocationInformation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('geo-location-information.new', {
            parent: 'geo-location-information',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/geo-location-information/geo-location-information-dialog.html',
                    controller: 'GeoLocationInformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                locationUuid: null,
                                locationType: null,
                                parentLocationUuid: null,
                                coordinatesType: null,
                                coordinates: null,
                                city: null,
                                state: null,
                                country: null,
                                zipcode: null,
                                timezone: null,
                                address: null,
                                analyticCategory: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('geo-location-information', null, { reload: 'geo-location-information' });
                }, function() {
                    $state.go('geo-location-information');
                });
            }]
        })
        .state('geo-location-information.edit', {
            parent: 'geo-location-information',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/geo-location-information/geo-location-information-dialog.html',
                    controller: 'GeoLocationInformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GeoLocationInformation', function(GeoLocationInformation) {
                            return GeoLocationInformation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('geo-location-information', null, { reload: 'geo-location-information' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('geo-location-information.delete', {
            parent: 'geo-location-information',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/geo-location-information/geo-location-information-delete-dialog.html',
                    controller: 'GeoLocationInformationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GeoLocationInformation', function(GeoLocationInformation) {
                            return GeoLocationInformation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('geo-location-information', null, { reload: 'geo-location-information' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
