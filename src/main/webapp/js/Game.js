var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var width;
    var height;
    var resource;
    var tank;
    var wall;
    var projectile;
    var lastCode;
    var knownActors = [];
    var knownShapes = [];

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
        var actorsFromResponse = [];
        $.each(actors, function() {
            if (!knownActors[this.id]) {
                knownActors[this.id] = this;
            }

            actorsFromResponse[this.id] = this;

            var actorType = knownActors[this.id].actorType;
            switch (actorType) {
                case "TANK":
                    var tankShape = knownShapes[this.id];
                    if (!tankShape) {
                        tankShape = tank.drawTank(this);
                        knownShapes[this.id] = tankShape;
                        canvas.add(tankShape);
                    } else {
                        if (this.x) {
                            tankShape.set({"left": this.x});
                        }
                        if (this.y) {
                            tankShape.set({"top": this.y});
                        }
                        if (this.x || this.y) {
                            tankShape.setCoords();
                        }
                    }
                    break;
                case "PROJECTILE":
                    var projectileShape = knownShapes[this.id];
                    if (!projectileShape) {
                        projectileShape = projectile.drawProjectile(this);
                        knownShapes[this.id] = projectileShape;
                        canvas.add(projectileShape);
                    } else {
                        if (this.x) {
                            projectileShape.set({"left": this.x});
                        }
                        if (this.y) {
                            projectileShape.set({"top": this.y});
                        }
                        if (this.x || this.y) {
                            projectileShape.setCoords();
                        }
                    }
                    break;
                case "WALL":
                    var wallShape = knownShapes[this.id];
                    if (!wallShape) {
                        wallShape = wall.drawWall(this);
                        knownShapes[this.id] = wallShape;
                        canvas.add(wallShape);
                    }
                    break;
            }
        });

        removeDeadActors(actorsFromResponse);
        canvas.renderAll();
    };

    var drawBoard = function() {
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.backgroundColor = '#33FF33';
        canvas.renderAll();
    };

    var removeDeadActors = function(actorsFromResponse) {
        for (var id in knownActors) {
            if (!actorsFromResponse[id]) {
                canvas.remove(knownShapes[id]);
                knownActors[id] = undefined;
                knownShapes[id] = undefined;
            }
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

    resource = new GameResource(update);
    tank = new Tank();
    projectile = new Projectile();
    registerEventListeners();
};