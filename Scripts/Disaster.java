package Scripts;

public class Disaster {
    private double weight; // chance for disaster to happen each day, 0-1;
    private double distMod; // percentage 1 to -1, -1 is can't move, 1 is double speed, mult with other disasters/events
    private int duration; // blizzard length in days
    private double compoundChance; //chance for other disasters to happen. 0 = none, 1 = normal, 2 = double, etc.
    private boolean active;
    public Disaster(double w, double dm, int d, double cc) {
        weight = w;
        distMod = dm;
        duration = d;
        active = false;
    }
}
