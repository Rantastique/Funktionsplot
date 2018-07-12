package scanner_parser;

public class LogOp extends Knoten {
	private Knoten op;

	public LogOp(Knoten k) {
		this.op = k;
	}

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
		return new DivOp(op.ableitung(), op);
	}

}
