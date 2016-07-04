(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('CATEGORYDetailController', CATEGORYDetailController);

    CATEGORYDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CATEGORY', 'Jhi_user'];

    function CATEGORYDetailController($scope, $rootScope, $stateParams, entity, CATEGORY, Jhi_user) {
        var vm = this;

        vm.cATEGORY = entity;

        var unsubscribe = $rootScope.$on('yoUVcodeApp:cATEGORYUpdate', function(event, result) {
            vm.cATEGORY = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
