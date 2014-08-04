var Tank = function () {

    this.drawTank = function (tank) {
        var raupeLeft;
        var raupeRight;
        var tower;
        var tankBody;
        var pipe;


        raupeLeft = new fabric.Rect({
            left: tank.x,
            top: tank.y,
            fill: 'brown',
            width: tank.width,
            height: 7
        });

        raupeRight = new fabric.Rect({
            left: tank.x,
            top: tank.y + 18,
            fill: 'brown',
            width: tank.width,
            height: 7
        });

        tankBody = new fabric.Rect({
            left: tank.x + 3,
            top: tank.y + 4,
            fill: 'black',
            width: tank.width - 6,
            height: tank.height - 8
        });

        tower = new fabric.Circle({
            left: tank.x + 8,
            top: tank.y + 8,
            fill: 'brown',
            radius: 5
        });

        pipe = new fabric.Rect({
            left: tank.x + 12,
            top: tank.y + 11,
            fill: 'black',
            width: tank.width - 12,
            height: 4
        });

        return new fabric.Group([raupeLeft, raupeRight, tankBody, tower, pipe], {
            left: tank.x,
            top: tank.y
        });
    };
};