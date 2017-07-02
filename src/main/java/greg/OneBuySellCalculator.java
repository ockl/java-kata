package greg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * We want to place exactly one buy order and one sell order.
 */
public class OneBuySellCalculator {

	long buyTime;
	long sellTime;
	double profit;

	public OneBuySellCalculator(PublicTrade[] trades) {
		if (trades.length < 2) {
			throw new RuntimeException();
		}

		double minFromFront[] = new double[trades.length];
		minFromFront[0] = trades[0].price;
		for (int i = 1; i < trades.length; i++) {
			minFromFront[i] = Math.min(minFromFront[i - 1], trades[i].price);
		}

		double maxFromBack[] = new double[trades.length];
		maxFromBack[trades.length - 1] = trades[trades.length - 1].price;
		for (int i = trades.length - 2; i >= 0; i--) {
			maxFromBack[i] = Math.max(maxFromBack[i + 1], trades[i].price);
		}

		double diffs[] = new double[trades.length];
		for (int i = 0; i < trades.length; i++) {
			diffs[i] = maxFromBack[i] - minFromFront[i];
		}

		// find beginning of section with largest difference (the point where we want to buy)
		double largestDiff = 0;
		int startIndex = 0;
		for (int i = 0; i < trades.length; i++) {
			if (diffs[i] > largestDiff) {
				largestDiff = diffs[i];
				startIndex = i;
			}
		}

		if (largestDiff <= 0) {
			buyTime = -1;
			sellTime = -1;
			profit = 0;
			return;
		}

		// get end of section containing biggestDiff (the point where we want to sell)
		int endIndex = trades.length - 1;
		{
			double min = minFromFront[startIndex];
			double max = maxFromBack[startIndex];
			for (int i = startIndex + 1; i < trades.length; i++) {
				if (minFromFront[i] != min || maxFromBack[i] != max) {
					endIndex = i - 1;
					break;
				}
			}
		}

		buyTime = trades[startIndex].time;
		sellTime = trades[endIndex].time;
		profit = trades[endIndex].price - trades[startIndex].price;
	}

	public long getBuyTime() {
		return buyTime;
	}

	public long getSellTime() {
		return sellTime;
	}

	public double getProfit() {
		return profit;
	}

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

	private static void log(String msg) {
		System.out.println(sdf.format(new Date(System.currentTimeMillis())) + ": " + msg);
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			throw new Exception("missing arguments");
		}

		ArrayList<PublicTrade> trades = new ArrayList<>();

		log("opening file");

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			int time = 100;
		    String line;
		    while ((line = br.readLine()) != null) {
		    	double price = Long.parseLong(line);
		    	trades.add(new PublicTrade(time++, price));
		    }
		}

		log("parsed " + trades.size() + " prices");

		OneBuySellCalculator c;
		{
			PublicTrade[] localTrades = new PublicTrade[trades.size()];
			trades.toArray(localTrades);
			c = new OneBuySellCalculator(localTrades);
		}

		log("profit = " + c.getProfit());
	}

}
