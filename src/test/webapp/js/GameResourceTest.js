describe("Test converting protocol to view object", function() {
    it("should correctly map the protocol to a view object", function() {
        var expectedResult = [
            {
                actorType: "TANK",
                id: 1,
                x: 15,
                y: 15,
                width: 20,
                height: 20,
                direction: "N",
                velocity: 10
            }
        ];

        var onFinished = function(actors) {
            expect(expectedResult).toEqual(actors);
        };

        var resource = new GameResource(onFinished);

        var event = {
            data : '[0,{"t":0,"i":1,"x":15,"y":15,"w":20,"h":20,"d":"N","v":10}]'
        };

        resource.onMessage(event);
    });
});