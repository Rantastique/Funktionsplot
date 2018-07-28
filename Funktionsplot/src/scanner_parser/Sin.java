package scanner_parser;

public class Sin extends MonadOp{

	
	public Sin() {}
	public Sin(Knoten k) {
		super(k);
	}

	@Override
	public Double calcAt(double x) {
		return Math.sin(op.calcAt(x));
	}

	@Override
	public Knoten ableitung() {
		return new MultOp(new Cos(op.copy()), op.ableitung());
	}

	@Override
	public void print() {
		System.out.println("Knoten " + num + " sin() ");
		System.out.println("Operrand: " + op.getNum());
		System.out.println();
		op.print();
		
	}

	@Override
	public Knoten copy() {
		return new Sin(op.copy());
	}
}
