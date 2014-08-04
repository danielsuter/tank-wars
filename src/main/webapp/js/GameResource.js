var GameResource = function(_onGameUpdate) {
    var onGameUpdate = _onGameUpdate;

    var onJoined;

    this.join = function(_onJoined) {
        onJoined = _onJoined;
        var wsUri = TUtil.getWebsocketGameUrl();
        var websocket = new WebSocket(wsUri);

        websocket.onerror = this.onError;
        websocket.onmessage = this.onMessage;
    };

    this.onMessage = function(event) {
        var message = JSON.parse(event.data);

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

};