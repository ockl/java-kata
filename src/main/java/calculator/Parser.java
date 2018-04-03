package calculator;

import java.util.ArrayList;

public class Parser {

	public Parser(String expression) {
		this.expression = expression;
	}

	private boolean isDigitOrDot(int i) {
		Character character = expression.charAt(i);
		return Character.isDigit(character) || character == '.';
	}

	public ArrayList<Token> parse() throws Exception {
		ArrayList<Token> tokens = new ArrayList<Token>();
		int i = 0;
		while (i < expression.length()) {
			Character character = expression.charAt(i);
			if (isDigitOrDot(i)) {
				int j = i + 1;
				while (j < expression.length() && isDigitOrDot(j)) {
					j++;
				}
				tokens.add(new Token(Double.parseDouble(expression.substring(i, j))));
				i = j;
			} else if (character.equals('+')) {
				tokens.add(new Token(Token.Type.Add));
				i++;
			} else if (character.equals('-')) {
				tokens.add(new Token(Token.Type.Sub));
				i++;
			} else if (character.equals('*')) {
				tokens.add(new Token(Token.Type.Mul));
				i++;
			} else if (character.equals('/')) {
				tokens.add(new Token(Token.Type.Div));
				i++;
			} else if (character.equals('(')) {
				tokens.add(new Token(Token.Type.OpeningBracket));
				i++;
			} else if (character.equals(')')) {
				tokens.add(new Token(Token.Type.ClosingBracket));
				i++;
			} else {
				throw new Exception("unexpected character '" + character + "'");
			}
		}
		return tokens;
	}

	private String expression;
}
