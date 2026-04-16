package Scripts;

public class Person {
	private String name;
	private int health = 100;
	public boolean isDead = false;

	public Person(String n) {
		name = n;
	}

	public int hp() {
		return health;
	}
	
	public void dmg(int d) {
		health -= d;
	}

	public boolean isDead() {
		return isDead;
	}

	public boolean changeDeathState(boolean b) {
		isDead = b;
		return isDead;
	}

	public String toString() {
		return name + ": " + health + " health. ";
	}
}
