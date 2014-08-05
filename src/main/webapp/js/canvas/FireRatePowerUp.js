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
            left: powerUp.x + (powerUp.width / 2) - 2,
            top: powerUp.y,
            fill: 'orange',
            width: 4,
            height: powerUp.height
        });

        return new fabric.Group([background, orangeX], {
            left: powerUp.x,
            top: powerUp.y
        });
    },
};