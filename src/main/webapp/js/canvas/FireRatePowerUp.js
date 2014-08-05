var FireRatePowerUp = {

    drawFireRatePowerUp: function (powerUp) {

        var background;
        var orangeX;

        background = new fabric.Rect({
            left: powerUp.x,
            top: powerUp.y,
            fill: 'white',
            width: powerUp.width,
            height: powerUp.height
        });

        orangeX = new fabric.Rect({
            left: powerUp.x,
            top: powerUp.y + (powerUp.height/ 2)- 2,
            fill: 'orange',
            width: powerUp.width,
            height: 4
        });

        return new fabric.Group([background, orangeX], {
            left: powerUp.x,
            top: powerUp.y
        });
    },
};