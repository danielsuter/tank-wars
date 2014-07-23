$(function() {
    var messageText = $("#messageId");

    $("#sendButtonId").on('click', function() {
        sendMessage(messageText.val());
        messageText.val('');
    });


    var wsUri = TUtil.getWebsocketUrl();


    var output = document.getElementById("messages");
    var websocket = new WebSocket(wsUri);


    websocket.onerror = function(evt) { onError(evt) };
    /**
     *
     * @param evt {Event} websocket response
     */
    function onError(evt) {
        writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data + '<br/>' + wsUri);
    }

    websocket.onopen = function(evt) { onOpen(evt) };

    /**
     *
     * @param message {string}
     */
    function writeToScreen(message) {
        output.innerHTML += message + "<br>";
    }

    function onOpen() {
        writeToScreen("Connected to " + wsUri);
    }
    /**
     *
     * @param event {Event}
     */
    websocket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        writeToScreen(message.user + ': ' + message.message);
    };

    /**
     *
     * @param message {string}
     */
    function sendMessage(message) {
        var messageObject = {
          command: "CHAT",
          data: message
        };

        websocket.send(JSON.stringify(messageObject));
    }
});