var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var width;
    var height;
    var resource;
    var tank;
    var wall;
    var projectile;
    var tanks = [];
    var walls = [];
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
                    var tankShape = tanks[this.playerId];
                    if (!tankShape) {
                        tankShape = tank.drawTank(this);
                        tanks[this.playerId] = tankShape;
                        canvas.add(tankShape);
                    } else {
                        tankShape.set({"left": this.x, "top": this.y});
                        tankShape.setCoords();
                    }
                    break;
                case "PROJECTILE":
                    var projectileShape = projectile.drawProjectile(this);
                    projectiles.push(projectileShape);
                    canvas.add(projectileShape);
                    break;
                case "WALL":
                    var wallShape = walls[this.wallId];
                    if(!wallShape) {
                        wallShape = wall.drawWall(this);
                        walls[this.wallId] = wallShape;
                        canvas.add(wallShape);
                    }
                    break;
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

    resource = new GameResource(update);
    tank = new Tank();
    projectile = new Projectile();
    wall = new Wall();
    registerEventListeners();
};