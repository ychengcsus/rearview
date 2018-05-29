(function() {
    'use strict';
    angular
        .module('sscappApp')
        .factory('TrafficMeasurements', TrafficMeasurements);

    TrafficMeasurements.$inject = ['$resource', 'DateUtils'];

    function TrafficMeasurements ($resource, DateUtils) {
        var resourceUrl =  'api/traffic-measurements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                        data.end_time = DateUtils.convertDateTimeFromServer(data.end_time);
                        data.timestamp = DateUtils.convertDateTimeFromServer(data.timestamp);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
