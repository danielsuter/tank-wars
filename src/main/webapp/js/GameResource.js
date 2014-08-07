var GameResource = function(_onGameUpdate, _onPlayersChanged, _onStart, _onStop) {
    var onGameUpdate = _onGameUpdate;
    var websocket;
    var onJoined;
    var onConnect;
    var onPlayersChanged = _onPlayersChanged;
    var onStart = _onStart;
    var onStop = _onStop;

    var lastMessageTime = 0;

    this.connect = function (_onConnect) {
        onConnect = _onConnect;
        var wsUri = TUtil.getWebsocketGameUrl();

        websocket = new WebSocket(wsUri);

        websocket.onerror = this.onError;
        websocket.onmessage = this.onMessage;

        websocket.onopen = function (event) {
           sendMessage("CONNECT");
        }
    };

    this.join = function (_onJoined, playerName) {
        onJoined = _onJoined;
        sendMessage("JOIN " + playerName);
    };

    this.start = function () {
        sendMessage("START");
    };

    this.stop = function () {
        sendMessage("STOP");
    };

    this.shoot = function () {
        sendMessage('SHOOT');
    };

    this.plantMine = function() {
        sendMessage('PLANTMINE');
    };

    this.onMessage = function(event) {
        var message = JSON.parse(event.data);

        if(!message.messageType) {
            $("#startGame").hide();
            message.splice(0, 1); // return value -> message type
            var actors = mapToActorArray(message);
            var then = $.now();
            onGameUpdate(actors);
            var time = $.now() - then;
            if (time > 50) {
                console.log("processing time unusually high: " + time + "ms");
            }
        } else if (message.messageType === 'CONNECT') {
            onConnect(message.gameRunning);
        } else if (message.messageType === 'JOIN') {
            onJoined(message.playerId, message.battlefieldMap);
        } else if(message.messageType === "PLAYERS_CHANGED") {
            onPlayersChanged(message.players);
        } else if(message.messageType === "START") {
            onStart();
        } else if(message.messageType === "STROP") {
            onStop();
        }
    };

    this.onError = function (event) {
        $("#connectionInfo").hide();
        $("#connectionWarning").show();
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
        "b" : "tankKilled",
        "t" : "actorType",
        "i" : "id",
        "x" : "x",
        "y" : "y",
        "w" : "width",
        "h" : "height",
        "d" : "direction",
        "v" : "velocity",
        "f" : "fireRate",
		"k" : "kills",
        "s" : "hits",
		"l" : "health",
        "m" : "mines",
        "n" : "bombs"
    };

    var actorTypeMap = {
        0 : "TANK",
        1 : "PROJECTILE",
        2 : "WALL",
        3 : "HealthPowerUp",
        4:  "FireRatePowerUp",
        5:  "LaserGunPowerUp",
        6:  "RocketLauncherPowerUp",
        7:  "MineBag",
        8:  "BombBag",
        9:  "Mine",
        10: "Bomb"
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