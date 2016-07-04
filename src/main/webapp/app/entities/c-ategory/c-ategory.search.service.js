(function() {
    'use strict';

    angular
        .module('yoUVcodeApp')
        .factory('CATEGORYSearch', CATEGORYSearch);

    CATEGORYSearch.$inject = ['$resource'];

    function CATEGORYSearch($resource) {
        var resourceUrl =  'api/_search/c-ategories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
