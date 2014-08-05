var HealthBar = {
    /**
     *
     * @param tank {Tank}
     * @returns {fabric.Group}
     */
    drawHealthBar : function (tank) {
        var bar;
        var health;

        var maxHealth = 100;

        var calculateHealth = function (tank) {
            return tank.health / maxHealth * tank.height;
        };

        bar = new fabric.Rect({
            left: 0,
            top: 0,
            fill: 'black',
            width: 3,
            height: tank.height
        });

        health = new fabric.Rect({
            left: 0,
            top: 0,
            fill: '#66FF33',
            width: 3,
            height: calculateHealth(tank)
        });

        var group = new fabric.Group([bar, health], {
            left: tank.x-10,
            top: tank.y
        });



        /**
         * @param tank {Tank}
         */
        group.updateLocation = function(tank) {
            this.set({"left" : tank.x-10});
            this.set({"top" : tank.y});

            this.item(1).set({
                height: calculateHealth(tank)
            });
        };

        return group;
    }

};