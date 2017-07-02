package greg;

public class Order {

	public long time;

	public enum BuySell {
		Buy, Sell
	}

	public BuySell action;

	public Order(long time, BuySell action) {
		this.time = time;
		this.action = action;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Order) {
			return ((Order) other).time == this.time && ((Order) other).action == this.action;
		}
		return false;
	}

}
