package Scripts;
//100 off ice, frostbite, bad map
//600 ice - crevasse, snowstorm, volcano, flat light, frostbite, bad map, avalanche
//140 bay - town, snowstorms, flat light, frostbite, bad map, 
import java.awt.Font;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.text.*;

public class Game {
  //making items so its easier to print stuff out and such just a bunch of variables
  //bool is stackable or not, //i think bags and skis you just auto buy for both, (Gichy-michy) //25 per pound, 80 days, 2000 money for all the food you need, (breadapple) //maybe more expensive but this makes them happy or smth, (first aid kit) //maybe add stack limit?, (map) //good map reduces chance of getting lost, bad map increases it, (orsh) //seems to have like healing properties, so maybe more expensive?
  //i changed it to array to look better
  private static ArrayList<Item> inventory = new ArrayList<Item>();
  private static int money = 5000;
  private static final int stoveCost = 1800, tentCost = 1000, sbagsCost = 500, sledgeCost = 750, gmCost = 25, kgermCost = 35, bappleCost = 75, orshCost = 100, fakitCost = 400, mapCost = 650, backpackCost = 850, skisCost = 100;
  private static final Item[] shop = {new Item(stoveCost, "Stove", false), new Item(tentCost, "Tent", false), new Item(sbagsCost, "Sleeping Bags", false), new Item(sledgeCost, "Sledge", false), new Item(gmCost, "Gichy-michy", true), new Item(kgermCost, "Kadik-germ", true), new Item(bappleCost, "Breadapple", true), new Item(orshCost, "Orsh", true), new Item(fakitCost, "First Aid", true), new Item(mapCost, "Map", false), new Item(backpackCost, "Backpack", true), new Item(skisCost, "Skis", false)};
  private static JFrame frame = new JFrame("Game");
  private static Font font = new Font("Times New Roman", Font.PLAIN, 16);
  private static JTextArea text = new JTextArea();
  private static String c = "";
  private static String chara;
  private static int p;
  private static ArrayList<String> old = new ArrayList<String>();
  private static int count = 0;
  private static int mapQuality = -1;
  private static int standTravel = 13; //standard travel amt per day, can change based on how the players ration or their health
  private static int foodUse = 1;
  private static int foodUnits = 0;
  private static boolean first = true;
  private static Person g = new Person("Genly");
  private static Person e = new Person("Estraven");
  private static DecimalFormat df = new DecimalFormat("#.####");

  //THIS IS IMPORTANT
  private static Scanner scan = new Scanner(System.in);

  private static double dtrav = 12.0;
  private static final double distance = 840.0;
  private static double distanceleft = 840.0;
  private static String biome = "o"; //o = orgoreyn, i = ice, b = bay of Guthen
  private static ArrayList<String> activeDisasters = new ArrayList<String>();

  public static void start() {
    //vis
    JScrollPane hold = new JScrollPane(text);
    frame.setSize(1470, 920);
    frame.setLayout(null);
    frame.setUndecorated(true);
    frame.setVisible(true);
    text.setEditable(false);
    hold.setBounds(235, 25, 1000, 750);
    hold.setBorder(null);
    text.setFont(font);
    text.setLineWrap(true);
    frame.add(hold);
    text.append("Welcome to the Gobrin Trail!\nYour goal is to travel safely across the Gobrin Ice and find freedom in Karhide, 840 miles away. \n");
    text.append("You must manage your resources wisely and make strategic decisions to survive the harsh conditions of the Gobrin Ice. \n");
    text.append("You start with 2 Backpacks, and must buy more items from the shop to survive your journey.\nAlong the way, you will encounter various obstacles and disasters.\nGood luck on the Ice! \n");
    //sledge still should be ten times, but it said in the book that backpacks < 30lbs, sledge > 300lbs
    //maybe storage isnt limit, but makes travel slower with diff limit as hard cap
    frame.repaint();
    frame.revalidate();
    charSelect(true);
  }

