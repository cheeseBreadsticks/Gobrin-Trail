package Scripts;

public class Item {
	private int cost;
	private String name;
	private boolean multiple;
	private String quality;
	public Item(int c, String n, boolean m) {
		name = n;
		cost = c;
		multiple = m;
	}
	public String getn() {
		return name;
	}
	public int getc() {
		return cost;
	}
	public boolean m() {
		return multiple;
	}
	public void q(String qual) {
		quality = qual;
	}
	public String toString() {
		return quality + " " + name + ": " + cost;
	}
}