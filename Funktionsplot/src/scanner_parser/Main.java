package scanner_parser;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Input:");
		String inputLine = input.nextLine();
		FuncParser.theParser().parse(inputLine);
		FuncParser.theParser().getWurzel().print();
		input.close();
	}

}
