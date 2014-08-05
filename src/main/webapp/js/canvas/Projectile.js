var Projectile =  {

    drawProjectile : function(projectile) {

        if(projectile.radius > 0) {
            return new fabric.Circle({
                left: projectile.x,
                top: projectile.y,
                fill: 'red',
                radius: projectile.radius
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
