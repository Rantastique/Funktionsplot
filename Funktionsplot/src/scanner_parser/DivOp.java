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
	public Double calcAt(double x) {
		Double leftValue=leftOp.calcAt(x);
		if(leftValue==0)
		{
			return (double)0;
		}
		Double rightValue = rightOp.calcAt(x);
		if(rightValue==0) {
			return Double.NaN;
		}
		return leftOp.calcAt(x)/rightOp.calcAt(x);
	}

	@Override
	public Knoten ableitung() {
		MultOp zaehler1 = new MultOp(leftOp.ableitung(), rightOp.copy());
		MultOp zaehler2 = new MultOp(leftOp.copy(), rightOp.ableitung());
		SubOp zaehler = new SubOp(zaehler1, zaehler2);
		MultOp nenner = new MultOp(rightOp.copy(), rightOp.copy());
		return new DivOp(zaehler, nenner);
	}
	
	@Override
	public Knoten copy() {
		return new DivOp(leftOp.copy(),rightOp.copy());
	}
}
