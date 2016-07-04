(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('BlogStatusDetailController', BlogStatusDetailController);

    BlogStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'BlogStatus', 'User'];

    function BlogStatusDetailController($scope, $rootScope, $stateParams, entity, BlogStatus, User) {
        var vm = this;

        vm.blogStatus = entity;

        var unsubscribe = $rootScope.$on('yoUVcodeApp:blogStatusUpdate', function(event, result) {
            vm.blogStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
