var Game = function(canvasId) {
    var canvas = new fabric.Canvas(canvasId);
    var width;
    var height;

    var onJoin = function(boardSize, playerId) {
        width = boardSize.width;
        height = boardSize.height;
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

    var resource = new GameResource(update);
    resource.join(onJoin);
};