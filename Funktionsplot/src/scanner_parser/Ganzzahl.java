package scanner_parser;

public class Ganzzahl extends Knoten {
	private int wert;
	public Ganzzahl(int wert) {
		this.wert = wert;
	}
	@Override
	public void print() {
		System.out.println("Knoten " + num + " Integer " + wert + "\n");		
	}
}
