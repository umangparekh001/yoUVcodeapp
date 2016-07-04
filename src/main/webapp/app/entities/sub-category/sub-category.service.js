(function() {
    'use strict';
    angular
        .module('yoUVcodeApp')
        .factory('SubCategory', SubCategory);

    SubCategory.$inject = ['$resource', 'DateUtils'];

    function SubCategory ($resource, DateUtils) {
        var resourceUrl =  'api/sub-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
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
