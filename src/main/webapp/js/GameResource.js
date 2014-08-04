var GameResource = function(_onGameUpdate) {
    var onGameUpdate = _onGameUpdate;


    this.join = function(onJoined) {
        onJoined(/* feld grösse, player id */);
    };

};