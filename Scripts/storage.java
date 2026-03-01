package Scripts;

public class Storage {
	private final int capacity;
	private int capacityleft;
	private String name;
    
	public Storage (String n, int c) {
		capacity = c;
		name = n;
		capacityleft = c;
	}
	//might need an item class?
	public void store(int s) {
		if (capacityleft - s > 0) {
			capacityleft -= s;
			System.out.println("New capacity = " + capacityleft);
		}
		else {
			System.out.println("You cannot store that much!");
		}
	}
}