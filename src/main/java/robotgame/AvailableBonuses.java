package robotgame;

public class AvailableBonuses extends WeightedList<String> {

    public AvailableBonuses(RandomNumberGenerator randomNumberGenerator) {
        super(randomNumberGenerator);
        add(15, "02AP");
        add(8, "03AP");
        add(5, "05AP");
        add(1, "10AP");
        add(15, "05HP");
        add(8, "10HP");
        add(5, "15HP");
        add(3, "20HP");
        add(1, "80HP");
        add(5, "01endurance");
        add(4, "02endurance");
        add(3, "03endurance");
        add(1, "05endurance");
        add(5, "01speed");
        add(4, "02speed");
        add(3, "03speed");
        add(1, "05speed");
        add(5, "01attack");
        add(4, "02attack");
        add(3, "03attack");
        add(1, "05attack");
    }

}
