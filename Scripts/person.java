package Scripts;

public class Person {
	private String name;
	private int health = 100;
	private int str = 100;

	public void dmg(int d) {
		health -= d;
	}
	public String toString() {
		return name + ": " + health + " health, " + str + " energy.";
	}
}