var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var resource;
    var lastCode;
    var knownActors = [];
    var renderer;
    var lastFired = 0;
    var myId;

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
                event.preventDefault();
                if (cannonOffCooldown()) {
                    resource.shoot();
                }
                break;
        }
    };

    var cannonOffCooldown = function() {
        var myTank = knownActors[myId];
        var fireRate = myTank.fireRate;
        var cooldown = 1000 / fireRate;

        var now = $.now();
        var then = lastFired;

        var offCooldown = now - then >= cooldown;
        lastFired = offCooldown ? now : lastFired;
        return offCooldown;
    };

    $('#' + canvasId).bind({
        keydown: doKeyDown,
        keyup: doKeyUp
    });

    var onJoin = function(id, playGround) {
		myId = id;
        drawBoard(playGround);
        $("#playersDisplay").show();
    };

    var update = function(actorsFromResponse) {
        removeDeadActors(actorsFromResponse);

        $.each(actorsFromResponse, function() {
            var actor = this;

            if (isNewActor(actor)) {
                knownActors[actor.id] = actor;
                renderer.createShape(actor);
            } else {
                renderer.updateShape(actor);
            }
        });

        renderer.render();
    };

    var isNewActor = function(actor) {
        return typeof knownActors[actor.id] === "undefined";
    };

    var removeDeadActors = function(actorsFromResponse) {
        var ids = [];

        $.each(actorsFromResponse, function() {
            ids.push(parseInt(this.id));
        });

        for (var id in knownActors) {
            if (ids.indexOf(parseInt(id)) === -1) {
                renderer.removeShape(id);
                knownActors[id] = undefined;
            }
        }
    };

    var drawBoard = function(playGround) {
        canvas.setWidth(playGround.fieldWidth);
        canvas.setHeight(playGround.fieldHeight);
        drawWalls(playGround.walls);
        canvas.backgroundColor = '#33FF33';
        canvas.renderAll();
    };

    var drawWalls = function(walls) {
        $.each(walls, function() {
            renderer.createShape(this);
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
            $("#clearGame").show();
            resource.start();
        });

        $("#stopGame").click(function() {
            resource.stop();
            $(this).hide();
            $("#startGame").show();
        });

        $("#clearGame").click(function() {
            resource.clear();
            $("#stopGame").hide();
            $("#joinGame").show();
        });
   };

    resource = new GameResource(update);
    renderer = new ShapeRenderer(canvas);
    registerEventListeners();
};