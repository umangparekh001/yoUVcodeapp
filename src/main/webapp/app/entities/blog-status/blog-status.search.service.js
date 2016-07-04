(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .factory('BlogStatusSearch', BlogStatusSearch);

    BlogStatusSearch.$inject = ['$resource'];

    function BlogStatusSearch($resource) {
        var resourceUrl =  'api/_search/blog-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
