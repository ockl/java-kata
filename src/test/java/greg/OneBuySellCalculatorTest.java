package greg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OneBuySellCalculatorTest {

	private static PublicTrade mkTrade(long time, double price) {
		return new PublicTrade(time, price);
	}

	private static double Delta = 10e-8;

	@Test
	public void test1() {
		PublicTrade[] trades = { mkTrade(100, 4), mkTrade(101, 5), mkTrade(102, 1), mkTrade(103, 3), mkTrade(104, 3) };
		OneBuySellCalculator c = new OneBuySellCalculator(trades);
		assertEquals(102, c.getBuyTime());
		assertEquals(104, c.getSellTime());
		assertEquals(2, c.getProfit(), Delta);
	}

	@Test
	public void test2() {
		PublicTrade[] trades = { mkTrade(100, 2), mkTrade(101, 3), mkTrade(102, 1) };
		OneBuySellCalculator c = new OneBuySellCalculator(trades);
		assertEquals(100, c.getBuyTime());
		assertEquals(101, c.getSellTime());
		assertEquals(1, c.getProfit(), Delta);
	}

	@Test
	public void test3() {
		PublicTrade[] trades = { mkTrade(100, 2), mkTrade(101, 4), mkTrade(102, 1), mkTrade(103, 3), mkTrade(104, 2), mkTrade(105, 5), mkTrade(106, 2) };
		OneBuySellCalculator c = new OneBuySellCalculator(trades);
		assertEquals(102, c.getBuyTime());
		assertEquals(105, c.getSellTime());
		assertEquals(4, c.getProfit(), Delta);
	}

	@Test
	public void test4() {
		PublicTrade[] trades = { mkTrade(100, 3), mkTrade(101, 2), mkTrade(102, 1) };
		OneBuySellCalculator c = new OneBuySellCalculator(trades);
		assertEquals(-1, c.getBuyTime());
		assertEquals(-1, c.getSellTime());
		assertEquals(0, c.getProfit(), Delta);
	}

	@Test
	public void test5() {
		PublicTrade[] trades = { mkTrade(100, 1), mkTrade(101, 2), mkTrade(102, 3), mkTrade(103, 2), mkTrade(104, 4) };
		OneBuySellCalculator c = new OneBuySellCalculator(trades);
		assertEquals(100, c.getBuyTime());
		assertEquals(104, c.getSellTime());
		assertEquals(3, c.getProfit(), Delta);
	}

}
