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

    var update = function(_tanks) {
        $.each(_tanks, function() {
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

    var drawProjectile = function(projectile) {
        var projectileShape = projectiles[projectile.projectileId];

        if (!projectileShape) {
            projectileShape = new fabric.Circle({
                left: projectile.x,
                top: projectile.y,
                fill: 'red',
                radius: projectile.width
            });

            projectiles[projectile.projectileId] = projectileShape;
            canvas.add(projectileShape);
        } else {
            projectileShape.set({"left" : projectile.x, "top" : projectile.y});
            projectileShape.setCoords();
        }
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