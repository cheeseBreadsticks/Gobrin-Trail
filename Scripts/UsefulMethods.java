package Scripts;

public class UsefulMethods {
    //doesn't actually clear the terminal, just pushes the text up
    public static void clearTerminal() {
        for (int i = 0; i < 25; i ++) {
        System.out.println("\n");
        }
    }

    //should set the first letter of a string to caps
    public static String capitalize(String s) {
        if (s.length() > 1) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return "";
    }
}