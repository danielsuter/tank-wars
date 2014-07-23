var TUtil = {};
/**
 *
 * @returns {string}
 */
TUtil.getWebsocketUrl = function() {
    var wsUri;
    var hostname =  window.location.hostname;
    if(hostname === 'localhost') {
        wsUri = "ws://" + hostname + ":8080/tank-wars/lobby";
    } else {
        wsUri = "ws://" + hostname + ":9090/tank-wars/lobby";
        console.log("detected CI, using wsUri: " + wsUri);
    }
    return wsUri;
};