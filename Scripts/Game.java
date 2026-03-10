package Scripts;
//100 off ice, frostbite, bad map
//600 ice - crevasse, snowstorm, volcano, flat light, frostbite, bad map, avalanche
//140 bay - town, snowstorms, flat light, frostbite, bad map, 
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game {
  //making items so its easier to print stuff out and such just a bunch of variables
  //bool is stackable or not, //i think bags and skis you just auto buy for both, (Gichy-michy) //25 per pound, 80 days, 2000 money for all the food you need, (breadapple) //maybe more expensive but this makes them happy or smth, (first aid kit) //maybe add stack limit?, (map) //good map reduces chance of getting lost, bad map increases it, (orsh) //seems to have like healing properties, so maybe more expensive?
  //i changed it to array to look better
  private static ArrayList<Item> inventory = new ArrayList<Item>();
  private static int money = 5000;
  private static final int stoveCost = 1800, tentCost = 1000, sbagsCost = 500, sledgeCost = 750, gmCost = 25, kgermCost = 35, bappleCost = 75, orshCost = 100, fakitCost = 400, mapCost = 650, backpackCost = 850, skisCost = 100;
  private static final Item[] shop = {new Item(stoveCost, "Stove", false), new Item(tentCost, "Tent", false), new Item(sbagsCost, "Sleeping Bags", false), new Item(sledgeCost, "Sledge", false), new Item(gmCost, "Gichy-michy", true), new Item(kgermCost, "Kadik-germ", true), new Item(bappleCost, "Breadapple", true), new Item(orshCost, "Orsh", true), new Item(fakitCost, "First Aid Kit", true), new Item(mapCost, "Map", false), new Item(backpackCost, "Backpack", true), new Item(skisCost, "Skis", false)};
  private static String playerChar;
  private static int stashPrice = 0;
  private static String stashQual; // 1 = bad, 2 = okay, 3 = good

  //THIS IS IMPORTANT
  private static Scanner scan = new Scanner(System.in);

  private static double dtrav = 12.0;
  private static final double distance = 840.0;
  private static double distanceleft = 840.0;
  private static String biome = "o"; //o = orgoreyn, i = ice, b = bay of Guthen
  private static ArrayList<String> activeDisasters = new ArrayList<String>();

  public static void start() {
    UsefulMethods.clearTerminal();
    System.out.println("Welcome to the Gobrin Trail!\nYour goal is to travel safely across the Gobrin Ice and find freedom in Karhide, 840 miles away.");
    System.out.println("You must manage your resources wisely and make strategic decisions to survive the harsh conditions of the Gobrin Ice.");
    System.out.println("You start with 2x Backpacks, and must buy more items from the shop to survive your journey.\nAlong the way, you will encounter various obstacles and disasters.\nGood luck on the Ice!");
    //sledge still should be ten times, but it said in the book that backpacks < 30lbs, sledge > 300lbs
    //maybe storage isnt limit, but makes travel slower with diff limit as hard cap
    Storage b1 = new Storage("Backpack", 30);
    Storage b2 = new Storage("Backpack", 30); //backpacks
    charSelect();
    if (playerChar != null) {
      System.out.println("\n" + playerChar + " chosen. Press ENTER to begin.");
      String next = scan.nextLine();
      if (next.equals("")) {
        displayShop(true);
      }
    }
  }

  public static void charSelect() {
    System.out.println("\nPlease type the name of your character:\nEstraven\nGenly");
    String chara = scan.nextLine().toLowerCase();
    if (chara.equals("estraven") || chara.equals("estraven."))  {
      playerChar = "Estraven";
      return;
    }
    else if (chara.equals("genly") || chara.equals("genly.")) {
      playerChar = "Genly";
      return;
    } else {
      System.out.println("\nPlease pick a valid character.");
      charSelect();
    }
  }

  //list of supplies a few pages into chapter 15
  //orsh, gitchy-mitchy, kadik-germ, dried breadapple, red sugar, sleeping bags, clothes, skis, sledge, qualities for each equipment (good, avg, poor)
  //in story Estraven bought good qual everything & stole food
  //gichy-michy req 1lb/day
  public static void displayShop(boolean valid) {
    UsefulMethods.clearTerminal();
    System.out.println("Welcome to the shop! Here are the items available for purchase:");
    System.out.println(" ____________ _____________ ____________ _______________ __________");
    System.out.println("| Specialty: |    Stove    |    Tent    | Sleeping Bags |  Sledge  |");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾ ");
    System.out.println(" ____________ _____________ ____________ _______________ __________");
    System.out.println("|    Food:   | Gichy-michy | Kadik-germ |   Breadapple  |   Orsh   |");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾");
    System.out.println(" ____________ _____________ ____________ _______________ __________");
    System.out.println("|   Other:   |  First Aid  |     Map    |    Backpack   |   Skis   |");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾");
    System.out.println(" ____________");
    System.out.println("|  Continue  |");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾");
    System.out.println("Money: " + money);
    if (valid) {
      System.out.println("What would you like to buy?");
    } else {
      System.out.println("Please pick a valid item.");
    }
    String buy = scan.nextLine();
    buy = buy.toLowerCase();
    if (buy.equals("continue")) {
      System.out.println("1");
      shteal();
      return;
    }
    UsefulMethods.clearTerminal();
    findProduct(buy);
  }

  public static void findProduct(String p) {
    System.out.println("Money: " + money);
    System.out.println("\nWhat quality " + UsefulMethods.capitalize(p) + " would you like to buy?");
    Item it = null;
    p = p.toLowerCase();
    for (int i = 0; i < shop.length; i ++) {
      if (p.equals(shop[i].getn().toLowerCase())) {
        it = shop[i];
        break;
      }
    }
    if (p.equals("gichy michy")) {
      it = shop[4];
    } else if (p.equals("kadik germ")) {
      it = shop[5];
    } else if (p.equals("bread apple")) {
    it = shop[6];
    } else if (p.equals("first aid kit") || p.equals("first-aid") || p.equals("first-aid kit")) {
      it = shop[8];
    }
    if (it == null) {
      if (p.equals("continue")) {
        System.out.println("2");
        shteal();
      }
      displayShop(false);
    }
    else {
      dispPrice(it.getc(), it, false);
    }
  }

  public static void dispPrice(int price, Item i, boolean jumpQuant) {
    int p;
    String quality;
    if (!jumpQuant) {
      int bapr = playerChar.equals("Estraven") ? (int)(price * 3)/4 : (int)(((price*3)/4)*0.95);
      int gopr = playerChar.equals("Estraven") ? (int)(price * 1.25) : (int)((price*1.25)*0.95);
      int pric = playerChar.equals("Estraven") ? (int)price : (int)price; //I need it to be 4 dig or lower cuz the tables gonna look weird
      //this should work no matter price length
      System.out.println(" __________ _____________ ______________ ______________");
      System.out.println("| Quality: |     Bad     |     Okay     |     Good     |");
      System.out.println(" ‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
      System.out.println(" __________ _____________ ______________ ______________");
      System.out.print("|  Price:  |");
      for (int k = 0; k < (13 - Integer.toString(bapr).length())/2; k++) {
        System.out.print(" ");
      }
      System.out.print(bapr);
      if (Integer.toString(bapr).length() % 2 == 0) {
        System.out.print(" ");
      }
      for (int k = 0; k < (13 - Integer.toString(bapr).length())/2; k++) {
        System.out.print(" ");
      }
      System.out.print("|");
      for (int k = 0; k < (14 - Integer.toString(pric).length())/2; k++) {
        System.out.print(" ");
      }
      System.out.print(pric);
      if (Integer.toString(pric).length() % 2 == 1) {
        System.out.print(" ");
      }
      for (int k = 0; k < (14 - Integer.toString(pric).length())/2; k++) {
        System.out.print(" ");
      }
      System.out.print("|");
      for (int k = 0; k < (14 - Integer.toString(gopr).length())/2; k++) {
        System.out.print(" ");
      }
      System.out.print(gopr);
      if (Integer.toString(gopr).length() % 2 == 1) {
        System.out.print(" ");
      }
      for (int k = 0; k < (14 - Integer.toString(gopr).length())/2; k++) {
        System.out.print(" ");
      }
      System.out.println("|");
      System.out.println(" ‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾");

      quality = scan.nextLine();
      p = 0;
      quality = quality.toLowerCase();
      if (quality.equals("good")) {
        p = gopr;
      }
      else if (quality.equals("okay")) {
        p = pric;
      }
      else if (quality.equals("bad")) {
        p = bapr;
      }
      else {
        UsefulMethods.clearTerminal();
        System.out.println("Money: " + money);
        System.out.println("\nPlease input a valid quality.");
        dispPrice(price, i, false);
        return;
      }
    } else {
      p = stashPrice;
      stashPrice = 0;
      if (stashQual == "good") {
        quality = "good";
      }
      else if (stashQual == "okay") {
        quality = "okay";
      }
      else {
        quality = "bad";
      }
    }
    int quantity;
    if (i.m()) {
      System.out.println("How many would you like to buy?");
      if (scan.hasNextInt()) {
        quantity = scan.nextInt();
        scan.nextLine(); // consume the newline left by nextInt()
      } else {
        System.out.println("Please input a valid quantity.");
        stashPrice = p;
        stashQual = quality;
        dispPrice(price, i, true);
        return;
      }
    }
    else {
      quantity = 1;
    }
    if (money >= quantity * p) {
      money -= (quantity * p);
      purchaseItem(i, quality, quantity);
    }
    System.out.println("Would you like to buy anything else? (yes/no)");
    String cont = scan.nextLine().toLowerCase();
    if (cont.equals("yes")) {
      displayShop(true);
      System.out.println("Are you sure you want to stop shopping? (yes/no)");
      String stop = scan.nextLine();
      if (stop.toLowerCase().equals("yes")) {
        shteal();
      } else {
        displayShop(true);
      }
    }
    else {
      System.out.println("You don't have enough money to purchase that!");
      displayShop(true);
      return;
    }
  }

  public static void purchaseItem(Item i, String quality, int quantity) {
    if (i.m()) {
      if (quantity > 1) {
        System.out.println("You purchased " + quantity + " " + UsefulMethods.capitalize(quality) + " " + i.getn() + "s.");
      } else {
        System.out.println("You purchased 1 " + quality + " " + i.getn() + ".");
      }
    } else {
      System.out.println("You purchased 1 " + UsefulMethods.capitalize(quality) + " " + i.getn() + ".");
    }
    for (int j = 0; j < inventory.size(); j ++) {
      if (inventory.get(j).getn().equals(i.getn())) {
        if (inventory.get(j).getqual().equals(quality)) {
          inventory.get(j).setq(inventory.get(j).getq() + quantity);
        }
        inventory.add(i);
        i.setq(quantity);
        i.q(quality);
      }
      else {
        inventory.add(i);
        i.setq(quantity);
        i.q(quality);
      }
    }
    
  }

  public static void shteal() {
    System.out.println("You wake in the middle of the night before the journey.");
    System.out.println("Despite making the most of your money, you have doubts about your resources.");
    System.out.println("Steal from the nearby town (Yes/No)?");
    String steal = scan.nextLine();
    steal = steal.toLowerCase();
    if (steal.equals("no")) {
      go();
    }
    else {
      System.out.println("You sneak into the food shop in Turuf.");
      System.out.println("Stealing any of the other items would be too difficult on skis.");
      steal();
    }
  }
  public static void steal() {
    System.out.println(" _____________ ____________ ______ ____________ _______");
    System.out.println("| Gichy-michy | Kadik-germ | Orsh | Breadapple | Leave |");
    System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾");
    String ste = scan.nextLine();
    ste = ste.toLowerCase();
    for (int i = 4; i < 8; i ++) {
      if (ste.equals(shop[i].getn().toLowerCase())) {
        System.out.println("You can only steal 5 of an item at a time.");
        System.out.println("How many would you like to steal?");
        int amt = scan.nextInt();
        scan.nextLine(); // consume the newline left by nextInt()
        double c = Math.random() * amt;
        if (c <= 2) { //kinda arbitrary but whatever just that the more you buy the lower chance of hitting it, so 50% at 1 item, 25% at 2 item, 16.7% at 3 item
          purchaseItem(shop[i], "good", amt);
          break;
        }
        else {
          System.out.println("You accidentally drop some " + shop[i].getn().toLowerCase() + ".");
          System.out.println("After pausing briefly, you here a stir nearby. You quickly pack up your loot and run.");
          go();
          return;
        }
      }
    }
    if (ste.equals("leave")) {
      go();
    }
    else {
      System.out.println("Please select a valid item.");
      steal();
    }
  }

  public static double changeDistMult(double disasterMult, int mapQuality) {
    // TODO: use for loop w/ maxdisasters parameter to roll for extra disasters.
    int maxDisasters;
    double mult = 1.0;
    double flatDist = 0.0;
    double compoundChance = disasterMult;
    //might want to not round decimals (at least ccRoll)
    double rand = Math.round(Math.random() * 10000)/10000.0; //random number between 0 and 1, rounded to 4 decimal places
    double rand2 = Math.round(Math.random() * 10000)/10000.0; //used for secondary roll for higher chance on some secondary effects
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
      double disasterMod = Math.random() * 3;
      maxDisasters = 1 + (int)disasterMod;
      for (int i = 0; i < maxDisasters; i++){
        if (ccRoll < compoundChance && activeDisasters.size() < maxDisasters) { //ccroll btwn 0 & 1, but starts at 1 so always passes 1st time
          if (rand < 0.05) {
            //flat light
            if (!activeDisasters.contains("Flat Light")) {
              mult *= 0.8;
              compoundChance *= 0.3;
              activeDisasters.add("Flat Light");
            }
          }
          else if (rand < 0.15) {
            //snow storm
            if (!activeDisasters.contains("Snowstorm")) {
              mult *= 0.7;
              compoundChance *= 0.3;
              activeDisasters.add("Snowstorm");
            }
          }
          else if (rand < 0.175) {
            //eruption (volacno)
            if (!activeDisasters.contains("Volcano")) {
              mult *= 0.5;
              compoundChance *= 0.3;
              activeDisasters.add("Volcano");
            }
          }
          else if (rand < 0.18 && !activeDisasters.contains("Flat Light")) {
            // trips = day ends early & no second disaster (if first) (duration 1)
            activeDisasters.add("Tripped ");
            flatDist = Math.round(Math.random() * 10 * mult);
            mult *= 0;
            compoundChance *= 0;
            if (rand2 < 0.5) {
              //50% chance of frostbite
              activeDisasters.add("Frostbite");
              flatDist *= 0.5;
            }
          }
          else if (rand < 0.2 && activeDisasters.contains("Flat Light")) {
            // increased chance w/ flat light
            activeDisasters.add("Tripped");
            if (rand2 < 0.3) {
              //30% chance of frostbite, less b/c already has flat light
              activeDisasters.add("Frostbite");
              flatDist *= 0.5;
            }
          }
          else if (rand < 0.6 && (activeDisasters.contains("Snowstorm") || activeDisasters.contains("Tripped"))) {
            //frostbite
            activeDisasters.add("Frostbite");
            mult *= 0.3;
          } else {
            //no disaster, but still chance for one, just smaller
            compoundChance *= 0.3;
          }
        } else {
          break;
        }
      }
      // available disasters: crevasse, flat light, snow storm, bad map, avalance, frostbite, extreme snow storm, special snow storm
    } else if (biome.equals("i") ) { //ice
      double disasterMod = Math.random() * 2;
      maxDisasters = 3 + (int)disasterMod;
      if (ccRoll < compoundChance && activeDisasters.size() < maxDisasters) {
        if (rand < 0.1 && !activeDisasters.contains("Snowstorm")) {
          //ice crevasse
          activeDisasters.add("Snowstorm");
          compoundChance *= 0.6;
        } else if (rand < 0.2 && !activeDisasters.contains("Flat Light")) {
          //snow storm
          activeDisasters.add("Flat Light");
          compoundChance *= 0.6;
        } else if (rand < 0.25 && !activeDisasters.contains("Avalanche")) {
          //avalanche
          activeDisasters.add("Avalanche");
          compoundChance *= 0.6;
        } else if (rand < 0.4 && !activeDisasters.contains("Special Snowstorm")) {
          //special snow storm
          activeDisasters.add("Special Snowstorm");
          compoundChance *= 0.6;
        } else {
          //no disaster, but still chance for one, just smaller
          compoundChance *= 0.6;
        }
      }
    } else if (biome.equals("b")) { //bay of Guthen
      double disasterMod = Math.random() * 3;
      maxDisasters = 4 + (int)disasterMod; 
      //mult by 0.75 each time
      if (ccRoll < compoundChance && activeDisasters.size() < maxDisasters) {

      }

    }

    if (mult == 0) {
      dtrav += flatDist;
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
    String front = scan.nextLine();
    if (front.equals("forward")) {
      forward(front);
    }
    JFrame frame = new JFrame("test");
    frame.setSize(1280, 725);
    frame.setVisible(true);
  }

  //prob do choices to make it more interactive (ex, which path)
  //have a planning stage where players map out their route (from set choices)
  //could add a "lost" event in flat light/snowstorm = more interactivity
  //option to start/stop rationing food = hunger pangs hindering decision making = higher lost chance as genly, slighly higer as estraven
  public static void go() {
    //start of journey, I didn't want to go from stealing right into journey, should be a little bit of in between
  }

  public static void forward(String t) { //game forward
    dtrav = 12;
    dtrav += terrain();
    distanceleft -= dtrav;
    if (distanceleft <= 0) {
      distanceleft = 0;
      System.out.println("Congratulations! You have arrived in Kurkurast and won the game!");
    }
  }
}
