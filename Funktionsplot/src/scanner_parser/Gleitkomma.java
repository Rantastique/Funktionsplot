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
	public Double calcAt(double x) {
		return wert;
	}
	
	@Override
	public Knoten ableitung() {
		return new Ganzzahl(0);
	}

	@Override
	public Knoten copy() {
		return new Gleitkomma(wert);
	}
}
