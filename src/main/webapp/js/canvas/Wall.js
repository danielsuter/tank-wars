var Wall = {
    drawWall : function(wall) {
        return new fabric.Rect({
            left: wall.x,
            top: wall.y,
            fill: 'rgb(134,134,136)',
            width: wall.width,
            height: wall.height
        });
    }
};