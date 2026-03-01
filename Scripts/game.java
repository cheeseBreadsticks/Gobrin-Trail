package Scripts;
//100 off ice, frostbite, bad map
//600 ice - crevasse, snowstorm, volcano, flat light, frostbite, bad map, avalanche
//140 bay - town, snowstorms, flat light, frostbite, bad map, 
import java.util.Scanner;
import java.util.ArrayList;

public class Game {
  //making items so its easier to print stuff out and such just a bunch of variables
  private static Item stove = new Item(1800, "Chabe Stove", false);
  private static Item tent = new Item(1000, "Polyskin Tent", false);
  private static Item sbags = new Item(500, "Sleeping Bags", false); //boolean is stackable or not
  private static Item skis = new Item(250, "Skis", false); //i think bags and skis you just auto buy for both
  private static Item gm = new Item(25, "Gichy-michy", true); //25 per pound, 80 days, 2000 money for all the food you need
  private static Item kgerm = new Item(35, "Kadik-germ", true);
  private static Item bapple = new Item(75, "Dried breadapple", true); //maybe more expensive but this makes them happy or smth
  private static Item sugar = new Item(50, "Sugar", true);
  private static Item fakit = new Item(400, "First Aid Kit", true); //maybe add stack limit?
  private static Item map = new Item(650, "Map", false);
  private static Item backpack = new Item(850, "Backpack", true);
  private static Item orsh = new Item(100, "Orsh", true); //seems to have like healing properties, so maybe more expensive?

  //THIS IS IMPORTANT
  private static Scanner scan = new Scanner(System.in);

  private static double dtrav = 12.0;
  private final static double distance = 840.0;
  private static double distanceleft = 840.0;
  private static String biome = "o"; //o = orgoreyn, i = ice, b = bay of Guthen
  private static String b = "U+1F514";
  private static ArrayList<String> activeDisasters = new ArrayList<String>();
  public static void start() {
    System.out.println("Welcome to the Gobrin Trail!");
    System.out.println("Your goal is to travel safely across the Gobrin Ice and find freedom in Karhide, 840 miles away!");
    System.out.println("You must manage your resources wisely and make strategic decisions to survive the harsh conditions of the Gobrin Ice.");
    System.out.println("You start with 2x Backpacks.");
    //sledge still should be ten times, but it said in the book that backpacks < 30lbs, sledge > 300lbs
    //maybe storage isnt limit, but makes travel slower with diff limit as hard cap
  Storage b1 = new Storage("Backpack", 30);
  Storage b2 = new Storage("Backpack", 30); //backpacks
    System.out.println("Along the way, you will encounter various obstacles and disasters");
    System.out.println("Good luck on the ice!");
  }

  //list of supplies a few pages into chapter 15
  //orsh, gitchy-mitchy, kadik-germ, dried breadapple, red sugar, sleeping bags, clothes, skis, sledge, qualities for each equipment (good, avg, poor)
  //in story Estraven bought good qual everything & stole food
  //gichy-michy req 1lb/day
  public static void displayShop() {
    System.out.println(" ____________ _____________ ____________ _______________ ________");
    System.out.println("| Specialty: |    Stove    |    Tent    | Sleeping Bags |  Skis  |");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾ ");
    System.out.println(" ____________ _____________ ____________ _______________ ________");
    System.out.println("|    Food:   | Gichy-michy | Kadik-germ |   Breadapple  |  Sugar |");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾");
    System.out.println(" ____________ _____________ ____________ _______________ ________");
    System.out.println("|    Other:  |  First Aid  |     Map    |    Backpack   |  Orsh  |");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾");

    String buy = scan.nextLine();
  }

public static void findProduct(String p) {
  p.toLowerCase();
  if (p.equals("stove")) {
    dispPrice(stove.getc());
  }
  else if (p.equals("tent")) {
    dispPrice(tent.getc());
  }
  else if (p.equals("sleeping bags")) {
    dispPrice(sbags.getc());
  }
  else if (p.equals("skis")) {
    dispPrice(skis.getc());
  }
  else if (p.equals("gichy michy") || p.equals("gichy-michy")) {
    dispPrice(gm.getc());
  }
  else if (p.equals("kadik germ") || p.equals("kadik-germ")) {
    dispPrice(kgerm.getc());
  }
  else if (p.equals("breadapple") || p.equals("bread apple")) {
    dispPrice(bapple.getc());
  }
  else if (p.equals("sugar")) {
    dispPrice(sugar.getc());
  }
  else if (p.equals("first aid kit")) {
    dispPrice(fakit.getc());
  }
  else if (p.equals("map")) {
    dispPrice(map.getc());
  }
  else if (p.equals("backpack")) {
    dispPrice(backpack.getc());
  }
  else if (p.equals("orsh")) {
    dispPrice(orsh.getc());
  }
  else {
    displayShop();
  }
}

