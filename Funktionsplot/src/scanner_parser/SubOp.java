package scanner_parser;

public class SubOp extends DyadOp {
	public SubOp(Knoten l, Knoten r) {
		super(l, r);
	}

	public SubOp() { }

	@Override
	public void print() {
		System.out.println("Knoten " + num + " Sub ");
		System.out.println("linker Operrand: " + leftOp.getNum());
		System.out.println("rechter Operrand: " + rightOp.getNum());
		System.out.println();
		leftOp.print();
		rightOp.print();
	}
	
	@Override
	public double calcAt(double x) {
		return leftOp.calcAt(x)-rightOp.calcAt(x);
	}

	@Override
	public Knoten ableitung() {
		return new SubOp(leftOp.ableitung(),rightOp.ableitung());
	}
}
