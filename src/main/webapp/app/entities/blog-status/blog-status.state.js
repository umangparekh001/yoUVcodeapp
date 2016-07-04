(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('blog-status', {
            parent: 'entity',
            url: '/blog-status?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'yoUVcodeApp.blogStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/blog-status/blog-statuses.html',
                    controller: 'BlogStatusController',
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
                    $translatePartialLoader.addPart('blogStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('blog-status-detail', {
            parent: 'entity',
            url: '/blog-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'yoUVcodeApp.blogStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/blog-status/blog-status-detail.html',
                    controller: 'BlogStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('blogStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BlogStatus', function($stateParams, BlogStatus) {
                    return BlogStatus.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('blog-status.new', {
            parent: 'blog-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-status/blog-status-dialog.html',
                    controller: 'BlogStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                blog_status: null,
                                created_date: null,
                                last_chng_date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('blog-status', null, { reload: true });
                }, function() {
                    $state.go('blog-status');
                });
            }]
        })
        .state('blog-status.edit', {
            parent: 'blog-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-status/blog-status-dialog.html',
                    controller: 'BlogStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BlogStatus', function(BlogStatus) {
                            return BlogStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('blog-status', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('blog-status.delete', {
            parent: 'blog-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-status/blog-status-delete-dialog.html',
                    controller: 'BlogStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BlogStatus', function(BlogStatus) {
                            return BlogStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('blog-status', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