  public static JTextField bob() {  //this makes the JTextFields and prints them, idk why I named it bob
    JTextField answer = new JTextField();
    answer.setBounds(235, 767, 1000,35);
    answer.setFont(font);
    answer.setBorder(null);
    answer.setText("Type responses here: ");
    answer.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (answer.getText().equals("Type responses here: ")) {
          answer.setText("");
        }
      }
      public void focusLost(FocusEvent e) {
        if (answer.getText().equals("")) {
          answer.setText("Type responses here: ");
        }
      }
    });
    answer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        old.add(answer.getText());
        count = old.size();
        text.setCaretPosition(text.getDocument().getLength());
      }
    }); 
    answer.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          if (count > 0) {
            count --;
            answer.setText(old.get(count));
          }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          if (count == old.size() - 1) {
            count ++;
            answer.setText("");
          }
          if (count < old.size() - 1) {
              count ++;
              answer.setText(old.get(count));
          }
        }
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
          count = old.size();
        }
      }
    });
    frame.add(answer);
    frame.repaint();
    frame.revalidate();
    return answer;
}

  public static void charSelect(boolean valid) {
    text.append("\n");
    if (valid) {
      text.append("Please type the name of your character:\n");
    } else {
      text.append("Please pick a valid character:\n");
    }
    text.append("1. Estraven\n2. Genly\n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        if (c.toLowerCase().equals("genly")) {
          chara = "Genly";
          text.append(chara + " chosen \n");
          displayShop(true);
          frame.remove(a);
        }
        else if (c.toLowerCase().equals("estraven")) {
          chara = "Estraven";
          text.append(chara + " chosen \n");
          displayShop(true);
          frame.remove(a);
        }
        else {
          charSelect(false);
          frame.remove(a);
          return;
        }
      }
    });
  }
  
    // text.append(" ____________ _____________ ____________ _______________ __________\n");
    // text.append("| Specialty: |    Stove    |    Tent    | Sleeping Bags |  Sledge  |\n");
    // text.append(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾ \n");
    // text.append(" ____________ _____________ ____________ _______________ __________\n");
    // text.append("|    Food:   | Gichy-michy | Kadik-germ |   Breadapple  |   Orsh   |\n");
    // text.append(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾\n");
    // text.append(" ____________ _____________ ____________ _______________ __________\n");
    // text.append("|   Other:   |  First Aid  |     Map    |    Backpack   |   Skis   |\n");
    // text.append(" ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ‾‾‾‾‾‾‾‾‾‾\n");
    // text.append(" ____________\n");
    // text.append("|  Continue  |\n");
    // text.append(" ‾‾‾‾‾‾‾‾‾‾‾‾\n");
    //I don't think we can do this bc it prints poorly in the TextArea
  //list of supplies a few pages into chapter 15
  //orsh, gitchy-mitchy, kadik-germ, dried breadapple, red sugar, sleeping bags, clothes, skis, sledge, qualities for each equipment (good, avg, poor)
  //in story Estraven bought good qual everything & stole food
  //gichy-michy req 1lb/day

  public static void displayShop(boolean valid) {
    text.append("\n");
    text.append("Welcome to the shop! Here are the items available for purchase:\n");
    text.append("Specialties: Stove, Tent, Sleeping Bags, Sledge \n");
    text.append("Food: Gichy-michy, Kadik-germ, Breadapple, Orsh \n");
    text.append("Other: First Aid, Map, Backpack, Skis \n");
    text.append("Continue \n");
    text.append("Money: " + money + "\n");
    if (valid) {
      text.append("What would you like to buy?\n");
    } else {
      text.append("Please pick a valid item.\n");
    }
    //figure out the buy thing (if it doesn't work at first cry cry again)
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        if (c.toLowerCase().equals("continue")) {
          shteal();
          frame.remove(a);
          return;
        }
        if (!c.equals("")) {
          findProduct(c);
          frame.remove(a);
        }
      }
    });
  }

  public static void findProduct(String p) {
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
        shteal();
      }
      displayShop(false);
    }
    else {
      if (!it.m()) {
        for (int i = 0; i < inventory.size(); i ++) {
          if (it.getn().equals(inventory.get(i).getn())) {
            text.append("You have already purchased one of this item, you cannot buy another (some items are one and only)!");
            displayShop(true);
          }
        }
      }
      dispPrice(it.getc(), it);
    }
  }

  public static void dispPrice(int price, Item i) {
    text.append("\n");
    text.append("Money: " + money + "\n");
    text.append("What quality " + UsefulMethods.capitalize(i.getn()) + " would you like to buy?\n");
    int bapr = chara.equals("Estraven") ? (int)(price * 3)/4 : (int)(((price*3)/4)*0.95);
    int gopr = chara.equals("Estraven") ? (int)(price * 1.25) : (int)((price*1.25)*0.95);
    int pric = chara.equals("Estraven") ? (int)price : (int)price;
    text.append("Good Quality: " + gopr + "\n");
    text.append("Okay Quality: " + pric + "\n");
    text.append("Bad Quality: " + bapr + "\n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        if (c.toLowerCase().equals("good")) {
          frame.remove(a);
          getQuant(i, "Good", gopr);
        }
        else if (c.toLowerCase().equals("okay")) {
          frame.remove(a);
          getQuant(i, "Okay", pric);
        }
        else if (c.toLowerCase().equals("bad")) {
          frame.remove(a);
          getQuant(i, "Bad", bapr);
        }
        else {
          text.append("Please input a valid quality \n");
          dispPrice(price, i);
          frame.remove(a);
          return;
        }
      }
    });
  }
  
  public static void getQuant(Item i, String q, int p) {
    text.append("\n");
    if (i.m()) {
      text.append("How many " + q + " " + i.getn() + " would you like to buy? \n");
      JTextField a = bob();
      a.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          c = a.getText();
          int quant = 1;
          try {
            quant = Integer.parseInt(c);
          }
          catch (NumberFormatException z) {
            text.append("Please input a valid quantity (just the number!) \n");
            frame.remove(a);
            getQuant(i,q,p);
            return;
          }
          if (money >= (p * quant)) {
            purchaseItem(i,q,quant,p, false);
            frame.remove(a);
          }
          else {
            text.append("You do not have enough money to purchase that! \n");
            frame.remove(a);
            displayShop(true);
            return;
          }
        }
      });
    }
    else {
      if (money >= p) {
        for (int j = 0; j < inventory.size(); j ++) {
          if (inventory.get(j).getn().toLowerCase().equals(i.getn().toLowerCase())) {
            text.append("You have already bought one of these items! \n");
            displayShop(true);
            return;
          }
        }
        purchaseItem(i, q, 1, p, false);
      }
      else {
        text.append("You do not have enough money to purchase that! \n");
        displayShop(true);
        return;
      }
    }
  }

  public static void purchaseItem(Item i, String quality, int quantity, int price, boolean steal) {
    text.append("\n");
    int coun = 0;
    for (int j = 0; j < inventory.size(); j ++) {
      if (inventory.get(j).getn().equals(i.getn())) {
        if (inventory.get(j).getqual().equals(quality)) {
          inventory.get(j).setq(inventory.get(j).getq() + quantity);
        }
        else {
          inventory.add(i);
          i.setq(quantity);
          i.q(quality);
        }
      }
      else {
        coun ++;
      }
    }
    if (coun == inventory.size()) {
      inventory.add(i);
      i.setq(quantity);
      i.q(quality);
    }
    money -= quantity * price;
    if (!steal) {
      text.append("You purchased " + quantity + " " + UsefulMethods.capitalize(quality) + " " + i.getn() + "(s). \n");
    }
    else {
      text.append("You stole " + quantity + " " + UsefulMethods.capitalize(quality) + " " + i.getn() + "(s). \n");
    }
    text.append("Would you like to purchase anything else? \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        if (!steal) {
          if (c.toLowerCase().equals("no")) {
            shteal();
            frame.remove(a);
          }
          else {
            displayShop(true);
            frame.remove(a);
          }
        }
        else {
        if (c.toLowerCase().equals("no")) {
          go();
          frame.remove(a);
        }
        else {
          steal();
          frame.remove(a);
        }
      }
      }
    });
  }

  public static void shteal() {
    System.out.println(Arrays.deepToString(inventory.toArray()));
    for (int i = 0; i < inventory.size(); i ++) {
      if (inventory.get(i).getn().equals("Map")) {
        if (inventory.get(i).getqual().toLowerCase().equals("good")) {
          mapQuality = 2;
        }
        else if (inventory.get(i).getqual().toLowerCase().equals("okay")) {
          mapQuality = 1;
        }
        else if (inventory.get(i).getqual().toLowerCase().equals("bad")) {
          mapQuality = 0;
        }
      }
      if (inventory.get(i).getn().equals("Gichy-michy")) {
        foodUnits += inventory.get(i).getq();
      }
      if (inventory.get(i).getn().equals("Kadik-germ")) {
        foodUnits += (inventory.get(i).getq() * 2);
      }
      if (inventory.get(i).getn().equals("Breadapple")) {
        foodUnits += (inventory.get(i).getq() * 3);
      }
    }
    System.out.println(mapQuality);
    text.append("\n");
    text.append("You wake in the middle of the night before the journey. \n");
    text.append("Despite making the most of your money, you have doubts about your resources. \n");
    text.append("Steal from the nearby town (Yes/No)? \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        if (c.toLowerCase().equals("yes")) {
          text.append("You sneak into the food shop in Turuf. \n");
          steal();
          frame.remove(a);
        }
        else {
          go();
          frame.remove(a);
        }
      }
    });
  }
  
  public static void steal() {
    text.append("\n");
    text.append("Gichy-michy, Kadik-germ, Orsh, Breadapple \n");
    text.append("Leave \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Item it = null;
        c = a.getText();
        c = c.toLowerCase();
        for (int i = 4; i < 8; i ++) {
          if (c.equals(shop[i].getn().toLowerCase())) {
            it = shop[i];
            break;
          }
        }
        if (c.equals("gichy michy")) {
          it = shop[4];
        }
        else if (c.equals("kadik germ")) {
          it = shop[5];
        }
        if (it != null) {
          stealQuant(it);
          frame.remove(a);
        }
        else {
          if (c.equals("continue")) {
            text.append("Cutting your losses, you leave with your loot. \n");
            frame.remove(a);
            go();
          }
          else {
            text.append("Please input a valid item \n");
            steal();
            frame.remove(a);
            return;
          }
        }
      }
    });
  }

  public static void stealQuant(Item i) {
    text.append("\n");
    text.append("How many " + i.getn() + " would you like to steal (max 5) \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        try { 
          p = Integer.parseInt(c);
          if (p <= 5 && p > 0) {
            double d = (Math.random() * p);
            if (d <= (Math.pow(1.1, p))) {
              purchaseItem(i,"Okay",p,0, true);
              frame.remove(a);
            }
            else {
              text.append("While trying to steal the " + i.getn() + " you drop some and make a stir. \n");
              text.append("Not wanting to get caught, you cut your losses and ski back to camp. \n");
              frame.remove(a);
              go();
            }
          }
          else {
            text.append("Please input a valid quantity (more than 0, less than 6) \n");
            frame.remove(a);
            stealQuant(i);
            return;
          }
        } catch (NumberFormatException z) {
          text.append("Please input a valid quantity (just the number!) \n");
          frame.remove(a);
          stealQuant(i);
          return;
        }
      }
    });
  }

  public static double changeDistMult(double disasterMult) {
    text.append("\n");
    //I think this is necessary but should discuss
    activeDisasters.clear();
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
        if (!activeDisasters.contains("Lost")) {
          activeDisasters.add("Lost");
        }
      }
    } else if (mapQuality == 1)  {
      //average map
      if (rand < 0.25) {
        mult *= 0.9;
        if (!activeDisasters.contains("Lost")) {
          activeDisasters.add("Lost");
        }
      }
    } else if (mapQuality == -1) { //no map
      mult *= 0.9;
      if (!activeDisasters.contains("Lost")) {
        activeDisasters.add("Lost");
      }
    } else {
      //good map
      mult *= 1.01;
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
    text.append("\n");
    double e = Math.random();
    if (dtrav < 100) {
      biome = "o";
    }
    else if (dtrav < 640) {
      if (biome.equals("o")) {
        text.append("You have made it onto the Gobrin Ice! \n");
      }
      biome = "i";
    }
    else {
      if (biome.equals("i")) {
        text.append("You have made it to the Bay of Guthen! \n");
      }
      biome = "b";
    }
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

  //prob do choices to make it more interactive (ex, which path)
  //have a planning stage where players map out their route (from set choices)
  //could add a "lost" event in flat light/snowstorm = more interactivity
  //option to start/stop rationing food = hunger pangs hindering decision making = higher lost chance as genly, slighly higer as estraven
  public static void go() {
    //start of journey, I didn't want to go from stealing right into journey, should be a little bit of in between
    for (int i = 0; i < inventory.size(); i ++) {
      if (inventory.get(i).getn().equals("Skis")) {
        standTravel ++;
        break;
      }
    }
    text.append("Finally, you begin your journey across the Ice");
    move(true);
  }
  
  public static void move(boolean valid) {
    JTextField a = bob();
    if (!valid) {
      text.append("Please input one of the following, enter continue when ready to move on. \n");
    }
    text.append("Manage: Food, Distance, Inventory, Status. \n");
    if (first) {
      text.append("Type Continue whenever you want to move on. \n");
      first = false;
    }
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        a.setText("");
        if (distanceleft <= 0) {
          text.append("You won");
          a.setEditable(false);
        }
        if (c.toLowerCase().equals("food")) { 
          food();
          frame.remove(a);
        }
        if (c.toLowerCase().equals("distance")) {
          distance();
        }
        if (c.toLowerCase().equals("inventory")) {
          inventory();
        }
        if (c.toLowerCase().equals("status")) {
          status();
        }
        if (c.toLowerCase().equals("continue")) {
          c = a.getText();
          text.append("You traveled " + Double.parseDouble(df.format(Double.toString(forward()))) + " miles \n");
          text.append("Disasters: " + Arrays.deepToString(activeDisasters.toArray()));
          foodUnits -= foodUse;
          frame.remove(a);
          move(true);
        }
        else {
          move(false);
          frame.remove(a);
        }
      }
    });
  }
  //can prob change how food works this is just the first system that came to mind
  //units will be easiest but others def possible
  public static void food() { 
    JTextField a = bob();
    text.append("You are currently consuming " + foodUse + " units  of food everyday. \n");
    text.append("Kadik-germ = 2 units, Gichy-michy = 1 unit, Breadapple = 3 units \n");
    text.append("You currently have " + foodUnits + " units of food. \n");
    text.append("Would you like to change how you're rationing? \n");
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        if (c.toLowerCase().equals("yes")) {
          ration(true);
          frame.remove(a);
        }
        else {
          move(true);
          frame.remove(a);
        }
      }
    });
  }
  
  public static void ration(boolean val) {
    if (!val) {
      text.append("You can use a maximum of 4 food units a day, or a minimum of 1 food unit per day. \n");
      text.append("Using more or less will increase/decrease the distance you travel each day. \n");
      text.append("How many would you like to use per day (whole number)? \n");
    }
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        try {
          int x = Integer.parseInt(c);
          foodUse = x;
          text.append("You are now using " + x + " units of food per day. \n");
          frame.remove(a);
          move(true);
        } catch (NumberFormatException z) {
          text.append("Please input a whole number. \n");
          ration(false);
          frame.remove(a);
        }
      }
    });
  }

  public static void inventory() {
    text.append("You currently have: " + Arrays.deepToString(inventory.toArray()) + "\n");
    text.append("Would you like to dispose of anything? \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        if (c.toLowerCase().equals("yes")) {

        }
      }
    });
  }

  public static void dispose() {
    text.append("What would you like to dispose (this is irreversible)? \n");
    text.append("Enter 'none' if you would not like to dispose of anything. \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        for (int i = 0; i < inventory.size(); i ++) {
          if (inventory.get(i).getn().toLowerCase().equals(c.toLowerCase())) {
            for (int j = i + 1; j < inventory.size(); j ++) {
              if (inventory.get(j).getn().equals(inventory.get(i).getn())) {
                getDropQual(inventory.get(i).getn());
                return;
              }
            }
            if (inventory.get(i).m()) {
              getDropQuan(inventory.get(i).getn(), inventory.get(i).getqual());
              frame.remove(a);
              return;
            }
            else {
              inventory.remove(i);
              text.append("You dropped your " + inventory.get(i).getn() + ". \n");
              inventory();
              frame.remove(a);
              return;
            }
          }
        }

        if (c.toLowerCase().equals("none")) {
          move(true);
          frame.remove(a);
          return;
        }

        text.append("Please enter a valid item. \n");
        dispose();
        frame.remove(a);
      }
    });
  }

  public static void getDropQual(String s) {
    text.append("What quality " + s + " would you like to dispose of? \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        for (int i = 0; i < inventory.size(); i ++) {
          if (s.equals(inventory.get(i).getn())) {
            if (c.toLowerCase().equals(inventory.get(i).getqual().toLowerCase())) {
              if (inventory.get(i).m()) {
                getDropQuan(inventory.get(i).getn(), inventory.get(i).getqual());
                frame.remove(a);
                return;
              }
              else {
                inventory.remove(i);
                text.append("You dropped your " + inventory.get(i).getn() + ". \n");
                inventory();
                frame.remove(a);
                return;
              }
            }
          }
        }
        if (c.toLowerCase().equals("none")) {
          move(true);
          frame.remove(a);
          return;
        }
        text.append("Please input a valid quantity of the item you chose. \n");
        dispose();
        frame.remove(a);
      }
    });
  }

  public static void getDropQuan(String s, String q) {
    text.append("How much " + q + " " + s + " would you like to dispose? \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        if (c.toLowerCase().equals("none")) {
          inventory();
          frame.remove(a);
          return;
        }
        try {
            int amt = Integer.parseInt(c);
            for (int i = 0; i < inventory.size(); i ++) {
              if (inventory.get(i).getn().toLowerCase().equals(s.toLowerCase())) {
                if (inventory.get(i).getn().toLowerCase().equals(q.toLowerCase())) {
                  if (inventory.get(i).getq() > amt) {
                    inventory.get(i).setq(inventory.get(i).getq() - amt);
                    text.append("You have disposed of " + amt + " " + q + " " + s + ". \n");
                    frame.remove(a);
                    inventory();
                    return;
                  }
                  else {
                    inventory.remove(i);
                    text.append("You disposed all of your " + q + " " + s + ". \n");
                    frame.remove(a);
                    inventory();
                    return;
                  }
                }
              }
            }
            text.append("Soemthing messed up :( \n");
        } catch (NumberFormatException z) {
          text.append("Please input a valid integer. \n");
          getDropQuan(s, q);
          frame.remove(a);
        }
      }
    });
  }

  public static void distance() {
    text.append("You are currenlty " + (840 - distanceleft) + " miles through your journey. \n");
    move(true);
  }

  public static void status() {
    text.append(g.toString() + ", " + e.toString());
    JTextField a = bob();
    boolean hasKit = false;
    for (int i = 0; i < inventory.size(); i ++) {
      if (inventory.get(i).getn().equals("First Aid")) {
        hasKit = true;
      }
      if (hasKit) {
        text.append("Would you like to use any first aid kits? \n");
        a.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            c = a.getText();

            if (c.toLowerCase().equals("yes")) {
              for (int i = 0; i < inventory.size(); i ++) {
                if (inventory.get(i).getn().equals("First Aid")) {
                  for (int j = i + 1; j < inventory.size(); j ++) {
                    if (inventory.get(j).getn().equals("First Aid")) {
                      getMedQual();
                      frame.remove(a);
                      return;
                    }
                  }
                  getMedQuan(inventory.get(i).getqual());
                  frame.remove(a);
                  return;
                }
              }
            }
            else {
              move(true);
              frame.remove(a);
            }
          }
        });
      }
    }
  }

  public static void getMedQual() {
    JTextField a = bob();
    text.append("What quality first aid kit would you like to use? \n");
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        for (int i = 0; i < inventory.size(); i ++) {
          if (inventory.get(i).getn().equals("First Aid") && inventory.get(i).getqual().toLowerCase().equals(c.toLowerCase())) {
            if (inventory.get(i).getq() > 1) {
              //TODO: figure out which person to add it to or prompt player?
              frame.remove(a);
              return;
            }
            else {
              getMedQuan(c);
              frame.remove(a);
              return;
            }
          }
        }
        if (c.toLowerCase().equals("none")) {
          move(true);
          frame.remove(a);
          return;
        }
        text.append("Please input a valid quality of first aid kit that you own. \n");
        frame.remove(a);
      }
    });
  }

  public static void getMedQuan(String q) {
    text.append("What quantity of " + q + " first aid kits would you like to use? \n");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
        try {
            int amt = Integer.parseInt(c);
            for (int i = 0; i < inventory.size(); i ++) {
              if (inventory.get(i).getn().equals("First Aid") && inventory.get(i).getqual().toLowerCase().equals(q.toLowerCase())) {
                if (amt < inventory.get(i).getq()) {
                  //TODO: use amt, on who, etc. etc.
                  frame.remove(a);
                  return;
                }
                else {
                  text.append("You do not have that many " + q + " first aid kits! \n");
                  getMedQuan(q);
                  frame.remove(a);
                  return;
                }
              }
            }
        } catch (NumberFormatException z) {
          if (c.toLowerCase().equals("none")) {
            move(true);
            frame.remove(a);
            return;
          }
          text.append("Please input a valid integer! \n");
          getMedQuan(q);
        }
      }
    });
  }

  public static double forward() { //game forward
    dtrav = standTravel;
    dtrav += terrain();
    dtrav *= changeDistMult(1 - activeDisasters.size() * (0.14));
    distanceleft -= dtrav;
    return dtrav;
  }
}
