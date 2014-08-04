var GameResource = function(_onGameUpdate) {
    var onGameUpdate = _onGameUpdate;

    this.join = function(onJoined) {
        onJoined(
            {
                width : 800,
                height : 400
            },
            "abcdefghijklmnop"
        );

        onGameUpdate(
            [
                {
                    playerName : "hugo",
                    speed : 5,
                    x : 10,
                    y : 10,
                    width : 10,
                    height : 10
                },
                {
                    playerName : "peter",
                    speed : 5,
                    x : 50,
                    y : 50,
                    width : 10,
                    height : 10
                },
                {
                    playerName : "fritz",
                    speed : 5,
                    x : 5,
                    y : 150,
                    width : 10,
                    height : 10
                }
            ]
        );

    };



};