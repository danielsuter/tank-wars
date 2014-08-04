var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var width;
    var height;

    var onJoin = function(_width, _height) {
        width = _width;
        height = _height;
        drawBoard();
    };

    var update = function(tanks) {
        $.each(tanks, function(i) {
            drawTank(this);
        });
    };

    var drawBoard = function() {
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.backgroundColor = 'rgba(0,0,255,0.3)';
        canvas.renderAll();
    };

    var drawTank = function(player) {
        var tank = new fabric.Rect({
            left: player.x,
            top: player.y,
            fill: 'black',
            width: player.width,
            height: player.height
        });

        canvas.add(tank);
    };

    var registerEventListeners = function() {
      $("#submitName").click(function() {
            var playerName = $("#playerName").val();
            console.log("Starting game with player name " + playerName);
            resource.join(onJoin, playerName);
      });
    };

    var resource = new GameResource(update);
    registerEventListeners();
};