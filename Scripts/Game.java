package Scripts;
//100 off ice, frostbite, bad map
//600 ice - crevasse, snowstorm, volcano, flat light, frostbite, bad map, avalanche
//140 bay - town, snowstorms, flat light, frostbite, bad map, 
import java.awt.Font;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Game {
  //making items so its easier to print stuff out and such just a bunch of variables
  //bool is stackable or not, //i think bags and skis you just auto buy for both, (Gichy-michy) //25 per pound, 80 days, 2000 money for all the food you need, (breadapple) //maybe more expensive but this makes them happy or smth, (first aid kit) //maybe add stack limit?, (map) //good map reduces chance of getting lost, bad map increases it, (orsh) //seems to have like healing properties, so maybe more expensive?
  //i changed it to array to look better
  private static ArrayList<Item> inventory = new ArrayList<Item>();
  private static int money = 5000;
  private static final int stoveCost = 1800, tentCost = 1000, sbagsCost = 500, sledgeCost = 750, gmCost = 25, kgermCost = 35, bappleCost = 75, orshCost = 100, fakitCost = 400, mapCost = 650, backpackCost = 850, skisCost = 100;
  private static final Item[] shop = {new Item(stoveCost, "Stove", false), new Item(tentCost, "Tent", false), new Item(sbagsCost, "Sleeping Bags", false), new Item(sledgeCost, "Sledge", false), new Item(gmCost, "Gichy-michy", true), new Item(kgermCost, "Kadik-germ", true), new Item(bappleCost, "Breadapple", true), new Item(orshCost, "Orsh", true), new Item(fakitCost, "First Aid", true), new Item(mapCost, "Map", false), new Item(backpackCost, "Backpack", true), new Item(skisCost, "Skis", false)};
  private static JFrame frame = new JFrame("Game");
  private static Font font = new Font("Times New Roman", Font.PLAIN, 18);
  private static JTextArea text = new JTextArea();
  private static String c = "";
  private static String chara;
  private static int p;
  private static java.util.Timer check = new java.util.Timer();
  private static ArrayList<String> old = new ArrayList<String>();
  private static int count = 0;
  private static int mapQuality = -1;

  //THIS IS IMPORTANT
  private static Scanner scan = new Scanner(System.in);

  private static double dtrav = 12.0;
  private static final double distance = 840.0;
  private static double distanceleft = 840.0;
  private static String biome = "o"; //o = orgoreyn, i = ice, b = bay of Guthen
  private static ArrayList<String> activeDisasters = new ArrayList<String>();

  static TimerTask go = new TimerTask() {
    public void run() {
      if (text.getLineCount() >= 27) {
        ArrayList<String> keep = new ArrayList<String>();
        String[] lines = text.getText().split("\n");
        for (int j = lines.length - 1; j >= 0; j --) {
          if (lines[j].isEmpty()) {
            for (int i = j + 1; i < lines.length; i ++) {
              keep.add(lines[i]);
            }
            break;
          }
        }
        text.setText("");
        for (int i = 0; i < keep.size(); i++) {
          text.append(keep.get(i) + "\n");
        }
        text.append("\n");
      }
    }
  };

  public static void start() {
    //vis
    frame.setSize(1470, 920);
    frame.setLayout(null);
    frame.setUndecorated(true);
    frame.setVisible(true);
    text.setEditable(false);
    text.setBounds(235, 25, 1000, 750);
    text.setFont(font);
    text.setLineWrap(true);
    frame.add(text);
    text.append("Welcome to the Gobrin Trail!\nYour goal is to travel safely across the Gobrin Ice and find freedom in Karhide, 840 miles away. \n");
    text.append("You must manage your resources wisely and make strategic decisions to survive the harsh conditions of the Gobrin Ice. \n");
    text.append("You start with 2 Backpacks, and must buy more items from the shop to survive your journey.\nAlong the way, you will encounter various obstacles and disasters.\nGood luck on the Ice! \n");
    //sledge still should be ten times, but it said in the book that backpacks < 30lbs, sledge > 300lbs
    //maybe storage isnt limit, but makes travel slower with diff limit as hard cap
    Storage b1 = new Storage("Backpack", 30);
    Storage b2 = new Storage("Backpack", 30); //backpacks
    frame.repaint();
    frame.revalidate();
    charSelect(true);
  }

  public static JTextField bob() {  //this makes the JTextFields and prints them, idk why I named it bob
    JTextField answer = new JTextField();
    answer.setBounds(231, 770, 1007,35);
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
    for (int i = 0; i < inventory.size(); i ++) {
      if (inventory.get(i).getn().equals("Map")) {
        if (inventory.get(i).getqual().equals("good")) {
          mapQuality = 2;
        }
        else if (inventory.get(i).getqual().equals("okay")) {
          mapQuality = 1;
        }
        else if (inventory.get(i).getqual().equals("bad")) {
          mapQuality = 0;
        }
      }
    }
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
      biome = "i";
    }
    else {
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
    check.scheduleAtFixedRate(go, 0, 5);
    start();
    String front = scan.nextLine();
    if (front.equals("forward")) {
      forward(front);
    }
  }

  //prob do choices to make it more interactive (ex, which path)
  //have a planning stage where players map out their route (from set choices)
  //could add a "lost" event in flat light/snowstorm = more interactivity
  //option to start/stop rationing food = hunger pangs hindering decision making = higher lost chance as genly, slighly higer as estraven
  public static void go() {
    //start of journey, I didn't want to go from stealing right into journey, should be a little bit of in between
    text.append("Finally, you begin your journey across the Ice");
    JTextField a = bob();
    a.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        c = a.getText();
      }
    });

    
  }

  public static double forward(String t) { //game forward
    dtrav = 15;
    dtrav += terrain();
    dtrav *= changeDistMult(1 - activeDisasters.size() * (0.14));
    distanceleft -= dtrav;
    return dtrav;
  }
}
