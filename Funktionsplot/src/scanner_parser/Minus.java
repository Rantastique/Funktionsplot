package scanner_parser;

public class Minus extends Knoten {
	private Knoten op;
	public Minus(Knoten k) {
		this.op = k;
	}
	public Minus() { }
	
	public void setOp(Knoten op) {
		this.op = op; 
	}
	
	@Override
	public void print() {
		System.out.println("Knoten " + num + " Minus ");
		System.out.println("Operrand: " + op.getNum());
		System.out.println();
		op.print();
	}
	
	@Override
	public double calcAt(double x) {
		return op.calcAt(x)*(-1);
	}
	
	@Override
	public Knoten ableitung() {
		return new Minus(op.ableitung());
	}
}
