(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .factory('BlogSearch', BlogSearch);

    BlogSearch.$inject = ['$resource'];

    function BlogSearch($resource) {
        var resourceUrl =  'api/_search/blogs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
