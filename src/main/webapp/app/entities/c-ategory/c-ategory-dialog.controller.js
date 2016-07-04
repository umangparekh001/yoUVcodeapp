(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .controller('CATEGORYDialogController', CATEGORYDialogController);

    CATEGORYDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CATEGORY', 'Jhi_user'];

    function CATEGORYDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CATEGORY, Jhi_user) {
        var vm = this;

        vm.cATEGORY = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.jhi_users = Jhi_user.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cATEGORY.id !== null) {
                CATEGORY.update(vm.cATEGORY, onSaveSuccess, onSaveError);
            } else {
                CATEGORY.save(vm.cATEGORY, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('yoUVcodeApp:cATEGORYUpdate', result);
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
