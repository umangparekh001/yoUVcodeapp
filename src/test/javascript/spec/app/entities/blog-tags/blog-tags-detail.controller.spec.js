'use strict';

describe('Controller Tests', function() {

    describe('BlogTags Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBlogTags, MockBlog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBlogTags = jasmine.createSpy('MockBlogTags');
            MockBlog = jasmine.createSpy('MockBlog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BlogTags': MockBlogTags,
                'Blog': MockBlog
            };
            createController = function() {
                $injector.get('$controller')("BlogTagsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'yoUVcodeApp:blogTagsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
