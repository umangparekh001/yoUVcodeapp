(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('BlogTagsDialogController', BlogTagsDialogController);

    BlogTagsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BlogTags', 'Blog'];

    function BlogTagsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BlogTags, Blog) {
        var vm = this;

        vm.blogTags = entity;
        vm.clear = clear;
        vm.save = save;
        vm.blogs = Blog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.blogTags.id !== null) {
                BlogTags.update(vm.blogTags, onSaveSuccess, onSaveError);
            } else {
                BlogTags.save(vm.blogTags, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('yoUVcodeApp:blogTagsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
