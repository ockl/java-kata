package greg;

import java.util.ArrayList;

import greg.Order.BuySell;

/**
 * We can place as many orders as we want and we don't need to be concerned
 * about order fees.
 *
 * O(n) algorithm:
 * - take first derivative of time series
 * - find all sections where first derivative is positive (or zero)
 * - buy at start of each section, sell at the end - do this for all sections
 */
public class NoFeesCalculator {

	private Order[] orders;
	private double profit;

	public NoFeesCalculator(PublicTrade[] trades) {
		if (trades.length < 2) {
			orders = new Order[0];
			profit = 0;
			return;
		}

		// direction[i] tells us relationship between trades[i] and trades[i+1]
		int[] direction = new int[trades.length];

		for (int i = 0; i < trades.length - 1; i++) {
			PublicTrade curr = trades[i];
			PublicTrade next = trades[i + 1];
			if (next.price > curr.price) {
				direction[i] = 1;
			} else if (next.price < curr.price) {
				direction[i] = -1;
			} else {
				direction[i] = 0;
			}
		}

		// pretend that stock goes down at "the end of the day" for the
		// algorithm below to issue sell order if necessary
		direction[trades.length - 1] = -1;

		// The orders that we want to issue - every buy order will be followed
		// by a sell order
		ArrayList<Order> orders = new ArrayList<>();

		// The price of the stock at the time of our order - will be used for
		// profit calculation below
		ArrayList<Double> prices = new ArrayList<>();

		BuySell buySell = BuySell.Buy;
		for (int i = 0; i < direction.length; i++) {
			if (buySell == BuySell.Buy && direction[i] > 0) {
				orders.add(new Order(trades[i].time, BuySell.Buy));
				prices.add(trades[i].price);
				buySell = BuySell.Sell;
			} else if (buySell == BuySell.Sell && direction[i] < 0) {
				orders.add(new Order(trades[i].time, BuySell.Sell));
				prices.add(trades[i].price);
				buySell = BuySell.Buy;
			}
		}

		this.orders = new Order[orders.size()];
		orders.toArray(this.orders);

		for (int i = 0; i < prices.size() / 2; i++) {
			double buyPrice = prices.get(i * 2 + 0);
			double sellPrice = prices.get(i * 2 + 1);
			profit += sellPrice - buyPrice;
		}
	}

	public Order[] getOrders() {
		return orders;
	}

	public double getProfit() {
		return profit;
	}

}
