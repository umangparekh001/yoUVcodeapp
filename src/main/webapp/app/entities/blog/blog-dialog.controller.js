(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('BlogDialogController', BlogDialogController);

    BlogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Blog', 'Category', 'SubCategory', 'BlogStatus', 'User'];

    function BlogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Blog, Category, SubCategory, BlogStatus, User) {
        var vm = this;

        vm.blog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.categories = Category.query();
        vm.subcategories = SubCategory.query();
        vm.blogstatuses = BlogStatus.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.blog.id !== null) {
                Blog.update(vm.blog, onSaveSuccess, onSaveError);
            } else {
                Blog.save(vm.blog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('yoUVcodeApp:blogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.publishDate = false;
        vm.datePickerOpenStatus.created_date = false;
        vm.datePickerOpenStatus.last_chng_date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
