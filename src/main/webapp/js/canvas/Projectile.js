var Projectile =  {

    drawProjectile : function(projectile) {

        // IF radius is bigger than laser shoots radius of 2
        if(projectile.height === projectile.width) {
            return new fabric.Circle({
                left: projectile.x,
                top: projectile.y,
                fill: 'red',
                radius: projectile.height
            });
        } else {
            return new fabric.Rect({
                left: projectile.x,
                top: projectile.y,
                fill: 'red',
                width: projectile.width,
                height: projectile.height
            });
        }

    }
};
