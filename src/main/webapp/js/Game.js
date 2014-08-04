var Game = function(canvasId) {
    var canvas = new fabric.Canvas(canvasId);
    var resource = new GameResource(update);
    var width;
    var height;

    var update = function(gameState) {
        var rect = new fabric.Rect({
            left: 10,
            top: height / 2,
            fill: 'black',
            width: 10,
            height: 10
        });

        canvas.add(rect);
    };

    var onJoin = function(boardSize, playerId) {
        width = boardSize.width;
        height = boardSize.height;
    };

    var drawBoard = function() {
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.backgroundColor = 'rgba(0,0,255,0.3)';
        update();
    };

//    resource.join(onJoin);
    width = 600;
    height = 400;
    drawBoard();
};