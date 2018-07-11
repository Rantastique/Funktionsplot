package scanner_parser;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Input:");
		String inputLine = input.nextLine();
		FuncParser.theParser().parse(inputLine);
		FuncParser.theParser().getWurzel().print();
		//testcode "berechnen"
		System.out.println("Variable=");
		double x = input.nextDouble();
		double y = FuncParser.theParser().getWurzel().calcAt(x);
		System.out.println(y);
		//testcode ende
		input.close(); 
	}

}
