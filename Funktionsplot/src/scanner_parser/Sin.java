package scanner_parser;

public class Sin extends MonadOp{

	
	public Sin() {}
	public Sin(Knoten k) {
		super(k);
	}

	@Override
	public double calcAt(double x) {
		return Math.sin(x) ;
	}

	@Override
	public Knoten ableitung() {
		return new MultOp(new Cos(op), op.ableitung());
	}

	@Override
	public void print() {
		System.out.println("Knoten " + num + " sin() ");
		System.out.println("Operrand: " + op.getNum());
		System.out.println();
		op.print();
		
	}

}
