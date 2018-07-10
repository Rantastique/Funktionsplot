package scanner_parser;

public class HochOp extends DyadOp{
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
}
