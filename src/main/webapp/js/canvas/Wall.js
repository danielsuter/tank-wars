var Wall = {
    drawWall : function(wall) {

        var imgElement = document.getElementById('wallImg');
        var scaleFactorX = wall.width / imgElement.width;
        var scaleFactorY = wall.height / imgElement.height;

        var imgInstance = new fabric.Image(imgElement, {
            left: wall.x,
            top: wall.y,
            scaleX : scaleFactorX,
            scaleY : scaleFactorY
        });

        return imgInstance;
    }
};