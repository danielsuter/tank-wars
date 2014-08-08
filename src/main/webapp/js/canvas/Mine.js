var Mine = {

    drawMine: function (mine) {

        var innerMine;
        var trigger;

        innerMine = new fabric.Circle({
            left: 2,
            top: 2,
            fill: 'grey',
            radius: (mine.width - 4) / 2
        });

        trigger = new fabric.Circle({
            left: (mine.width - 2) / 2,
            top: (mine.height - 2) / 2,
            fill: 'red',
            radius: 2
        });

        return new fabric.Group([innerMine, trigger], {
            left: mine.x,
            top: mine.y
        });
    }
};