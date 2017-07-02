package greg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NoFeesCalculatorTest {

	private static PublicTrade mkTrade(long time, double price) {
		return new PublicTrade(time, price);
	}

	private static Order mkBuyOrder(long time) {
		return new Order(time, Order.BuySell.Buy);
	}

	private static Order mkSellOrder(long time) {
		return new Order(time, Order.BuySell.Sell);
	}

	private static double Delta = 10e-8;

	@Test
	public void empty() {
		PublicTrade[] trades = new PublicTrade[0];
		NoFeesCalculator c = new NoFeesCalculator(trades);
		assertEquals(0, c.getOrders().length);
		assertEquals(0, c.getProfit(), Delta);
	}

	@Test
	public void down1() {
		PublicTrade[] trades = { mkTrade(100, 2), mkTrade(101, 1) };
		NoFeesCalculator c = new NoFeesCalculator(trades);
		assertEquals(0, c.getOrders().length);
		assertEquals(0, c.getProfit(), Delta);
	}

	@Test
	public void down2() {
		PublicTrade[] trades = { mkTrade(100, 4), mkTrade(101, 3), mkTrade(102, 2) };
		NoFeesCalculator c = new NoFeesCalculator(trades);
		assertEquals(0, c.getOrders().length);
		assertEquals(0, c.getProfit(), Delta);
	}

	@Test
	public void noMove() {
		PublicTrade[] trades = { mkTrade(100, 1), mkTrade(101, 1), mkTrade(102, 1) };
		NoFeesCalculator c = new NoFeesCalculator(trades);
		assertEquals(0, c.getOrders().length);
		assertEquals(0, c.getProfit(), Delta);
	}

	@Test
	public void up1() {
		PublicTrade[] trades = { mkTrade(100, 1), mkTrade(101, 2) };
		NoFeesCalculator c = new NoFeesCalculator(trades);
		Order[] orders = c.getOrders();
		assertEquals(2, orders.length);
		assertEquals(mkBuyOrder(100), orders[0]);
		assertEquals(mkSellOrder(101), orders[1]);
		assertEquals(1, c.getProfit(), Delta);
	}

	@Test
	public void up2() {
		PublicTrade[] trades = { mkTrade(100, 1), mkTrade(101, 2), mkTrade(102, 3) };
		NoFeesCalculator c = new NoFeesCalculator(trades);
		Order[] orders = c.getOrders();
		assertEquals(2, orders.length);
		assertEquals(mkBuyOrder(100), orders[0]);
		assertEquals(mkSellOrder(102), orders[1]);
		assertEquals(2, c.getProfit(), Delta);
	}

	@Test
	public void upDown() {
		PublicTrade[] trades = { mkTrade(100, 1), mkTrade(101, 2), mkTrade(102, 1) };
		NoFeesCalculator c = new NoFeesCalculator(trades);
		Order[] orders = c.getOrders();
		assertEquals(2, orders.length);
		assertEquals(mkBuyOrder(100), orders[0]);
		assertEquals(mkSellOrder(101), orders[1]);
		assertEquals(1, c.getProfit(), Delta);
	}

	@Test
	public void downUp() {
		PublicTrade[] trades = { mkTrade(100, 2), mkTrade(101, 1), mkTrade(102, 2) };
		NoFeesCalculator c = new NoFeesCalculator(trades);
		Order[] orders = c.getOrders();
		assertEquals(2, orders.length);
		assertEquals(mkBuyOrder(101), orders[0]);
		assertEquals(mkSellOrder(102), orders[1]);
		assertEquals(1, c.getProfit(), Delta);
	}

	@Test
	public void upDownUp() {
		PublicTrade[] trades = { mkTrade(100, 1), mkTrade(101, 2), mkTrade(102, 1), mkTrade(103, 2) };
		NoFeesCalculator c = new NoFeesCalculator(trades);
		Order[] orders = c.getOrders();
		assertEquals(4, orders.length);
		assertEquals(mkBuyOrder(100), orders[0]);
		assertEquals(mkSellOrder(101), orders[1]);
		assertEquals(mkBuyOrder(102), orders[2]);
		assertEquals(mkSellOrder(103), orders[3]);
		assertEquals(2, c.getProfit(), Delta);
	}

}
