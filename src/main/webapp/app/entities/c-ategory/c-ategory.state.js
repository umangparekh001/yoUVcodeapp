(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-ategory', {
            parent: 'entity',
            url: '/c-ategory?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'yoUVcodeApp.cATEGORY.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-ategory/c-ategories.html',
                    controller: 'CATEGORYController',
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
                    $translatePartialLoader.addPart('cATEGORY');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-ategory-detail', {
            parent: 'entity',
            url: '/c-ategory/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'yoUVcodeApp.cATEGORY.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-ategory/c-ategory-detail.html',
                    controller: 'CATEGORYDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cATEGORY');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CATEGORY', function($stateParams, CATEGORY) {
                    return CATEGORY.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('c-ategory.new', {
            parent: 'c-ategory',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-ategory/c-ategory-dialog.html',
                    controller: 'CATEGORYDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                category_title: null,
                                active_ind: false,
                                created_date: null,
                                last_chng_date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-ategory', null, { reload: true });
                }, function() {
                    $state.go('c-ategory');
                });
            }]
        })
        .state('c-ategory.edit', {
            parent: 'c-ategory',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-ategory/c-ategory-dialog.html',
                    controller: 'CATEGORYDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CATEGORY', function(CATEGORY) {
                            return CATEGORY.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-ategory', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-ategory.delete', {
            parent: 'c-ategory',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-ategory/c-ategory-delete-dialog.html',
                    controller: 'CATEGORYDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CATEGORY', function(CATEGORY) {
                            return CATEGORY.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-ategory', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
