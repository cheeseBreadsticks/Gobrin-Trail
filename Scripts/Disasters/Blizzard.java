package Disasters;
public class Blizzard {
    private double weight = 0.05f; // chance for disaster to happen each day, 0-1;
    private double distMod; // percentage 1 to -1, -1 is can't move, 1 is double speed, mult with other disasters/events
    private int duration; // blizzard length in days
    private double compoundChance; //chance for other disasters to happen. 0 = none, 1 = normal, 2 = double, etc.
    public Blizzard() {
        this.weight = 0.05f;
        this.distMod = 0.2f; 
        this.duration = 6;
        this.compoundChance = 0.5f;
    }
}
