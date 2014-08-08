var Mine = {

    drawMine: function (mine) {

        var imgElement = document.getElementById('mineImg');
        var scaleFactor = mine.width / imgElement.width;

        var imgInstance = new fabric.Image(imgElement, {
            left: mine.x,
            top: mine.y,
            scaleX : scaleFactor,
            scaleY : scaleFactor
        });

        return imgInstance;

    }
};