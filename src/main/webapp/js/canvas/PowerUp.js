var PowerUp =  {

    drawHealthPowerUp : function(powerUp) {
        return new fabric.Rect({
            left: powerUp.x,
            top: powerUp.y,
            fill: 'red',
            width: powerUp.width,
            height: powerUp.height
        });
    }
};
