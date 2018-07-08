package scanner_parser;

import java.util.*;

public class FuncParser {
/*
 * Geklaut von Prof. Dr. Uwe Schmidt (FH Wedel)
 * aus seinem Foliensatz für "Compilerbau"
 */
	private static FuncParser theparser = new FuncParser(); 
    private FuncScanner s;
    private Token t;
    private Knoten wurzel = null;
    public Knoten getWurzel()  {
    	return wurzel;
    }
    private Map<String,Double> declarations = new HashMap<String,Double>();
    public void declare(String varname) {
    	if (!declarations.containsKey(varname)){
    		declarations.put(varname, 0.0);
    	}
    }
    public double getValue(String varname) throws Exception {
    	if (!declarations.containsKey(varname)) throw new Exception("Variable not declared");
    	return declarations.get(varname);
    }
    public void setValue(String varname, double value) throws Exception {
    	if (!declarations.containsKey(varname)) throw new Exception("Variable not declared");
    	declarations.put(varname, value);
    }
    public String[] allVars() {
    	String[] varnames = declarations.keySet().toArray(new String[0]); 
    	return varnames;
    }
    public static FuncParser theParser() {
    	if (theparser == null) theparser = new FuncParser();
    	return theparser;
    }
    private FuncParser() {}
    void advance() {
       t = s.nextToken();
    }
    void eat(int token) {
        if ( token == t.tokenId ) {
            advance();
        } else {
            error();
        }
    }
    Knoten S() {
        Knoten k = E();
        if (t.tokenId != Token.EOF)
            error();
        return k;
    }
    Knoten E() {
    	DyadOp kd;
    	Knoten k;
        switch (t.tokenId) {
        case Token.Identifier:
        case Token.Number:
        case Token.Ganzzahl:
        case Token.LKlamm:
        case Token.Minus:
            k = T();
            if ((kd = Eprime()) != null) {
            	kd.setLeftOp(k);
            	k = kd;
            }
            break;
        default:
        	k = null;
            error();
        }
        return k;
    }
    DyadOp Eprime() {
    	DyadOp kd;
    	Knoten k;
        switch (t.tokenId) {
        case Token.Plus:
            advance();
            k = T();
            if ((kd = Eprime()) != null) {
            	kd.setLeftOp(k);
            	k = kd;
            }
            kd = new AddOp();
            kd.setRightOp(k);
            break;
        case Token.Minus:
            advance();
            k = T();
            if ((kd = Eprime()) != null) {
            	kd.setLeftOp(k);
            	k = kd;
            }
            kd = new SubOp();
            kd.setRightOp(k);
            break;
        case Token.EOF:
        	kd = null;
            break;
        case Token.RKlamm:
        	kd = null;
            break;
        default:
        	kd = null;
            error();
        }
        return kd;
    }
    Knoten T() {
    	DyadOp kd;
    	Knoten k;
        switch (t.tokenId) {
        case Token.Identifier:
        case Token.Number:
        case Token.Ganzzahl:
        case Token.LKlamm:
        case Token.Minus:
            k = F();
            if ((kd = Tprime()) != null) {
            	kd.setLeftOp(k);
            	k = kd;
            } 
            break;
        default:
            error();
            k = null;
        }
        return k;
    } 
    DyadOp Tprime() {
    	DyadOp kd;
    	Knoten k;
        switch (t.tokenId) {
        case Token.Plus:
        	kd = null;
            break;
        case Token.Minus:
        	kd = null;
            break;
        case Token.Mult:
            advance();
            k = F();
            if ((kd = Tprime()) != null) {
            	kd.setLeftOp(k);
            	k = kd;
            } 
            kd = new MultOp();
            ((DyadOp)kd).setRightOp(k);
            break;
        case Token.Div:
            advance();
            k = F();
            if ((kd = Tprime()) != null) {
            	kd.setLeftOp(k);
            	k = kd;
            } 
            kd = new DivOp();
            ((DyadOp)kd).setRightOp(k);
            break;
        case Token.EOF:
        	kd = null;
            break;
        case Token.RKlamm:
        	kd = null;
            break;
        default:
            error();
            kd = null;
        }
        return kd;
    }
    Knoten F() {
    	DyadOp kd;
    	Minus km;
    	Knoten k;
        switch (t.tokenId) {
        case Token.Identifier:
        	k = new Variable(t.text);
            advance();
            if ((kd = H()) != null) {
            	kd.setLeftOp(k);
            	k = kd;
            }
            break;
        case Token.Number:
        case Token.Ganzzahl:
        	k = new Gleitkomma(Double.valueOf(t.text));
            advance();
            break;
        case Token.Minus :
        	km = new Minus();
        	advance();
        	k = F();
        	km.setOp(k);
        	k = km;
        	break;
        case Token.LKlamm:
            advance();
            k = E();
            eat(Token.RKlamm);
            break;
        default:
            error();
            k = null;
        }
        return k;
    }
    DyadOp H() {
    	DyadOp kd = null;
    	Knoten k = null;
    	switch (t.tokenId) {
    	case Token.Hoch :
     		advance();
     		if (t.tokenId == Token.Ganzzahl) {
     			k = new Ganzzahl(Integer.valueOf(t.text));
     			kd = new HochOp();
     			kd.setRightOp(k);
     			advance();
     		} else {
     			error();
     		}
     		break;
    	case Token.EOF :
    	case Token.Plus :
    	case Token.Minus :
    	case Token.Mult :
    	case Token.Div :
    	case Token.RKlamm :
    		kd = null;
    		break;
    	default:
    		error();
    		kd = null;
    	}
    	return kd;
    }
    private int errcnt = 0;
    void error() {
        ++errcnt;
        System.out.println("parse error, illegal symbol \"" + t.text + "\"");
        if ( t.tokenId != Token.EOF ) {
            advance();
        }
    }
    public void parse(String input) {
    	s = new FuncScanner(input);
        advance();
        wurzel = S();
        System.out.println("Parsing done, " + errcnt + " error(s) found");
    }
 
}
