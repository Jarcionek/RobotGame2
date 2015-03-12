package robotgame.game;

public class Bonus {

    private final Attribute attribute;
    private final int delta;

    public Bonus(Attribute attribute, int delta) {
        this.attribute = attribute;
        this.delta = delta;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int getDelta() {
        return delta;
    }

}
