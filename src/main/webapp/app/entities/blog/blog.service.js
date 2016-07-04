(function() {
    'use strict';
    angular
        .module('yoUVcodeApp')
        .factory('Blog', Blog);

    Blog.$inject = ['$resource', 'DateUtils'];

    function Blog ($resource, DateUtils) {
        var resourceUrl =  'api/blogs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.publishDate = DateUtils.convertDateTimeFromServer(data.publishDate);
                        data.created_date = DateUtils.convertDateTimeFromServer(data.created_date);
                        data.last_chng_date = DateUtils.convertDateTimeFromServer(data.last_chng_date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
