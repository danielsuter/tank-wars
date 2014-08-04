var GameResource = function(_onGameUpdate) {
    var onGameUpdate = _onGameUpdate;
    var websocket;
    var onJoined;

    this.join = function(_onJoined, playerName) {
        onJoined = _onJoined;
        var wsUri = TUtil.getWebsocketGameUrl();
        websocket = new WebSocket(wsUri);

        websocket.onerror = this.onError;
        websocket.onmessage = this.onMessage;

        websocket.onopen = function(event) {
            sendMessage("JOIN " + playerName);
        }
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
     *
     * @param message {string}
     */
    var sendMessage = function (message) {
        websocket.send(message);
    }
};