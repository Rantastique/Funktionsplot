package scanner_parser;

public class DivOp extends DyadOp{
	@Override
	public void print() {
		System.out.println("Knoten " + num + " Div ");
		System.out.println("linker Operrand: " + leftOp.getNum());
		System.out.println("rechter Operrand: " + rightOp.getNum());
		System.out.println();
		leftOp.print();
		rightOp.print();
	}
}
