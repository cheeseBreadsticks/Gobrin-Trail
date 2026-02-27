// import Scripts.*;
//100 off ice, frostbite, bad map
//600 ice - crevasse, snowstorm, volcano, flat light, frostbite, bad map, avalanche
//140 bay - town, snowstorms, flat light, frostbite, bad map, 
public class game {
  private static double dtrav = 12.0;
  private final static double distance = 840.0;
  private static double distanceleft = 840.0;
  private static String biome = "o"; //o = orgoreyn, i = ice, b = bay of Guthen
  private static String b = "U+1F514";
  private static String[] activeDisasters;
  public static void start() {
    System.out.println("Welcome to the Gobrin Trail!");
    System.out.println("Your goal is to travel safely across the Gobrin Ice and find freedom in Karhide, 840 miles away!");
    System.out.println("You must manage your resources wisely and make strategic decisions to survive the harsh conditions of the Gobrin Ice.");
    System.out.println("You start with 2x Backpacks.");
    storage b1 = new storage("Backpack", 100);
    storage b2 = new storage("Backpack", 100); //backpacks
    System.out.println("Along the way, you will encounter various obstacles and disasters");
    System.out.println("Good luck on the ice!");
  }

  //list of supplies a few pages into chapter 15
  //orsh, gitchy-mitchy, kadik-germ, dried breadapple, red sugar, sleeping bags, clothes, skis, sledge, qualities for each equipment (good, avg, poor)
  //in storry Estraven bought good qual everything & stole food
  //gichy-michy req 1lb/day
  public static void shop() {
    System.out.println(" ______________  __________");
    System.out.println("| Stove: 1800₾ || Tent: ");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾‾‾  ‾‾‾‾‾‾‾‾‾‾");
    System.out.println();
    System.out.println();

  }

  public static double changeDistMult(double disasterMult, int mapQuality) {
    // TODO: use for loop w/ maxdisasters parameter to roll for extra disasters.

    double mult = 1.0;
    double compoundChance = disasterMult;
    double rand = Math.round(Math.random() * 100)/100.0;
    if (mapQuality == 0) {
      //bad map
      if (rand< 0.7) {
        mult *= 0.9;
        activeDisasters[0] = "Lost";
      }
    } else if (mapQuality == 1)  {
      //average map
      if (rand < 0.25) {
        mult *= 0.9;
        activeDisasters[0] = "Lost";
      }
    }
    if (biome.equals("o")) { //orgoreyn
      if (rand < 0.1) {
        //flat light
        mult *= 0.8;
        compoundChance *= 0.1;
        activeDisasters[1] = "Flat Light";
      }
      else if (rand < 0.2) {
        //snow storm
        mult *= 0.7;
        compoundChance *= 0.1;
        activeDisasters[1] = "Snowstorm";
      }
      else if (rand < 0.3) {
        
      }
      else if (rand < 0.4) {
        
      }
      else if (rand < 0.5) {
      }
      else if (rand < 0.6) {
        //frostbite
      }
      // available disasters: crevasse, flat light, snow storm, bad map, avalance, frostbite, extreme snow storm, special snow storm
    } else if (biome.equals("i") ) { //ice

    } else if (biome.equals("b")) { //bay of Guthen

    }
    return mult;
  }

  public static double terrain() {
    double e = Math.random();
    if (biome.equals("o")) { //orgoreyn
      if (e < (1/3)) {
        return 8.0; //good terrain
      }
      else if (e < (2/3)) {
        return 3.0; //neutral
      }
      else {
        return -3.0; //bad terrain
      }
    }
    else if (biome.equals("i")) //ice 
    {
      if (e < (1/6)) {
        return 6.0; //good terrain
      }
      else if (e < (5/6)) {
        return -1.5; //neutral
      }
      else {
        return -4.0; //bad terrain
      }
    }
    else if (biome.equals("b")) //bay of Guthen
    {
      if (e < (5/12)) {
        return 4.0; //good terrain
      }
      else if (e < (1/2)) {
        return 0.0; //neutral
      }
      else {
        return -2.0; //bad terrain
      }
    }
    return 0.0;
  }

  public static void main(String[] args) {
    start();
  }

  public static void forward(String t) { //game forward
    dtrav = 12;
    dtrav += terrain();
  }
}
