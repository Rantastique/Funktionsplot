package scanner_parser;

public class AddOp extends DyadOp {
	public AddOp(Knoten l, Knoten r) {
		super(l, r);
	}

	public AddOp() { }

	@Override
	public void print() {
		System.out.println("Knoten " + num + " Add ");
		System.out.println("linker Operrand: " + leftOp.getNum());
		System.out.println("rechter Operrand: " + rightOp.getNum());
		System.out.println();
		leftOp.print();
		rightOp.print();
	}

	@Override
	public double calcAt(double x) {
		
		return leftOp.calcAt(x)+rightOp.calcAt(x);
	}

	@Override
	public Knoten ableitung() {
		return new AddOp(leftOp.ableitung(), rightOp.ableitung());
	}
}
