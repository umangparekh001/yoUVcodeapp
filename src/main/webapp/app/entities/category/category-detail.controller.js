(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('CategoryDetailController', CategoryDetailController);

    CategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Category', 'User'];

    function CategoryDetailController($scope, $rootScope, $stateParams, entity, Category, User) {
        var vm = this;

        vm.category = entity;

        var unsubscribe = $rootScope.$on('yoUVcodeApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
