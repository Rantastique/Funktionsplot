package scanner_parser;

import java.util.Scanner;

import funktionsplot.Funktion;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Input:");
		String inputLine = input.nextLine();
		Funktion f = FuncParser.theParser().parse(inputLine);
		f.print();
		//testcode "berechnen"
		System.out.println("Variable=");
		double x = input.nextDouble();
		double y = FuncParser.theParser().getWurzel().calcAt(x);
		System.out.println(y);
		System.out.println("Ableitung:");
		Funktion g = f.ableitung();
		g.print();
		input.close(); 
	}

}
