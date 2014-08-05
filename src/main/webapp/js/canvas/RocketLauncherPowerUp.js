var RocketLauncherPowerUp = {

    drawRocketLauncherPowerUp: function (powerUp) {

        var background;
        var projectile;

        background = new fabric.Rect({
            left: powerUp.x,
            top: powerUp.y,
            fill: 'brown',
            width: powerUp.width,
            height: powerUp.height
        });

        projectile = new fabric.Circle({
            left: powerUp.x + powerUp.width - 12,
            top: powerUp.y + (powerUp.height / 2) - 4,
            fill: 'red',
            radius: 4
        });

        return new fabric.Group([background, projectile], {
            left: powerUp.x,
            top: powerUp.y
        });
    }
};