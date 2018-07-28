package scanner_parser;

public class Minus extends MonadOp {
	
	public Minus(Knoten k) {
		super(k);
	}
	public Minus() { 
		super();
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

	@Override
	public Knoten copy() {
		return new Minus(op.copy());
	}
}
