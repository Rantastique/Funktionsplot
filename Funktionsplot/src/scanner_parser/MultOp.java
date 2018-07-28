package scanner_parser;

public class MultOp extends DyadOp{
	public MultOp(Knoten l, Knoten r) {
		super(l, r);
	}

	public MultOp() { }

	@Override
	public void print() {
		System.out.println("Knoten " + num + " Mult ");
		System.out.println("linker Operrand: " + leftOp.getNum());
		System.out.println("rechter Operrand: " + rightOp.getNum());
		System.out.println();
		leftOp.print();
		rightOp.print();
	}

	@Override
	public double calcAt(double x) {
		return leftOp.calcAt(x)*rightOp.calcAt(x);
	}

	@Override
	public Knoten ableitung() {
		MultOp mult1 = new MultOp(leftOp.ableitung(), rightOp.copy());
		MultOp mult2 = new MultOp(leftOp.copy(), rightOp.ableitung());
		return new AddOp(mult1, mult2);
	}
	
	@Override
	public Knoten copy() {
		return new MultOp(leftOp.copy(),rightOp.copy());
	}
}
