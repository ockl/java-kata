package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Node {

	public Node(List<Token> tokens) {
		this.tokens = tokens;
	}

	public void expand() throws Exception {
		if (tokens.size() == 0) {
			throw new Exception("tokens empty");
		}

		// remove outside brackets
		int startIndex = 0;
		int stopIndex = tokens.size() - 1;
		if (tokens.get(startIndex).isOpening() && tokens.get(stopIndex).isClosing()) {
			startIndex++;
			stopIndex--;
		}

		if (startIndex == stopIndex) {
			token = tokens.get(startIndex);
			if (!token.isValue()) {
				throw new Exception("leaf node not a value");
			}
			return;
		}

		// search for operators and expand them (+- higher in the tree for
		// correct precedence)
		ArrayList<Predicate<Token>> operators = new ArrayList<Predicate<Token>>();
		operators.add((Token token) -> {
			return token.isAddSub();
		});
		operators.add((Token token) -> {
			return token.isMulDiv();
		});
		for (Predicate<Token> operator : operators) {
			int operatorIndex = findOperatorOutsideBracket(startIndex, stopIndex, operator);
			if (operatorIndex != -1) {
				token = tokens.get(operatorIndex);
				left = new Node(tokens.subList(startIndex, operatorIndex));
				right = new Node(tokens.subList(operatorIndex + 1, stopIndex + 1));
				left.expand();
				right.expand();
				return;
			}
		}

		throw new Exception("failed to expand tokens: " + tokens.toString());
	}

	public double eval() throws Exception {
		Token.Type type = token.getType();
		switch (type) {
		case Value:
			return token.getValue();
		case Add:
			return left.eval() + right.eval();
		case Sub:
			return left.eval() - right.eval();
		case Mul:
			return left.eval() * right.eval();
		case Div:
			return left.eval() / right.eval();
		case OpeningBracket:
		case ClosingBracket:
			throw new Exception("node cannot be a bracket");
		default:
			throw new Exception("unknown type of node");
		}
	}

	private int findOperatorOutsideBracket(int startIndex, int stopIndex, Predicate<Token> operator) {
		int brackets = 0;
		for (int i = startIndex; i <= stopIndex; i++) {
			Token token = tokens.get(i);
			if (token.isOpening()) {
				brackets++;
			} else if (token.isClosing()) {
				brackets--;
			} else if (operator.test(token) && brackets == 0) {
				return i;
			}
		}
		return -1;
	}

	private Token token;
	private Node left;
	private Node right;

	private List<Token> tokens;

}
