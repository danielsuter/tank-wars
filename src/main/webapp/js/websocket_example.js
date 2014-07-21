var wsUri = "ws://" + document.location.host + document.location.pathname + "echo";
var output = document.getElementById("output");
var websocket = new WebSocket(wsUri);


websocket.onerror = function(evt) { onError(evt) };
/**
 *
 * @param evt {Event} websocket response
 */
function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
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
    websocket.send("hello from client :-)");
}
/**
 *
 * @param event {Event}
 */
websocket.onmessage = function(event) {
    writeToScreen("Received message: " + event.data);
};