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
    var clock;
    var gameStarted = false;

    /**
     * @type {boolean} true if the player is dead
     */
    var isDead = false;

//    var audioHit = document.createElement('audio');
//    audioHit.setAttribute('src', 'sound/hit.flac');

    var audioHit = new Audio('sound/hit.flac');


    var doKeyDown = function(event) {
        if(event.keyCode === lastCode || isDead) {
            return;
        }

        if (lastCode && isDirectionKey(event.keyCode)) {
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

    var isDirectionKey = function(keyCode) {
        return keyCode >= 37 && keyCode <= 40;
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
            case 77: // m
                event.preventDefault();
                resource.plantMine();
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
        if (gameRunning) {
            $("#gameRunning").show();
        } else {
            $(".enterName").show();
        }
    };

    var onStop = function () {
        $("#connectionInfo").hide();
        $("#connectionWarning").show();
        clock.stop();
    };

    var onJoin = function (id, playGround) {
        myId = id;
        drawBoard(playGround);
        var playersDisplay = $("#playersDisplay");
        var canvas = $("#board");

        var canvasWidth = canvas.width();
        var x = canvas.position().left;
        var y = canvas.position().top;
        var topRightCanvas = parseFloat(x) + parseFloat(canvasWidth);

        playersDisplay.show();
        playersDisplay.css("position", "fixed");
        playersDisplay.css("left", topRightCanvas );
        playersDisplay.css("top", y );

        $("#countdown").show();
    };

    var update = function(actorsFromResponse) {
        if (!gameStarted) {
            gameStarted = true;
            countTime();
        }

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
        renderer.renderStatusBar(knownActors[myId], canvas.getHeight());

        renderer.render();
    };

    var checkOwnDeath = function() {
        if (isDead) {
            return;
        }

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
                if(knownActors[id].actorType === 'PROJECTILE') {
                    audioHit.play();
                }
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
            $(".startGame").hide();
            $(".stopGame").show();
            resource.start();
        });

        $("#stopGame").click(function() {
            resource.stop();
            $(this).hide();
            $("#startGame").show();
            clock.stop();
        });

        $("#clearGame").click(function() {
            resource.clear();
            $("#stopGame").hide();
            $("#joinGame").show();
            $("#playerName").prop('disabled', false);
            clock.stop();
        });
   };

   var countTime = function() {
       clock.countdown = false;
       clock.start();
   };

   var playerDisplayTemplate =
       "<tr id='color{{id}}'>" +
           "<td name='rank'></td>" +
           "<td>{{name}}</span>" +
           "<td data-value='0' name='hitsBadge' id='hits{{id}}'>0</td>" +
           "<td data-value='0' name='killsBadge' id='kills{{id}}'>0</td>" +
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
           hitsBadge.html(actor.hits);
           hitsBadge.data("value", actor.hits);

           scoreChanged = true;
       }

       if (actor.kills) {
           var killsBadge = $("#kills" + actor.id);
           killsBadge.html(actor.kills);
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
           $("#color" + actor.id).css('text-color', actor.color);
       }

       if (scoreChanged) {
           sortRows();
       }
   };

    var sortRows = function() {
        var playerRows = $("#playersList tr").get();

        playerRows.sort(function(a, b) {
            var aHits = $(a).find($("td[name='hitsBadge']")).data("value");
            var aKills = $(a).find($("td[name='killsBadge']")).data("value");
            var bHits = $(b).find($("td[name='hitsBadge']")).data("value");
            var bKills = $(b).find($("td[name='killsBadge']")).data("value");

            var compareKills = (parseInt(aKills) - parseInt(bKills)) * -1;

            if (compareKills !== 0) {
                return compareKills;
            }

            return (parseInt(aHits) - parseInt(bHits)) * -1;
        });

        $.each(playerRows, function(i) {
           $(this).find("td[name='rank']").html(parseInt(i) + 1);
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

    var onStart = function() {
        clock.start();
    };

    resource = new GameResource(update, onPlayersChanged, onStart, onStop);
    renderer = new ShapeRenderer(canvas);

    canvas.renderOnAddRemove = false;

    registerEventListeners();

    clock = $('#countdown').FlipClock(10, {
        countdown : true,
        clockFace : 'MinuteCounter'
    });

    clock.stop();
};