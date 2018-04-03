package calculator;

public class Token {

	public enum Type {
		Value, Add, Sub, Mul, Div, OpeningBracket, ClosingBracket
	}

	public Token(Type type) {
		this.type = type;
		this.value = 0;
	}

	public Token(double value) {
		this.type = Type.Value;
		this.value = value;
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject instanceof Token) {
			Token otherToken = (Token) otherObject;
			return this.type.equals(otherToken.type) && this.value == otherToken.value;
		}
		return false;
	}

	@Override
	public String toString() {
		return type + "/" + value;
	}

	public boolean isOpening() {
		return type.equals(Type.OpeningBracket);
	}

	public boolean isClosing() {
		return type.equals(Type.ClosingBracket);
	}

	public boolean isMulDiv() {
		return type.equals(Type.Mul) || type.equals(Type.Div);
	}

	public boolean isAddSub() {
		return type.equals(Type.Add) || type.equals(Type.Sub);
	}

	public boolean isValue() {
		return type.equals(Type.Value);
	}

	public double getValue() {
		return value;
	}

	public Type getType() {
		return type;
	}

	private Type type;
	private double value;
}
