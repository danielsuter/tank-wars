var HealthPowerUp =  {

    drawHealthPowerUp : function(powerUp) {

        var background;
        var healthCrossX;
        var healthCrossY;

        background = new fabric.Rect({
            left: powerUp.x,
            top: powerUp.y,
            fill: 'white',
            width: powerUp.width,
            height: powerUp.height
        });

        healthCrossX = new fabric.Rect({
            left: powerUp.x + (powerUp.width/ 2) - 2,
            top: powerUp.y + 3,
            fill: 'red',
            width: 4,
            height: powerUp.height - 6
        });

        healthCrossY = new fabric.Rect({
            left: powerUp.x + 3,
            top: powerUp.y + (powerUp.height/ 2)- 2,
            fill: 'red',
            width: powerUp.width - 6,
            height: 4
        });

        return new fabric.Group([background, healthCrossX, healthCrossY], {
            left: powerUp.x,
            top: powerUp.y
        });
    }
};