  public static void dispPrice(int price) {
    int bapr = (price * 3)/4;
    int gopr = (int)(price * 1.25);
    int pric = price; //I need it to be 4 dig or lower cuz the tables gonna look weird
    if (Integer.toString(price).length() == 4) {
      System.out.println(" __________ _____________ ______________ ______________");
      System.out.println("| Quality: |     Bad     |     Okay     |     Good     |");
      System.out.println(" ‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
      System.out.println(" __________ _____________ ______________ ______________");
      System.out.println("|  Price:  |     "+bapr+"     |     "+pric+"     |     "+gopr+"     |"); //it looks messed up but it should be fine
      System.out.println(" ‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
    }
  else if (Integer.toString(price).length() == 3) {
      System.out.println(" __________ _____________ ______________ ______________");
      System.out.println("| Quality: |      Bad    |      Okay    |      Good    |");
      System.out.println(" ‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
      System.out.println(" __________ _____________ ______________ ______________");
      System.out.println("|  Price:  |      "+bapr+"     |      "+pric+"     |      "+gopr+"     |");
      System.out.println(" ‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
    }
  else if (Integer.toString(price).length() == 2) {
      System.out.println(" __________ _____________ ______________ ______________");
      System.out.println("| Quality: |     Bad     |      Okay    |      Good    |");
      System.out.println(" ‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
      System.out.println(" __________ _____________ ______________ ______________");
      System.out.println("|  Price:  |      "+bapr+"      |       "+pric+"     |       "+gopr+"     |"); //it looks messed up but it should be fine I can't test
      System.out.println(" ‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
    }
  }

  public static void purchaseItem(Item i, String quality, int quantity) {
    int q;
    if (quantity <= 0) {
      quantity = 1;
    }
    if (quality == "good") {
      q = 2;
    } else if (quality == "avg") {
      q = 1;
    } else if (quality == "poor" || quality == "bad") {
      q = 0;
    } else {
      q = 1;
    }
  }

  public static double changeDistMult(double disasterMult, int mapQuality) {
    // TODO: use for loop w/ maxdisasters parameter to roll for extra disasters.

    double mult = 1.0;
    double compoundChance = disasterMult;
    //might want to not round decimals (at least ccRoll)
    double rand = Math.round(Math.random() * 10000)/10000.0; //random number between 0 and 1, rounded to 4 decimal places
    double ccRoll = Math.round(Math.random() * 10000)/10000.0; //see above, but used for compound chance roll
    if (mapQuality == 0) {
      //bad map
      if (rand< 0.7) {
        mult *= 0.9;
        activeDisasters.add("Lost");
      }
    } else if (mapQuality == 1)  {
      //average map
      if (rand < 0.25) {
        mult *= 0.9;
        activeDisasters.add("Lost");
      }
    } else {
      //good map
      mult *= 1.01;
      activeDisasters.add("On Track");
    }
    if (biome.equals("o")) { //orgoreyn
      if (ccRoll < compoundChance) { //ccroll btwn 0 & 1, but starts at 1 so always passes 1st time
        if (rand < 0.05) {
          //flat light
          if (!activeDisasters.contains("Flat Light")) {
            mult *= 0.8;
            compoundChance *= 0.1;
            activeDisasters.add("Flat Light");
          }
        }
        else if (rand < 0.15) {
          //snow storm
          if (!activeDisasters.contains("Snowstorm")) {
            mult *= 0.7;
            compoundChance *= 0.1;
            activeDisasters.add("Snowstorm");
          }
        }
        else if (rand < 0.175) {
          //eruption (volacno)
          if (!activeDisasters.contains("Volcano")) {
            mult *= 0.5;
            compoundChance *= 0.1;
            activeDisasters.add("Volcano");
          }
        }
        else if (rand < 0.25 && !activeDisasters.contains("Flat Light")) {
          // trips = day ends early (duration 1)
          activeDisasters.add("Tripped ");
        }
        else if (rand < 0.5 && activeDisasters.contains("Flat Light")) {
          // increased chance w/ flat light
          activeDisasters.add("Tripped");
        }
        else if (rand < 0.6) {
          //frostbite
        }
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
