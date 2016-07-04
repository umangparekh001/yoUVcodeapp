(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sub-category', {
            parent: 'entity',
            url: '/sub-category?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'yoUVcodeApp.subCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sub-category/sub-categories.html',
                    controller: 'SubCategoryController',
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
                    $translatePartialLoader.addPart('subCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sub-category-detail', {
            parent: 'entity',
            url: '/sub-category/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'yoUVcodeApp.subCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sub-category/sub-category-detail.html',
                    controller: 'SubCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SubCategory', function($stateParams, SubCategory) {
                    return SubCategory.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('sub-category.new', {
            parent: 'sub-category',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sub-category/sub-category-dialog.html',
                    controller: 'SubCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sub_category_title: null,
                                active_ind: false,
                                created_date: null,
                                last_chng_date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sub-category', null, { reload: true });
                }, function() {
                    $state.go('sub-category');
                });
            }]
        })
        .state('sub-category.edit', {
            parent: 'sub-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sub-category/sub-category-dialog.html',
                    controller: 'SubCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubCategory', function(SubCategory) {
                            return SubCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sub-category', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sub-category.delete', {
            parent: 'sub-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sub-category/sub-category-delete-dialog.html',
                    controller: 'SubCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SubCategory', function(SubCategory) {
                            return SubCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sub-category', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
