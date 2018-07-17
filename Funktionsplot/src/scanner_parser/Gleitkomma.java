package scanner_parser;

public class Gleitkomma extends Knoten {
	private double wert;
	public Gleitkomma(double wert) {
		this.wert = wert;
	}
	@Override
	public void print() {
		System.out.println("Knoten " + num + " Double " + wert + "\n");		
	}

	@Override
	public double calcAt(double x) {
		return wert;
	}
}
