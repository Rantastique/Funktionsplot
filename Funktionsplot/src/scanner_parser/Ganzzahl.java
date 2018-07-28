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
	
	@Override
	public Double calcAt(double x) {
		return (double) wert;
	}
	
	@Override
	public Knoten ableitung() {
		return new Ganzzahl(0);
	}
	
	@Override
	public Knoten copy() {
		return new Ganzzahl(wert);
	}
}
