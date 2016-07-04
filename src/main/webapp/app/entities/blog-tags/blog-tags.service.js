(function() {
    'use strict';
    angular
        .module('yoUVcodeApp')
        .factory('BlogTags', BlogTags);

    BlogTags.$inject = ['$resource'];

    function BlogTags ($resource) {
        var resourceUrl =  'api/blog-tags/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
