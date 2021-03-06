var Tank = {

    drawTankEast : function (tank) {
        var raupeLeft;
        var raupeRight;
        var tower;
        var tankBody;
        var pipe;

        this.direction = "E";

        raupeLeft = new fabric.Rect({
            left: tank.x,
            top: tank.y,
            fill: 'brown',
            width: tank.width,
            height: tank.height / 4
        });

        raupeRight = new fabric.Rect({
            left: tank.x,
            top: tank.y + tank.height - tank.height / 4,
            fill: 'brown',
            width: tank.width,
            height: tank.height / 4
        });

        tankBody = new fabric.Rect({
            left: tank.x + tank.width / 8,
            top: tank.y + tank.height / 8,
            fill: tank.color,
            width: tank.width * 0.75,
            height: tank.height * 0.75
        });

        tower = new fabric.Circle({
            left: tank.x + (tank.width / 2) - ((tank.height / 5)),
            top: tank.y + (tank.height / 2) - ((tank.height / 5)),
            fill: 'brown',
            radius: tank.height / 4.5
        });

        pipe = new fabric.Rect({
            left: tank.x + (tank.width / 2) - 1,
            top: tank.y + (tank.height / 2) - 1,
            fill: 'black',
            width: tank.width / 2 + 4,
            height: 4
        });

        return new fabric.Group([raupeLeft, raupeRight, tankBody, tower, pipe], {
            left: tank.x,
            top: tank.y
        });
    },

    drawTankWest : function (tank) {
        var raupeLeft;
        var raupeRight;
        var tower;
        var tankBody;
        var pipe;

        this.direction = "W";

        raupeLeft = new fabric.Rect({
            left: tank.x,
            top: tank.y,
            fill: 'brown',
            width: tank.width,
            height: tank.height / 4
        });

        raupeRight = new fabric.Rect({
            left: tank.x,
            top: tank.y + tank.height - tank.height / 4,
            fill: 'brown',
            width: tank.width,
            height: tank.height / 4
        });

        tankBody = new fabric.Rect({
            left: tank.x + tank.width / 8,
            top: tank.y + tank.height / 8,
            fill: tank.color,
            width: tank.width * 0.75,
            height: tank.height * 0.75
        });

        tower = new fabric.Circle({
            left: tank.x + (tank.width / 2) - ((tank.height / 5)),
            top: tank.y + (tank.height / 2) - ((tank.height / 5)),
            fill: 'brown',
            radius: tank.height / 4.5
        });

        pipe = new fabric.Rect({
            left: tank.x - 4,
            top: tank.y + (tank.height / 2) - 1,
            fill: 'black',
            width: tank.width / 2 + 4,
            height: 4
        });

        return new fabric.Group([raupeLeft, raupeRight, tankBody, tower, pipe], {
            left: tank.x,
            top: tank.y
        });
    },

    drawTankNorth : function (tank) {
        var raupeLeft;
        var raupeRight;
        var tower;
        var tankBody;
        var pipe;

        this.direction = "N";

        raupeLeft = new fabric.Rect({
            left: tank.x,
            top: tank.y,
            fill: 'brown',
            width: tank.width / 4,
            height: tank.height
        });

        raupeRight = new fabric.Rect({
            left: tank.x + tank.width - tank.width / 4,
            top: tank.y,
            fill: 'brown',
            width: tank.width / 4,
            height: tank.height
        });

        tankBody = new fabric.Rect({
            left: tank.x + tank.width / 8,
            top: tank.y + tank.height / 8,
            fill: tank.color,
            width: tank.width * 0.75,
            height: tank.height * 0.75
        });

        tower = new fabric.Circle({
            left: tank.x + (tank.width / 2) - ((tank.height / 5)),
            top: tank.y + (tank.height / 2) - ((tank.height / 5)),
            fill: 'brown',
            radius: tank.height / 4.5
        });

        pipe = new fabric.Rect({
            left: tank.x + (tank.width / 2) - 1,
            top: tank.y - 4,
            fill: 'black',
            width: 4,
            height: tank.width / 2 + 4
        });

        return new fabric.Group([raupeLeft, raupeRight, tankBody, tower, pipe], {
            left: tank.x,
            top: tank.y
        });
    },

    drawTankSouth : function (tank) {
        var raupeLeft;
        var raupeRight;
        var tower;
        var tankBody;
        var pipe;

        this.direction = "S";

        raupeLeft = new fabric.Rect({
            left: tank.x,
            top: tank.y,
            fill: 'brown',
            width: tank.width / 4,
            height: tank.height
        });

        raupeRight = new fabric.Rect({
            left: tank.x + tank.width - tank.width / 4,
            top: tank.y,
            fill: 'brown',
            width: tank.width / 4,
            height: tank.height
        });

        tankBody = new fabric.Rect({
            left: tank.x + tank.width / 8,
            top: tank.y + tank.height / 8,
            fill: tank.color,
            width: tank.width * 0.75,
            height: tank.height * 0.75
        });

        tower = new fabric.Circle({
            left: tank.x + (tank.width / 2) - ((tank.height / 5)),
            top: tank.y + (tank.height / 2) - ((tank.height / 5)),
            fill: 'brown',
            radius: tank.height / 4.5
        });

        pipe = new fabric.Rect({
            left: tank.x + (tank.width / 2) - 1,
            top: tank.y + (tank.height / 2),
            fill: 'black',
            width: 4,
            height: tank.width / 2 + 4
        });

        return new fabric.Group([raupeLeft, raupeRight, tankBody, tower, pipe], {
            left: tank.x,
            top: tank.y
        });
    }
};