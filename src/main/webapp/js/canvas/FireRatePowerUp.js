var FireRatePowerUp = {

    drawFireRatePowerUp: function (powerUp) {

        var background;
        var projectile1;
        var projectile2;
        var projectile3;

        background = new fabric.Rect({
            left: powerUp.x,
            top: powerUp.y,
            fill: 'black',
            width: powerUp.width,
            height: powerUp.height
        });

        projectile1 = new fabric.Circle({
            left: powerUp.x + powerUp.width - 5,
            top: powerUp.y + (powerUp.height / 2) - 2,
            fill: 'red',
            radius: 2
        });

        projectile2 = new fabric.Circle({
            left: powerUp.x + powerUp.width - 10,
            top: powerUp.y + (powerUp.height / 2) - 2,
            fill: 'red',
            radius: 2
        });

        projectile3 = new fabric.Circle({
            left: powerUp.x + powerUp.width - 15,
            top: powerUp.y + (powerUp.height / 2) - 2,
            fill: 'red',
            radius: 2
        });

        return new fabric.Group([background, projectile1, projectile2, projectile3], {
            left: powerUp.x,
            top: powerUp.y
        });
    }
};