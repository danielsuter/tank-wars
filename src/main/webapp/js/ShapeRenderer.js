var ShapeRenderer = function(_canvas) {

    var shapes = [];
    var canvas = _canvas;
    var availableColors = ['white', 'red', 'black', 'blue', 'orange'];

    this.createShape = function(actor) {
        var shape;
        switch (actor.actorType) {
            case "TANK":
                actor.color = getColor();
                shape = Tank.drawTank(actor);
                shape.direction = actor.direction;
                break;
            /*case "PROJECTILE":
                shape = Projectile.drawProjectile(actor);
                break;*/
            case "WALL":
                shape = Wall.drawWall(actor);
                break;
        }
        shapes[actor.id] = shape;
        canvas.add(shape);
    };

    var getColor = function() {
        // TODO: Render random colors:
        //  '#'+Math.floor(Math.random()*16777215).toString(16);
        return availableColors.splice(0, 1);
    };

    this.updateShape = function(actor) {
        if (!actor.x && !actor.y) {
            return;
        }

        var shape = shapes[actor.id];

        if (!shape) {
            return "This shape does not exist, cannot update it!";
        }

//        if (actor.direction && actor.direction !== shape.direction) {
//            var currentAngle = shape.getAngle();
//
//            switch (actor.direction) {
//                case "N":
//                    shape.setAngle(-90);
//                    break;
//                case "S":
//                    shape.setAngle(90);
//                    break;
//                case "W":
//                    shape.setAngle(-180);
//                    break;
//                default:
//                    shape.setAngle(0);
//            }
//        }

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