(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('BlogStatusDialogController', BlogStatusDialogController);

    BlogStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BlogStatus', 'User'];

    function BlogStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BlogStatus, User) {
        var vm = this;

        vm.blogStatus = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.blogStatus.id !== null) {
                BlogStatus.update(vm.blogStatus, onSaveSuccess, onSaveError);
            } else {
                BlogStatus.save(vm.blogStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('yoUVcodeApp:blogStatusUpdate', result);
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
