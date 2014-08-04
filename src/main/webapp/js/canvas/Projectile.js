var Projectile = function() {

    var drawProjectile = function(projectile) {
        vreturn = new fabric.Circle({
            left: projectile.x,
            top: projectile.y,
            fill: 'red',
            radius: projectile.width
        });
    };
};
