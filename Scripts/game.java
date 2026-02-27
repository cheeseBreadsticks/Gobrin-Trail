import Disasters.*;

public class Game {
  public static void start() {
    System.out.println("Welcome to the Gobrin Trail!");
    System.out.println("Your goal is to travel safely across the Gobrin Ice and find freedom in Karhide, 840 miles away!");
    System.out.println("You must manage your resources wisely and make strategic decisions to survive the harsh conditions of the Gobrin Ice.");
    System.out.println("Along the way, you will encounter various obstacles and disasters");
    System.out.println("Good luck on the ice!");
  }

  public static void pickRandDisaster(double disasterMult) {
    double rand = Math.random();
    // return disaster;
  }

  public static void main(String[] args) {
    start();
  }

  public static void forward(String t) { //game forward
    double dtrav = 20;
    double e = Math.random();
    if (t.equals("good")) {
      dtrav += 5; //good terrain
      System.out.println("The terrain is especially good, allowing for swifter traveling.");
    }
    if (t.equals("neutral")) {
      System.out.println("The terrain is as to be expected, allowing for regular travelling.");
    }
    if (t.equals("bad")) {
      dtrav -= 3; //bad terrain
      System.out.println("The terrain is especially poor today, causing slower traveling.");
    }
    if (e < 0.4) { //btw if you don't like the values for randomness/distance decreased, just say smth
      if (e > 0.845) {
        dtrav -= 5; //crevasse, takes longer to go around and such
        System.out.println("A deep crevasse blocks your path, requiring the group to travel around it.");
        //maybe percent chance that one person takes damage : I added a person class with health
      }
      else if (e > 0.69) {
        dtrav /= 2; //flat light, can't see and progress is extremely slow
        System.out.println("Overcast weather interferes with visibility, requiring extra careful traveling.");
      }
      else if (e > 0.535) {
        dtrav -= 7; //snow storm
        System.out.println("Strong snow storms hinder progress and cause the group to quickly drain of energy.");
      }
      else if (e > 0.38) {
        dtrav -= 4; //bad map, go in wrong direction
        System.out.println("While traveling you realize the map has led you in the wrong direction, requiring a reroute.");
        //can also make the chances variable, i.e. depending on quality of map this could be rarer
      }
      else if (e > 0.225) {
        dtrav -= 5; //avalanche, maybe blocks path or causes them to pause from caution
        System.out.println("A sudden avalance falling down the mountain blocks your path and requires a reroute.");
      }
      else if (e > 0.07) {
        dtrav -= 8; //frostbite stops them early could also make this a random amount decrease
        System.out.println("___ suddenly falters due to frostbite on their face, requiring a lengthy delay.");
        //frostbite, maybe take damage again
      }
      else if (e > 0.04) {
        dtrav = 0; //snow storm preventing travel, could rig to be towards the end of the journey
        System.out.println("An extremely dangerous snow storm ravages the ice, preventing travel of any sort.");
        //could make this last longer than one day
      }
      else if (e > 0.01) {
        dtrav /= 2; //volcanic eruption, smoke and such messing with them
        System.out.println("All of a sudden, Mount Dremegole begins to erupt, slowing progress and eventually preventing progress"); //or Drumner
        //maybe specific to travel distance so far, could cause them to not be able to surpass a specific distance for the day
      }
      else {
        System.out.println("You see weird guy in snowstorm he mad cool and heal you.");
        //special snowstorm, maybe gain heatlh(?) literally one percent chance so maybe gives food and full heal
        //idk what to write for this
      }
    }
  }
}
