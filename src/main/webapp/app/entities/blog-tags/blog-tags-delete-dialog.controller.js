(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('BlogTagsDeleteController',BlogTagsDeleteController);

    BlogTagsDeleteController.$inject = ['$uibModalInstance', 'entity', 'BlogTags'];

    function BlogTagsDeleteController($uibModalInstance, entity, BlogTags) {
        var vm = this;

        vm.blogTags = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BlogTags.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
