package calculator;

import java.util.ArrayList;

public class Calculator {

	public static double calculate(String expression) throws Exception {
		Parser parser = new Parser(expression);
		ArrayList<Token> tokens = parser.parse();
		Tree tree = new Tree(tokens);
		return tree.eval();
	}

	public static void main(String[] args) {
		try {
			if (args.length > 1) {
				throw new Exception("unexpected number of arguments");
			}
			System.out.println(calculate(args[0]));
		} catch (Exception ex) {
			System.err.println("error: " + ex.getMessage());
			System.exit(-1);
		}
	}
}
