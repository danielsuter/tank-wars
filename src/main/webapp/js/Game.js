var Game = function(canvasId) {
    var canvas = new fabric.StaticCanvas(canvasId);
    var resource;
    var lastCode;
    var knownActors = [];
    var knownPlayers = [];
    var renderer;
    var lastFired = 0;
    var myId; // player id = tank id
    var ignoreNextKeyUp = false;
    /**
     * @type {boolean} true if the player is dead
     */
    var isDead = false;

    var doKeyDown = function(event) {
        if(event.keyCode === lastCode || isDead) {
            return;
        }

        if (lastCode) {
            ignoreNextKeyUp = true;
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
        if(isDead) {
            return;
        }

        if (ignoreNextKeyUp) {
            ignoreNextKeyUp = false;
            return;
        }

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
        var cooldown = 500 / fireRate;

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

    var onConnect = function (gameRunning) {
        $("#connectGame").hide();
        $("#connectionInfo").hide();
        if (gameRunning) {
            $("#gameRunning").show();
        } else {
            $("#connectionSuccess").show();
            $("#joinGame").show();
            $("#playerName").show();
        }
    };

    var onJoin = function (id, playGround) {
        myId = id;
        drawBoard(playGround);
        $("#playersDisplay").show();
        newsFlash("WELCOME!");
    };

    var update = function(actorsFromResponse) {
        removeDeadActors(actorsFromResponse);

        $.each(actorsFromResponse, function() {
            var actorUpdate = this;

            if (isNewActor(actorUpdate)) {
                knownActors[actorUpdate.id] = actorUpdate;
                renderer.createShape(actorUpdate);
            } else {
                updateActor(actorUpdate);
                renderer.updateShape(knownActors[actorUpdate.id]);
            }

            updateScore(knownActors[actorUpdate.id]);
        });


        checkOwnDeath();
        renderer.renderStatusBar(knownActors[myId]);

        renderer.render();
    };

    var checkOwnDeath = function() {
        var myPlayer = knownActors[myId];
        if(!myPlayer) {
            isDead = true;
            renderer.renderDeath();
        }
    };

    var updateActor= function(actorUpdate) {
        var cachedActor = knownActors[actorUpdate.id];

        for (var property in actorUpdate) {
            if (cachedActor[property] !== actorUpdate[property]) {
                cachedActor[property] = actorUpdate[property];
            }
        }
    }

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
                delete knownActors[id];
            }
        }
    };

    var drawBoard = function(playGround) {
        canvas.setWidth(playGround.fieldWidth);
        canvas.setHeight(playGround.fieldHeight);
        drawWalls(playGround.walls);
        canvas.backgroundColor = '#85a923';
        canvas.renderAll();
    };

    var drawWalls = function(walls) {
        $.each(walls, function() {
            renderer.createShape(this);
        });
    };

    var registerEventListeners = function () {
        $("#connectGame").click(function () {
            resource.connect(onConnect);
        });
        $("#joinGame").click(function () {
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
            $("#playerName").prop('disabled', false);
        });
   };

   var playerDisplayTemplate =
       "<li class='list-group-item playerScore'>" +
           "<span class='label label-default' id='color{{id}}'>{{name}}</span>" +
           "<span class='badge' name='killsBadge' data-value='0' id='kills{{id}}'>Kills: 0</span>" +
           "<span class='badge' name='hitsBadge' data-value='0' id='hits{{id}}'>Hits: 0</span>" +
       "</li>";


   var onPlayersChanged = function(players) {
       var rows = "";
       $.each(players, function() {
           knownPlayers[this.id] = this.name;
           rows += Mustache.render(playerDisplayTemplate, this);
       });
       $("#playersList").html(rows);
   };

   var updateScore = function(actor) {
       var scoreChanged = false;
       if (actor.hits) {
           var hitsBadge = $("#hits" + actor.id);
           hitsBadge.html("Hits: " + actor.hits);
           hitsBadge.data("value", actor.hits);

           scoreChanged = true;
       }

       if (actor.kills) {
           var killsBadge = $("#kills" + actor.id);
           killsBadge.html("Kills: " + actor.kills);
           killsBadge.data("value", actor.kills);
           scoreChanged = true;

           if (knownPlayers[actor.tankKilled]) {
               var killerName = knownPlayers[actor.id];
               var victimName = knownPlayers[actor.tankKilled];
               newsFlash(killerName + " has PWNED " + victimName + "!!!!");
               delete knownPlayers[actor.tankKilled];
           }
       }
       if (actor.color) {
           $("#color" + actor.id).css('background-color', actor.color);
       }

       if (scoreChanged) {
           sortRows();
       }
   };

    var sortRows = function() {
        var playerRows = $(".playerScore").get();

        playerRows.sort(function(a, b) {
            var aHits = $(a).find($("span[name='hitsBadge']")).data("value");
            var aKills = $(a).find($("span[name='killsBadge']")).data("value");
            var bHits = $(b).find($("span[name='hitsBadge']")).data("value");
            var bKills = $(b).find($("span[name='killsBadge']")).data("value");

            var compareKills = (parseInt(aKills) - parseInt(bKills)) * -1;

            if (compareKills !== 0) {
                return compareKills;
            }

            return (parseInt(aHits) - parseInt(bHits)) * -1;
        });

        $.each(playerRows, function() {
           $("#playersList").append(this);
        });
    };

    var newsFlash = function(text) {
        var container = $("#gameNews");
        var news = $("#newsText");

        news.html(text);
        container.fadeIn(1000);
        container.fadeOut(7000);
    };

    resource = new GameResource(update, onPlayersChanged);
    renderer = new ShapeRenderer(canvas);

    canvas.renderOnAddRemove = false;

    registerEventListeners();
};