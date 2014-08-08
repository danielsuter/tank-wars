var Ground = {

    drawGround: function (canvas) {

        var imgElement = document.getElementById('dirtImg');
        var scaleFactorX = canvas.width / imgElement.width;
        var scaleFactorY = canvas.height / imgElement.height;

        var imgInstance = new fabric.Image(imgElement, {
            left: canvas.x,
            top: canvas.y,
            scaleX : scaleFactorX,
            scaleY : scaleFactorY
        });

        return imgInstance;

    }
};