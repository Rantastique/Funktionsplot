package scanner_parser;

public class DivOp extends DyadOp{
	
	public DivOp(Knoten zaehler, Knoten nenner) {
		super(zaehler, nenner);
	}

	public DivOp() { }

	@Override
	public void print() {
		System.out.println("Knoten " + num + " Div ");
		System.out.println("linker Operrand: " + leftOp.getNum());
		System.out.println("rechter Operrand: " + rightOp.getNum());
		System.out.println();
		leftOp.print();
		rightOp.print();
	}

	@Override
	public double calcAt(double x) {
		return leftOp.calcAt(x)/rightOp.calcAt(x);
	}

	@Override
	public Knoten ableitung() {
		MultOp zaehler1 = new MultOp(leftOp.ableitung(), rightOp);
		MultOp zaehler2 = new MultOp(leftOp, rightOp.ableitung());
		SubOp zaehler = new SubOp(zaehler1, zaehler2);
		MultOp nenner = new MultOp(rightOp, rightOp);
		return new DivOp(zaehler, nenner);
	}
}
