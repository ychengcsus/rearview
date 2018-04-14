(function () {
    'use strict';

    angular
        .module('rearviewSandiegoApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
