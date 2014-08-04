var Wall = function() {

    this.drawWall = function(wall) {
        return new fabric.Rect({
            left: wall.x,
            top: wall.y,
            fill: 'yellow',
            width: wall.width,
            height: wall.height
        });
    };
};