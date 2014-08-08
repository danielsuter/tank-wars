var HealthBar = {
    /**
     *
     * @param tank {Tank}
     * @returns {fabric.Group}
     */
    drawHealthBar : function (tank, playerName) {
        var bar;
        var health;
        var playerName;

        var maxHealth = 100;

        var calculateHealth = function (tank) {
            return tank.health / maxHealth * tank.height;
        };

        playerName = new fabric.Text(playerName, {
            left: 0,
            fontSize: 10,
            fill: 'black',
            top: 0
        });

        bar = new fabric.Rect({
            left: 0,
            top: 14,
            fill: 'black',
            width: tank.width,
            height: 4
        });

        health = new fabric.Rect({
            left: 0,
            top: 14,
            fill: '#66FF33',
            width: calculateHealth(tank),
            height: 4
        });

        var group = new fabric.Group([bar, health, playerName], {
            left: tank.x,
            top: tank.y - 20
        });



        /**
         * @param tank {Tank}
         */
        group.updateLocation = function(tank) {
            var deltaX;
            switch (tank.direction) {
                case "W":
                    deltaX = 4;
                    break;
                case "N":
                case "S":
                case "E":
                    deltaX = 0;
                    break;
            }

            this.set({"left" : tank.x + deltaX});
            this.set({"top" : tank.y - 20});

            this.item(1).set({
                width: calculateHealth(tank)
            });
        };

        return group;
    }

};