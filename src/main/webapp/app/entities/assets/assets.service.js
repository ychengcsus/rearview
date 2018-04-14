(function() {
    'use strict';
    angular
        .module('rearviewSandiegoApp')
        .factory('Assets', Assets);

    Assets.$inject = ['$resource', 'DateUtils'];

    function Assets ($resource, DateUtils) {
        var resourceUrl =  'api/assets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.assetCreationDate = DateUtils.convertDateTimeFromServer(data.assetCreationDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
