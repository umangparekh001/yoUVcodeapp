(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('BlogStatusDeleteController',BlogStatusDeleteController);

    BlogStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'BlogStatus'];

    function BlogStatusDeleteController($uibModalInstance, entity, BlogStatus) {
        var vm = this;

        vm.blogStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BlogStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
