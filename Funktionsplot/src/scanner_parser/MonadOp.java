package scanner_parser;

public abstract class MonadOp extends Knoten {
	
	protected Knoten op;
	
	public MonadOp() {}
	public MonadOp(Knoten op) {
		this.op = op;
	}
	public void setOp(Knoten op) {
		this.op = op; 
	}
}
