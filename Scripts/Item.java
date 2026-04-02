package Scripts;

public class Item {
	private int cost;
	private String name;
	private boolean multiple;
	private String quality;
	private int quantity;
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
	public String getqual() {
		return quality;
	}
	public String toString() {
		return quantity + "x " + quality + " " + name + ": " + cost;
	}
	public void setq(int q) {
		quantity = q;
	}
	public int getq() {
		return quantity;
	}
}
