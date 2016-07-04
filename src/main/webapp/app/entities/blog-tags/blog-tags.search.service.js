(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .factory('BlogTagsSearch', BlogTagsSearch);

    BlogTagsSearch.$inject = ['$resource'];

    function BlogTagsSearch($resource) {
        var resourceUrl =  'api/_search/blog-tags/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
