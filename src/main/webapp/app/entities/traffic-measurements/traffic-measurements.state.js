(function() {
    'use strict';

    angular
        .module('sscappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('traffic-measurements', {
            parent: 'entity',
            url: '/traffic-measurements?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sscappApp.trafficMeasurements.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/traffic-measurements/traffic-measurements.html',
                    controller: 'TrafficMeasurementsController',
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trafficMeasurements');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('traffic-measurements-detail', {
            parent: 'traffic-measurements',
            url: '/traffic-measurements/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sscappApp.trafficMeasurements.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/traffic-measurements/traffic-measurements-detail.html',
                    controller: 'TrafficMeasurementsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trafficMeasurements');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TrafficMeasurements', function($stateParams, TrafficMeasurements) {
                    return TrafficMeasurements.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'traffic-measurements',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('traffic-measurements-detail.edit', {
            parent: 'traffic-measurements-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/traffic-measurements/traffic-measurements-dialog.html',
                    controller: 'TrafficMeasurementsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TrafficMeasurements', function(TrafficMeasurements) {
                            return TrafficMeasurements.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('traffic-measurements.new', {
            parent: 'traffic-measurements',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/traffic-measurements/traffic-measurements-dialog.html',
                    controller: 'TrafficMeasurementsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startTime: null,
                                end_time: null,
                                asset_uuid: null,
                                asset_description: null,
                                event_type: null,
                                counter_direction: null,
                                counter_direction_speed: null,
                                counter_direction_vehicle_count: null,
                                speed: null,
                                vehicle_count: null,
                                timestamp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('traffic-measurements', null, { reload: 'traffic-measurements' });
                }, function() {
                    $state.go('traffic-measurements');
                });
            }]
        })
        .state('traffic-measurements.edit', {
            parent: 'traffic-measurements',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/traffic-measurements/traffic-measurements-dialog.html',
                    controller: 'TrafficMeasurementsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TrafficMeasurements', function(TrafficMeasurements) {
                            return TrafficMeasurements.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('traffic-measurements', null, { reload: 'traffic-measurements' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('traffic-measurements.delete', {
            parent: 'traffic-measurements',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/traffic-measurements/traffic-measurements-delete-dialog.html',
                    controller: 'TrafficMeasurementsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TrafficMeasurements', function(TrafficMeasurements) {
                            return TrafficMeasurements.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('traffic-measurements', null, { reload: 'traffic-measurements' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
