var ShapeRenderer = function(_canvas) {

    var shapes = [];
    var canvas = _canvas;

    var statusBarShape;

    this.createShape = function(actor) {
        var shape;
        switch (actor.actorType) {
            case "TANK":
                actor.color = getColor();
                shape = Tank.drawTank(actor);
                shape.direction = actor.direction;
                break;
            case "PROJECTILE":
                shape = Projectile.drawProjectile(actor);
                break;
            case "WALL":
                shape = Wall.drawWall(actor);
                break;
            case "HealthPowerUp":
                shape = HealthPowerUp.drawHealthPowerUp(actor);
                break;
            case "FireRatePowerUp":
                shape = FireRatePowerUp.drawFireRatePowerUp(actor);
                break;
        }

        // optimize
        if(shape) {
            shape.hasRotatingPoint = false;
            shape.selectable = false;
            shapes[actor.id] = shape;
            canvas.add(shape);
        }

    };

    var getColor = function() {
        return '#'+Math.floor(Math.random()*16777215).toString(16);
    };

    this.updateShape = function(actor) {
        var shape = shapes[actor.id];

        if (!shape) {
            throw "This shape does not exist, cannot update it!";
        }

        shape.set({"left" : actor.x});
        shape.set({"top" : actor.y});

        shape.setCoords();
    };

    this.removeShape = function(id) {
        var shape = shapes[id];
        canvas.remove(shape);
        delete shapes[id];
    };

    this.renderDeath = function() {
        statusBarShape = new fabric.Text('<<< Game over >>>', {
            left: 110,
            top: 250,
            fontSize: 60,
            fill: 'red',
            stroke: '#c3bfbf',
            strokeWidth: 3,
            fontFamily: 'Arial',
            fontWeight: 'bold'});

        // optimize
        statusBarShape.selectable = false;
        statusBarShape.hasRotatingPoint = false;

        canvas.add(statusBarShape);
    };

    this.render = function() {
        canvas.renderAll();
    };

    var generateStatusbarText = function(player) {
        return 'Health:' + player.health + '   Fire rate: ' + player.fireRate;
    };

    this.renderStatusBar= function(player) {
        if(!statusBarShape) {
            statusBarShape = new fabric.Text(generateStatusbarText(player), {
                left: 10,
                top: 560,
                fontSize: 20,
                fill: 'white',
                fontFamily: 'Arial',
                fontWeight: 'bold'});

            // optimize
            statusBarShape.selectable = false;
            statusBarShape.hasRotatingPoint = false;

            canvas.add(statusBarShape);
        }
        statusBarShape.setText(generateStatusbarText(player));
    };
};