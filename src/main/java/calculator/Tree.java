package calculator;

import java.util.ArrayList;

public class Tree {

	public Tree(ArrayList<Token> tokens) {
		this.root = new Node(tokens);
	}

	double eval() throws Exception {
		root.expand();
		return root.eval();
	}

	private Node root;

}
