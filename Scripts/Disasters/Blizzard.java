package Disasters;
public class Blizzard {
    private float distMod; // percentage 1 to -1, -1 is can't move, 1 is double speed, mult with other disasters/events
    private int duration; // blizzard length in days
    private float compoundChance; //chance for other disasters to happen. 0 = none, 1 = normal, 2 = double, etc.
    public Blizzard() {
        this.distMod = 0.2f; 
        this.duration = 6;
        this.compoundChance = 0.5f;
    }
}
