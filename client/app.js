var app = angular.module('myApp', []);

app.controller('controller', function($scope, $http, $timeout) {

    var serverPath = "http://localhost:8080/";

    $scope.loadGame = function(id){
        $timeout(function() {   $http({
            method: "GET", url: serverPath + id
        }).then(function(res) {
            $scope.game = res.data;
        });}, 200);
    };

    $scope.skipTurn = function(){
        $http({
            method: "POST", url: serverPath + $scope.gameId + "/skip"
        }).then(function(response){
            $scope.loadGame($scope.gameId);
        },function(error){
            $scope.loadGame($scope.gameId);
            alert(error.data.message);
        });
    };

    $scope.playCard = function(cardId, player){

        if(player !== $scope.game.turn){
            alert("other players turn");
            return;
        }

        $http({
            method: "POST", url: serverPath + $scope.gameId + "/play/" + cardId
        }).then(function(response){
            $scope.loadGame($scope.gameId);
        },function(error){
            $scope.loadGame($scope.gameId);
            alert(error.data.message);
        });
    };

    $scope.newGame = function(){
        $http({
            method: "POST", url: serverPath + "createNewGame"
        }).then(function(res) {
            $scope.gameId = res.data;
            $scope.loadGame($scope.gameId);
        });
    };

    $scope.newGame();
});