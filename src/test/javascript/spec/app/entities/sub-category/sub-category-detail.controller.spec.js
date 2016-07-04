'use strict';

describe('Controller Tests', function() {

    describe('SubCategory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubCategory, MockCategory, MockUser, MockLast_chng_by;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubCategory = jasmine.createSpy('MockSubCategory');
            MockCategory = jasmine.createSpy('MockCategory');
            MockUser = jasmine.createSpy('MockUser');
            MockLast_chng_by = jasmine.createSpy('MockLast_chng_by');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SubCategory': MockSubCategory,
                'Category': MockCategory,
                'User': MockUser,
                'Last_chng_by': MockLast_chng_by
            };
            createController = function() {
                $injector.get('$controller')("SubCategoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'yoUVcodeApp:subCategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
