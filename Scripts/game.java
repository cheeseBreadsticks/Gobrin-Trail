import Disasters.*;
import wingdings.ttf;
//100 off ice, frostbite, bad map
//600 ice - crevasse, snowstorm, volcano, flat light, frostbite, bad map, avalanche
//140 bay - town, snowstorms, flat light, frostbite, bad map, 
public class game {
  private static double dtrav = 12.0;
  private static double distance = 840.0;
  private static double distanceleft = 840.0;
  private static String biome;
  public static void start() {
    System.out.println("Welcome to the Gobrin Trail!");
    System.out.println("Your goal is to travel safely across the Gobrin Ice and find freedom in Karhide, 840 miles away!");
    System.out.println("You must manage your resources wisely and make strategic decisions to survive the harsh conditions of the Gobrin Ice.");
    System.out.println("Along the way, you will encounter various obstacles and disasters");
    System.out.println("Good luck on the ice!");
  }

  public static void shop() {
    System.out.println("__________");
    System.out.println("|Stove: ");
    System.out.println();
    System.out.println();
    System.out.println();

  }

  public static void pickRandDisaster(double disasterMult) {
    double rand = Math.random();
    // return disaster;
  }

  public static double terrain() {
    double e = Math.random();
    if (biome.equals("o")) { //orgoreyn
      if (e < (1/3)) {
        return 8; //good terrain
      }
      else if (e < (2/3)) {
        return 3; //neutral
      }
      else {
        return -3; //bad terrain
      }
    }
    else if (biome.equals("i")) //ice 
    {
      if (e < (1/6)) {
        return 6; //good terrain
      }
      else if (e < (5/6)) {
        return -1.5; //neutral
      }
      else {
        return -4; //bad terrain
      }
    }
    else if (biome.equals("b")) //bay of Guthen
    {
      if (e < (5/12)) {
        return 4; //good terrain
      }
      else if (e < (1/2)) {
        return 0; //neutral
      }
      else {
        return -2; //bad terrain
      }
    }
  }

  public static void main(String[] args) {
    start();
  }

  public static void forward(String t) { //game forward
    dtrav = 12;
    dtrav += terrain();
  }
