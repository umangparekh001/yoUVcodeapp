(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('SubCategoryDetailController', SubCategoryDetailController);

    SubCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SubCategory', 'Category', 'User'];

    function SubCategoryDetailController($scope, $rootScope, $stateParams, entity, SubCategory, Category, User) {
        var vm = this;

        vm.subCategory = entity;

        var unsubscribe = $rootScope.$on('yoUVcodeApp:subCategoryUpdate', function(event, result) {
            vm.subCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
