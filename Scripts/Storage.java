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
	public void store(int quantity) {
		if (capacityleft - quantity > 0) {
			capacityleft -= quantity;
			System.out.println("You can store " + capacityleft + " more units.");
		}
		else {
			System.out.println("You cannot store that much!");
		}
	}

	//get max capacity
	public int getCap() {
		return capacity;
	}
	
	//get remaining capacity
	public int getRem() {
		return capacityleft;
	}

	//get name of storage
	public String getName() {
		return name;
	}
}
