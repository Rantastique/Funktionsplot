package scanner_parser;

public class Variable extends Knoten {
	private String name;
	public Variable(String name) {
		this.name = name;
		FuncParser.theParser().declare(this.name);
	}
	@Override
	public void print() {
		System.out.println("Knoten " + num + " Variable " + name + "\n");		
	}
	
	@Override
	public double calcAt(double x) {
		return x;
	}
	@Override
	public Knoten ableitung() {
		return new Ganzzahl(1);
	}
}
