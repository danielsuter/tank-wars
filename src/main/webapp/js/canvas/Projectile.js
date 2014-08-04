var Projectile = function() {

    this.drawProjectile = function(projectile) {
        return new fabric.Circle({
            left: projectile.x,
            top: projectile.y,
            fill: 'red',
            radius: projectile.dimension
        });
    };
};
