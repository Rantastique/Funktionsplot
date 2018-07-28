package scanner_parser;

public abstract class Knoten {
	private static int numCount = 1;
	protected int num = numCount++;
	public int getNum() { return num; }
	public abstract void print();
	public abstract Double calcAt(double x);
	public abstract Knoten ableitung();
	public abstract Knoten copy();
}
