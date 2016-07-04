(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('SubCategoryDialogController', SubCategoryDialogController);

    SubCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubCategory', 'Category', 'User'];

    function SubCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SubCategory, Category, User) {
        var vm = this;

        vm.subCategory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.categories = Category.query();
        vm.users = User.query();
        vm.last_chng_bies = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subCategory.id !== null) {
                SubCategory.update(vm.subCategory, onSaveSuccess, onSaveError);
            } else {
                SubCategory.save(vm.subCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('yoUVcodeApp:subCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.created_date = false;
        vm.datePickerOpenStatus.last_chng_date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
