package stockprofit;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

public class Benchmark {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

	private static void log(String msg) {
		System.out.println(sdf.format(new Date(System.currentTimeMillis())) + ": " + msg);
	}

	@Test
	public void benchmark() throws Exception {
		ArrayList<PublicTrade> trades = new ArrayList<>();

		log("opening file");

		try (BufferedReader br = new BufferedReader(new FileReader("1000000-32767-benchmark.txt"))) {
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
		assertEquals(32767, c.getProfit(), 10e-8);
	}

}
