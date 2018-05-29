(function() {
    'use strict';

    angular
        .module('sscappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('assets', {
            parent: 'entity',
            url: '/assets?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sscappApp.assets.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/assets/assets.html',
                    controller: 'AssetsController',
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
                    $translatePartialLoader.addPart('assets');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('assets-detail', {
            parent: 'assets',
            url: '/assets/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sscappApp.assets.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/assets/assets-detail.html',
                    controller: 'AssetsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('assets');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Assets', function($stateParams, Assets) {
                    return Assets.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'assets',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('assets-detail.edit', {
            parent: 'assets-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assets/assets-dialog.html',
                    controller: 'AssetsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Assets', function(Assets) {
                            return Assets.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('assets.new', {
            parent: 'assets',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assets/assets-dialog.html',
                    controller: 'AssetsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                assetUuid: null,
                                description: null,
                                properties: null,
                                status: null,
                                assetType: null,
                                mediaType: null,
                                eventTypes: null,
                                coordinates: null,
                                parentAssetUuid: null,
                                assetCreationDate: null,
                                assetToLocationId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('assets', null, { reload: 'assets' });
                }, function() {
                    $state.go('assets');
                });
            }]
        })
        .state('assets.edit', {
            parent: 'assets',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assets/assets-dialog.html',
                    controller: 'AssetsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Assets', function(Assets) {
                            return Assets.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('assets', null, { reload: 'assets' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('assets.delete', {
            parent: 'assets',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assets/assets-delete-dialog.html',
                    controller: 'AssetsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Assets', function(Assets) {
                            return Assets.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('assets', null, { reload: 'assets' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
