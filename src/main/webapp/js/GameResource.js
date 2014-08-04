var GameResource = function(_onGameUpdate) {
    var onGameUpdate = _onGameUpdate;
    var websocket;
    var onJoined;

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

    this.onMessage = function(event) {
        var message = JSON.parse(event.data);
        console.log('Received message: ' + message);

        if(!message.messageType) {
            // TODO We don't have a message type for the game class yet
            onGameUpdate(message);
        } else if(message.messageType === 'JOIN') {
            onJoined(message.fieldWidth, message.fieldHeight);
        }
    };

    this.onError = function(event) {
        console.error(event);
    }

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
    }
};