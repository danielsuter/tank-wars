var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var width;
    var height;
    var resource;
    var tanks = [];
    var lastCode;

    var doKeyDown = function(event) {
        if(event.keyCode === lastCode) {
            return;
        }
        lastCode = event.keyCode;

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
        }
    };

    var doKeyUp = function(event) {
        lastCode = null;
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
            case 32: // Space
                resource.shoot();
                break;
        }
    };

    $('#' + canvasId).bind({
        keydown: doKeyDown,
        keyup: doKeyUp
    });

    var onJoin = function(_width, _height) {
        width = _width;
        height = _height;
        drawBoard();
    };

    var update = function(_tanks) {
        $.each(_tanks, function() {
            drawTank(this);
        });
    };

    var drawBoard = function() {
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.backgroundColor = 'rgba(0,0,255,0.3)';
        canvas.renderAll();
    };

    var drawTank = function(tank) {
        var tankShape = tanks[tank.playerId];

        if (!tankShape) {
            tankShape = new fabric.Rect({
                left: tank.x,
                top: tank.y,
                fill: 'black',
                width: tank.width,
                height: tank.height
            });

            tanks[tank.playerId] = tankShape;
            canvas.add(tankShape);
        } else {
            tankShape.set({"left" : tank.x, "top" : tank.y});
            tankShape.setCoords();
        }

        canvas.renderAll();
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
            $(this).hide();
            $("#startGame").show();
        });
   };

    var resource = new GameResource(update);
    registerEventListeners();
};