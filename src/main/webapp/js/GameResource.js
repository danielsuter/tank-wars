var GameResource = function(_onGameUpdate, _onPlayersChanged) {
    var onGameUpdate = _onGameUpdate;
    var websocket;
    var onJoined;
    var onPlayersChanged = _onPlayersChanged;

    var lastMessageTime = 0;

    this.join = function(_onJoined, playerName) {
        console.log("Joining game with player name " + playerName + ".");

        onJoined = _onJoined;
        var wsUri = TUtil.getWebsocketGameUrl();
        websocket = new WebSocket(wsUri);

        websocket.onerror = this.onError;
        websocket.onmessage = this.onMessage;

        websocket.onopen = function(event) {
            sendMessage("JOIN " + playerName);
        }
    };

    this.start = function() {
        console.log("Starting game.");
        sendMessage("START");
    };

    this.stop = function() {
        console.log("Stopping game.");
        sendMessage("STOP");
    };

    this.shoot = function() {
        sendMessage('SHOOT');
    };

    this.onMessage = function(event) {
        var message = JSON.parse(event.data);

        if(!message.messageType) {
            message.splice(0, 1); // return value -> message type
            var actors = mapToActorArray(message);
            var then = $.now();
            onGameUpdate(actors);
            var time = $.now() - then;
            if (time > 50) {
                console.log("processing time unusually high: " + time + "ms");
            }
        } else if(message.messageType === 'JOIN') {
            onJoined(message.playerId, message.playGround);
        } else if(message.messageType === "PLAYERS_CHANGED") {
            onPlayersChanged(message.players);
        }
    };

    this.onError = function(event) {
        console.error(event);
    };

    /**
     * @param direction {string} one of RIGHT, LEFT, TOP, DOWN
     */
    this.move = function(direction) {
        sendMessage('MOVE ' + direction);
    };

    /**
     * @param direction {string} one of RIGHT, LEFT, TOP, DOWN
     */
    this.stopMove = function(direction) {
        sendMessage('MOVESTOP ' + direction);
    };

    /**
     *
     * @param message {string}
     */
    var sendMessage = function (message) {
        websocket.send(message);
    };

    this.clear = function() {
        sendMessage('CLEAR');
    };

    var protocolToViewMap = {
        "t" : "actorType",
        "i" : "id",
        "x" : "x",
        "y" : "y",
        "r" : "radius",
        "w" : "width",
        "h" : "height",
        "d" : "direction",
        "v" : "velocity",
        "f" : "fireRate",
        "l" : "health"
    };

    var actorTypeMap = {
        0 : "TANK",
        1 : "PROJECTILE",
        2 : "WALL",
        3 : "HealthPowerUp",
        4:  "FireRatePowerUp"
    };

    var mapToActorArray = function(protocol) {
        var actors = [];
        $.each(protocol, function() {
            var actor = {};

            for (var property in this) {
                var viewPropertyName = protocolToViewMap[property];
                if (viewPropertyName === "actorType") {
                    actor[viewPropertyName]  = actorTypeMap[this[property]];
                } else {
                    actor[viewPropertyName] = this[property];
                }
            }

            actors.push(actor);
        });

        return actors;
    };
};