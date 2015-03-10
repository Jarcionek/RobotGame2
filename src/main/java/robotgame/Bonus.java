package robotgame;

public class Bonus {

    private final Attribute attribute;
    private final int modifier;

    public Bonus(Attribute attribute, int modifier) {
        this.attribute = attribute;
        this.modifier = modifier;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int getModifier() {
        return modifier;
    }

}
