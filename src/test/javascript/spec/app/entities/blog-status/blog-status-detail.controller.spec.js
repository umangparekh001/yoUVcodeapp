'use strict';

describe('Controller Tests', function() {

    describe('BlogStatus Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBlogStatus, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBlogStatus = jasmine.createSpy('MockBlogStatus');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BlogStatus': MockBlogStatus,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("BlogStatusDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'yoUVcodeApp:blogStatusUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
