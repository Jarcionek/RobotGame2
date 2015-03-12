package robotgame.game;

import static robotgame.game.Attribute.AP;
import static robotgame.game.Attribute.ATTACK;
import static robotgame.game.Attribute.ENDURANCE;
import static robotgame.game.Attribute.HP;
import static robotgame.game.Attribute.SPEED;

public class AvailableBonuses extends WeightedList<Bonus> {

    public AvailableBonuses(RandomNumberGenerator randomNumberGenerator) {
        super(randomNumberGenerator);
        add(15, new Bonus(AP, 2));
        add(8, new Bonus(AP, 3));
        add(5, new Bonus(AP, 5));
        add(1, new Bonus(AP, 10));
        add(15, new Bonus(HP, 5));
        add(8, new Bonus(HP, 10));
        add(5, new Bonus(HP, 15));
        add(3, new Bonus(HP, 20));
        add(1, new Bonus(HP, 80));
        add(5, new Bonus(ENDURANCE, 1));
        add(4, new Bonus(ENDURANCE, 2));
        add(3, new Bonus(ENDURANCE, 3));
        add(1, new Bonus(ENDURANCE, 5));
        add(5, new Bonus(SPEED, 1));
        add(4, new Bonus(SPEED, 2));
        add(3, new Bonus(SPEED, 3));
        add(1, new Bonus(SPEED, 5));
        add(5, new Bonus(ATTACK, 1));
        add(4, new Bonus(ATTACK, 2));
        add(3, new Bonus(ATTACK, 3));
        add(1, new Bonus(ATTACK, 5));
    }

}
