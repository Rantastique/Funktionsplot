package scanner_parser;

public class LogOp extends MonadOp {

	public LogOp(Knoten k) {
		super(k);
	}
	
	public LogOp() {}

	@Override
	public void print() {
		System.out.println("Knoten " + num + " ln() ");
		System.out.println("Operrand: " + op.getNum());
		System.out.println();
		op.print();		
	}

	@Override
	public double calcAt(double x) {
		return Math.log(op.calcAt(x));
	}

	@Override
	public Knoten ableitung() {
		return new DivOp(op.ableitung(), op.copy());
	} 
	
	@Override
	public Knoten copy() {
		return new LogOp(op.copy());
	}

}
