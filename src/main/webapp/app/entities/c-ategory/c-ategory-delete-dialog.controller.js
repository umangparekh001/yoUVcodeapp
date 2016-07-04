(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('CATEGORYDeleteController',CATEGORYDeleteController);

    CATEGORYDeleteController.$inject = ['$uibModalInstance', 'entity', 'CATEGORY'];

    function CATEGORYDeleteController($uibModalInstance, entity, CATEGORY) {
        var vm = this;

        vm.cATEGORY = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CATEGORY.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
