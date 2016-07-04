(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('blog-tags', {
            parent: 'entity',
            url: '/blog-tags?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'yoUVcodeApp.blogTags.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/blog-tags/blog-tags.html',
                    controller: 'BlogTagsController',
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
                    $translatePartialLoader.addPart('blogTags');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('blog-tags-detail', {
            parent: 'entity',
            url: '/blog-tags/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'yoUVcodeApp.blogTags.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/blog-tags/blog-tags-detail.html',
                    controller: 'BlogTagsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('blogTags');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BlogTags', function($stateParams, BlogTags) {
                    return BlogTags.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('blog-tags.new', {
            parent: 'blog-tags',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-tags/blog-tags-dialog.html',
                    controller: 'BlogTagsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tags: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('blog-tags', null, { reload: true });
                }, function() {
                    $state.go('blog-tags');
                });
            }]
        })
        .state('blog-tags.edit', {
            parent: 'blog-tags',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-tags/blog-tags-dialog.html',
                    controller: 'BlogTagsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BlogTags', function(BlogTags) {
                            return BlogTags.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('blog-tags', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('blog-tags.delete', {
            parent: 'blog-tags',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-tags/blog-tags-delete-dialog.html',
                    controller: 'BlogTagsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BlogTags', function(BlogTags) {
                            return BlogTags.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('blog-tags', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
