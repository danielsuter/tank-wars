var ShapeRenderer = function(_canvas) {

    var shapes = [];
    var canvas = _canvas;

    this.createShape = function(actor) {
        var shape;
        switch (actor.actorType) {
            case "TANK":
                shape = Tank.drawTank(actor);
                break;
            case "PROJECTILE":
                shape = Projectile.drawProjectile(actor);
                break;
            case "WALL":
                shape = Wall.drawWall(actor);
        }

        shapes[actor.id] = shape;
        canvas.add(shape);
    };

    this.updateShape = function(actor) {
        if (!actor.x && !actor.y) {
            return;
        }

        var shape = shapes[actor.id];

        if (!shape) {
            throw "This shape does not exist, cannot update it!";
        }

        if (actor.x) {
            shape.set({"left" : actor.x});
        }

        if (actor.y) {
            shape.set({"top" : actor.y});
        }

        shape.setCoords();
    };

    this.removeShape = function(id) {
        var shape = shapes[id];
        canvas.remove(shape);
        shapes[id] = undefined;
    };

    this.render = function() {
        canvas.renderAll();
    };

};