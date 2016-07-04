'use strict';

describe('Controller Tests', function() {

    describe('CATEGORY Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCATEGORY, MockJhi_user;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCATEGORY = jasmine.createSpy('MockCATEGORY');
            MockJhi_user = jasmine.createSpy('MockJhi_user');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CATEGORY': MockCATEGORY,
                'Jhi_user': MockJhi_user
            };
            createController = function() {
                $injector.get('$controller')("CATEGORYDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'yoUVcodeApp:cATEGORYUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
