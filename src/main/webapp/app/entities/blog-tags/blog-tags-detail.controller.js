(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('BlogTagsDetailController', BlogTagsDetailController);

    BlogTagsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'BlogTags', 'Blog'];

    function BlogTagsDetailController($scope, $rootScope, $stateParams, entity, BlogTags, Blog) {
        var vm = this;

        vm.blogTags = entity;

        var unsubscribe = $rootScope.$on('yoUVcodeApp:blogTagsUpdate', function(event, result) {
            vm.blogTags = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
