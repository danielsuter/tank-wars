var MineBag = {

    drawMineBag: function (mineBag) {

        var background;
        var innerMine;

        background = new fabric.Rect({
            left: 0,
            top: 0,
            fill: 'brown',
            width: mineBag.width,
            height: mineBag.height
        });

        innerMine = new fabric.Circle({
            left: 2,
            top: 2,
            fill: 'grey',
            radius: (mineBag.width - 4) / 2
        });

        return new fabric.Group([background, innerMine], {
            left: mineBag.x,
            top: mineBag.y
        });
    }
};