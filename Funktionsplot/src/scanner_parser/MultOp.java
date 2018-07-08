package scanner_parser;

public class MultOp extends DyadOp{
	@Override
	public void print() {
		System.out.println("Knoten " + num + " Mult ");
		System.out.println("linker Operrand: " + leftOp.getNum());
		System.out.println("rechter Operrand: " + rightOp.getNum());
		System.out.println();
		leftOp.print();
		rightOp.print();
	}
}
