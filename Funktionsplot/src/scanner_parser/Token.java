package scanner_parser;

public class Token {
	public static final int
		EOF = 1,
		Plus = 2,
		Minus = 3,
		Mult = 4,
		Div = 5,
		Hoch = 6,
		Identifier = 7,
		Ganzzahl = 8,
		Number = 9,
		LKlamm = 10,
		RKlamm = 11,
		Sin = 12,
		Cos = 13,
		Log = 14,
		Err = 99;
	public int tokenId;
	public String text;
	Token(int id, String text) {
		this.tokenId = id;
		this.text = text;
	}
	@Override
	public String toString() {
		return "Token: " + tokenId + " text: " + text;
	}
}
