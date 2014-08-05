var LaserGunPowerUp = {

    drawLaserGunPowerUp: function (powerUp) {

        var background;
        var laser;

        background = new fabric.Rect({
            left: powerUp.x,
            top: powerUp.y,
            fill: 'yellow',
            width: powerUp.width,
            height: powerUp.height
        });

        laser = new fabric.Rect({
            left: powerUp.x + powerUp.width - 13,
            top: powerUp.y + (powerUp.height / 2) - 1,
            fill: 'red',
            width: 10,
            height: 2
        });

        return new fabric.Group([background, laser], {
            left: powerUp.x,
            top: powerUp.y
        });
    }
};