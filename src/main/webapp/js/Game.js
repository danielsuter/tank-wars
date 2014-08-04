var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var width;
    var height;
    var resource;
    var tank;
    var projectile;
    var tanks = [];
    var projectiles = [];
    var lastCode;
    var knownActors = [];

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
        var projectilesFromResponse = [];
        $.each(actors, function() {
            if (!knownActors[this.id]) {
                knownActors[this.id] = this;
            }

            var actorType = knownActors[this.id].actorType;
            switch (actorType) {
                case "TANK":
                    var tankShape = tanks[this.id];
                    if (!tankShape) {
                        tankShape = tank.drawTank(this);
                        tanks[this.id] = tankShape;
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
                    projectilesFromResponse[this.id] = this;
                    var projectileShape = projectiles[this.id];
                    if (!projectileShape) {
                        projectileShape = projectile.drawProjectile(this);
                        projectiles[this.id] = projectileShape;
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

            }
        });

//        removeDeadProjectiles(projectilesFromResponse);
        canvas.renderAll();
    };

    var drawBoard = function() {
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.backgroundColor = '#33FF33';
        canvas.renderAll();
    };

    var removeDeadProjectiles = function(projectilesInGame) {
        $.each(projectiles, function() {
            if (!projectilesInGame[this.id]) {
                projectiles[this.id] = undefined;
                knownActors[this.id] = undefined;
                canvas.remove(this);
            }
        });
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