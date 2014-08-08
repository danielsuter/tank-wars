var BombBag = {

    drawBombBag: function (bombBag) {

        var background;
        var innerBomb;

        background = new fabric.Rect({
            left: 0,
            top: 0,
            fill: 'blue',
            width: bombBag.width,
            height: bombBag.height
        });

        innerBomb = new fabric.Circle({
            left: 2,
            top: 2,
            fill: 'black',
            radius: (bombBag.width - 4) / 2
        });

        return new fabric.Group([background, innerMine], {
            left: bombBag.x,
            top: bombBag.y
        });
    }
};