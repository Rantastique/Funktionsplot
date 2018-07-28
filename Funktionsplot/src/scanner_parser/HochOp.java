package scanner_parser;

public class HochOp extends DyadOp{
	public HochOp(Knoten l, Knoten r) {
		super(l, r);
	}

	public HochOp() { }

	@Override
	public void print() {
		System.out.println("Knoten " + num + " Hoch ");
		System.out.println("linker Operrand: " + leftOp.getNum());
		System.out.println("rechter Operrand: " + rightOp.getNum());
		System.out.println();
		leftOp.print();
		rightOp.print();
	}

	@Override
	public double calcAt(double x) {
		return Math.pow(leftOp.calcAt(x), rightOp.calcAt(x));
	}

	@Override
	public Knoten ableitung() {
		
		return new MultOp(new AddOp(new MultOp(rightOp.ableitung(), new LogOp(leftOp.copy())),
				new MultOp(rightOp.copy(), new DivOp(leftOp.ableitung(),leftOp.copy()))), new HochOp(leftOp.copy(), rightOp.copy()));
		//HochOp f = new HochOp(leftOp,rightOp); //funktion selbst
		//MultOp produkt = new MultOp(rightOp, new LogOp(leftOp)); //produkt von  e^produkt
		//return new MultOp(f, produkt.ableitung()); //ableitung von e^produkt
	}
	
	@Override
	public Knoten copy() {
		return new HochOp(leftOp.copy(),rightOp.copy());
	}
}
