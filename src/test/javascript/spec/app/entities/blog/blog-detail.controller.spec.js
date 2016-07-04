'use strict';

describe('Controller Tests', function() {

    describe('Blog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBlog, MockCategory, MockSubcategory, MockBlogStatus, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBlog = jasmine.createSpy('MockBlog');
            MockCategory = jasmine.createSpy('MockCategory');
            MockSubcategory = jasmine.createSpy('MockSubcategory');
            MockBlogStatus = jasmine.createSpy('MockBlogStatus');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Blog': MockBlog,
                'Category': MockCategory,
                'Subcategory': MockSubcategory,
                'BlogStatus': MockBlogStatus,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("BlogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'yoUVcodeApp:blogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
