var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var width;
    var height;
    var resource;
    var tanks = [];
    var projectiles = [];
    var lastCode;

    var doKeyDown = function(event) {
        if(event.keyCode === lastCode) {
            return;
        }
        lastCode = event.keyCode;

        switch(event.keyCode) {
            case 37: // LEFT
                event.preventDefault();
                resource.move('LEFT');
                break;
            case 38: // UP
                event.preventDefault();
                resource.move('UP');
                break;
            case 39: // RIGHT
                event.preventDefault();
                resource.move('RIGHT');
                break;
            case 40: // DOWN
                event.preventDefault();
                resource.move('DOWN');
                break;
        }
    };

    var doKeyUp = function(event) {
        lastCode = null;
        switch(event.keyCode) {
            case 37: // LEFT
                event.preventDefault();
                resource.stopMove('LEFT');
                break;
            case 38: // UP
                event.preventDefault();
                resource.stopMove('UP');
                break;
            case 39: // RIGHT
                event.preventDefault();
                resource.stopMove('RIGHT');
                break;
            case 40: // DOWN
                event.preventDefault();
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

    var update = function(actors) {
        removeProjectiles();
        $.each(actors, function() {
            switch (this.actorType) {
                case "TANK":
                    drawTank(this);
                    break;
                case "PROJECTILE":
                    drawProjectile(this);
            }
        });

        canvas.renderAll();
    };

    var drawBoard = function() {
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.backgroundColor = '#33FF33';
        canvas.renderAll();
    };

    var removeProjectiles = function() {
        $.each(projectiles, function() {
            canvas.remove(this);
        });
        projectiles = [];
    }


    var drawProjectile = function(projectile) {
        var projectileShape = new fabric.Circle({
            left: projectile.x,
            top: projectile.y,
            fill: 'red',
            radius: projectile.width
        });
        projectiles.push(projectileShape);
        canvas.add(projectileShape);
    };

    var drawTank = function(tank) {
        var tankShape = tanks[tank.playerId];
        var raupeLeft;
        var raupeRight;
        var tower;
        var tankBody;
        var pipe;


        if (!tankShape) {

            raupeLeft = new fabric.Rect({
                left: tank.x,
                top: tank.y,
                fill: 'brown',
                width: tank.width,
                height: 7
            });

            raupeRight = new fabric.Rect({
                left: tank.x,
                top: tank.y + 18,
                fill: 'brown',
                width: tank.width,
                height: 7
            });

            tankBody = new fabric.Rect({
                left: tank.x + 3,
                top: tank.y + 4,
                fill: 'black',
                width: tank.width - 6,
                height: tank.height - 8
            });

            tower = new fabric.Circle({
                left: tank.x + 8,
                top: tank.y + 8,
                fill: 'brown',
                radius: 5
            });

            pipe = new fabric.Rect({
                left: tank.x + 12,
                top: tank.y + 11,
                fill: 'black',
                width: tank.width - 12,
                height: 4
            });

            tankShape = new fabric.Group([raupeLeft, raupeRight, tankBody, tower, pipe], {
                left: tank.x,
                top: tank.y
            });

            tanks[tank.playerId] = tankShape;
            canvas.add(tankShape);
        } else {
            tankShape.set({"left" : tank.x, "top" : tank.y});
            tankShape.setCoords();
        }
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