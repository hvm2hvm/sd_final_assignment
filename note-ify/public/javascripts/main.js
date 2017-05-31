if (!String.prototype.format) {
    /**
     * > "{0} is dead, but {1} is alive! {0} {2}".format("ASP", "ASP.NET")
     *
     * ASP is dead, but ASP.NET is alive! ASP {2}
     */
  String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
      return typeof args[number] != 'undefined'
        ? args[number]
        : match
      ;
    });
  };
}

(function ($, undefined) {
    $.fn.getCursorPosition = function() {
        var el = $(this).get(0);
        var pos = 0;
        if('selectionStart' in el) {
            pos = el.selectionStart;
        } else if('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    }
})(jQuery);

var noteifyApp = angular.module("noteifyApp", ["ngRoute"]);

noteifyApp.factory('rest', function($q, $http) {
    var factory = {};

    function getEndPoint(objectName, id) {
        var endPoint = "/api/{0}".format(objectName);
        if (id !== undefined) {
            endPoint = "{0}/{1}".format(endPoint, id);
        }
        return endPoint;
    }

    /**
     * @param objectName: string with object name ('patient', 'consultation', 'account')
     * @param id: optional - get 1 object if id present, get all otherwise
     * @returns {Promise}
     */
    factory.get = function(objectName, id) {
        var deferred = $q.defer();

        $http.get(getEndPoint(objectName, id)).then(deferred.resolve, deferred.reject);

        return deferred.promise;
    };

    /**
     * @param objectName: string with object name ('patient', 'consultation', 'account')
     * @param data: object to add (json)
     * @returns {Promise}
     */
    factory.post = function(objectName, data) {
        var deferred = $q.defer();

        $http.post(getEndPoint(objectName), data).then(deferred.resolve, deferred.reject);

        return deferred.promise;
    };

    /**
     * @param objectName: string with object name ('patient', 'consultation', 'account')
     * @param id: id of object to update
     * @param data: object to use for update (json)
     * @returns {Promise}
     */
    factory.put = function(objectName, id, data) {
        var deferred = $q.defer();

        $http.put(getEndPoint(objectName, id), data).then(deferred.resolve, deferred.reject);

        return deferred.promise;
    };

    /**
     * @param objectName: string with object name ('patient', 'consultation', 'account')
     * @param id: object to delete
     * @returns {Promise}
     */
    factory.delete = function(objectName, id) {
        var deferred = $q.defer();

        $http.delete(getEndPoint(objectName, id)).then(deferred.resolve, deferred.reject);

        return deferred.promise;
    };

    return factory;
});

noteifyApp.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            controller: "NoteifyController",
            templateUrl: "assets/views/noteify.html"
        })
        .otherwise({ redirectTo: "/" });
});

noteifyApp.filter("topNotes", function() {
    return function(notes) {
        var filtered = [];
        notes.forEach(function(note) {
            if (note.top) {
                filtered.push(note);
            }
        });
        return filtered;
    };
});

noteifyApp.filter("notesFromIds", function() {
    return function(noteIds, notesById) {
        var filtered = [];
        noteIds.forEach(function(id) {
            filtered.push(notesById[id]);
        });
        return filtered;
    };
});

function keyName(event) {
    if (event.originalEvent != undefined) {
        event = event.originalEvent;
    }

    var result = "";
    if (event.ctrlKey) result += "Ctrl + ";
    if (event.altKey) result += "Alt + ";
    if (event.shiftKey) result += "Shift + ";
    result += event.key;
    return result;
}

var updateNoteTimeout;

noteifyApp.controller("NoteifyController", function ($scope, rest) {

    $scope.currentUser = {};

    $scope.login = function(credentials) {
        rest.post("login", credentials).then(function(result) {
            $scope.currentUser = result.data;
        });
    };

    $scope.refreshCurrentUser = function() {
        rest.get("loggedIn").then(function(result) {
            $scope.currentUser = result.data;
        });
    };

    $scope.logout = function() {
        rest.post("logout").then(function(result) {
            $scope.currentUser = result.data;
        });
    };

    $scope.hasPermission = function(permission) {
        var found = false;
        if ($scope.currentUser == undefined ||
            $scope.currentUser['permissions'] == undefined) {
            return false;
        }
        $scope.currentUser['permissions'].forEach(function(p) {
            if (p == permission) {
                found = true;
            }
        });
        return found;
    };

    $scope.refreshCurrentUser();

    $scope.update = function() {
        autosize.update($("textarea"));
        var height = $("textarea").height();
        $("div.note").height(height);
    };

    $scope.notes = [];
    $scope.notesById = {};

    $scope.refreshNotes = function() {
        rest.get("note").then(function(result) {
            $scope.notes = result.data;
            $scope.notes.push({
                id: -1,
                top: true,
                leaf: true,
                text: ""
            });
            $scope.notesById = {};
            result.data.forEach(function(note) {
                $scope.notesById[note.id] = note;
            });
        });
    };

    $scope.updateNoteTimeout = -1;

    $scope.updateNote = function(note) {
        clearTimeout($scope.updateNoteTimeout);
        if (note.id == -1) {
            $scope.updateNoteTimeout = setTimeout(function () {
                delete note.id;
                rest.post("note", note).then(function (result) {
                    $scope.refreshNotes();
                });
            }, 300);
        } else {
            $scope.updateNoteTimeout = setTimeout(function () {
                rest.put("note", note.id, note).then(function (result) {
                });
            }, 300);
        }
    };

    $scope.refreshNotes();
});
