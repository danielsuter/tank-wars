var Bomb = {

    drawBomb: function (bombActor) {

        var bomb;

        bomb = new fabric.Circle({
            left: bombActor.width,
            top: bombActor.height,
            fill: 'black',
            radius: bombActor.width / 2
        });


        return new fabric.Group([bomb], {
            left: bombActor.x,
            top: bombActor.y
        });
    }
};