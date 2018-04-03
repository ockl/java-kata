package calculator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class Tests {

	@Test
	public void parserTest() throws Exception {
		ArrayList<Token> tokens;
		Parser parser = new Parser("123.123*(45+67)");
		tokens = parser.parse();
		assertEquals(7, tokens.size());
		assertEquals(new Token(123.123), tokens.get(0));
		assertEquals(new Token(Token.Type.Mul), tokens.get(1));
		assertEquals(new Token(Token.Type.OpeningBracket), tokens.get(2));
		assertEquals(new Token(45), tokens.get(3));
		assertEquals(new Token(Token.Type.Add), tokens.get(4));
		assertEquals(new Token(67), tokens.get(5));
		assertEquals(new Token(Token.Type.ClosingBracket), tokens.get(6));
	}

	void verify(double value, String expression) throws Exception {
		assertEquals(value, Calculator.calculate(expression), 10e-4);
	}

	@Test
	public void calculatorTests() throws Exception {
		verify(7, "5*1+2");
		verify(15, "5*(1+2)");
		verify(15, "(1+2)*5");
		verify(770, "3.5*(4+18)*10");
		verify(54, "2*(3*(4+5))");
		verify(10, "2*3+4");
		verify(10, "4+3*2");
		verify(1.5, "5/2-1");
		verify(4, "4");
		verify(4, "(4)");
	}

	@Test(expected = Exception.class)
	public void operatorMissingTest() throws Exception {
		Calculator.calculate("2(1+1)");
	}

	@Test(expected = Exception.class)
	public void negativeValuesTest() throws Exception {
		Calculator.calculate("-2");
	}

}
