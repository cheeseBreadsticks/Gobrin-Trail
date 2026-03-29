package Scripts;

public class Person {
	private String name;
	private int health = 100;

	public Person(String n) {
		name = n;
	}

	public int hp() {
		return health;
	}
	
	public void dmg(int d) {
		health -= d;
	}
	public String toString() {
		return name + ": " + health + " health. ";
	}
}
