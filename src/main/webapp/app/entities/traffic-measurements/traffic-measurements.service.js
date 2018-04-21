(function() {
    'use strict';
    angular
        .module('rearviewFinal1App')
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
                        data.endTime = DateUtils.convertDateTimeFromServer(data.endTime);
                        data.timestamp = DateUtils.convertDateTimeFromServer(data.timestamp);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
