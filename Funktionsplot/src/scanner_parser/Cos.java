package scanner_parser;

public class Cos extends MonadOp {

	public Cos() {}
	public Cos(Knoten k) {
		super();
	}

	@Override
	public double calcAt(double x) {
		return Math.cos(x) ;
	}

	@Override
	public Knoten ableitung() {
		return new Minus(new MultOp(new Sin(op), op.ableitung()));
	}

	@Override
	public void print() {
		System.out.println("Knoten " + num + " cos() ");
		System.out.println("Operrand: " + op.getNum());
		System.out.println();
		op.print();
		
	}
}
