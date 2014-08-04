var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var width;
    var height;
    var resource;

    var doKeyDown = function(event) {
        switch(event.keyCode) {
            case 37: // LEFT
                resource.move('LEFT');
                break;
            case 38: // UP
                resource.move('UP');
                break;
            case 39: // RIGHT
                resource.move('RIGHT');
                break;
            case 40: // DOWN
                resource.move('DOWN');
                break;
            default:
                console.log('We don\'t care about keycode: ' + event.keyCode);
        }
    };

    var doKeyUp = function(event) {
        switch(event.keyCode) {
            case 37: // LEFT
                resource.stopMove('LEFT');
                break;
            case 38: // UP
                resource.stopMove('UP');
                break;
            case 39: // RIGHT
                resource.stopMove('RIGHT');
                break;
            case 40: // DOWN
                resource.stopMove('DOWN');
                break;
            default:
                console.log('We don\'t care about keycode: ' + event.keyCode);
        }
    };


    window.addEventListener('keydown', doKeyDown, false);
    window.addEventListener('keyup', doKeyUp, false);

    var onJoin = function(_width, _height) {
        width = _width;
        height = _height;
        drawBoard();
    };

    var update = function(tanks) {
        $.each(tanks, function() {
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
        $("#joinGame").click(function() {
            $(this).hide();
            $("#playerName").prop("disabled", true);
            $("#startGame").show();
            resource.join(onJoin, $("#playerName").val());
        });

        $("#startGame").click(function() {
            $(this).hide();
            $("#stopGame").show();
            resource.start();
        });

        $("#stopGame").click(function() {
            resource.stop();
        });
   };

    var resource = new GameResource(update);
    registerEventListeners();
};