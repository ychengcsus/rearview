(function() {
    'use strict';
    angular
        .module('rearviewFinal1App')
        .factory('GeoLocationInformation', GeoLocationInformation);

    GeoLocationInformation.$inject = ['$resource'];

    function GeoLocationInformation ($resource) {
        var resourceUrl =  'api/geo-location-informations/:id';

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
