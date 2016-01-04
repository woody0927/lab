package idv.woody.future;

import java.util.concurrent.Callable;

public class SleepCallable implements Callable<String> {

	final String name;
	final long period;

	public SleepCallable(final String name, final long period) {
		this.name = name;
		this.period = period;
	}

	public String call() {
		try {
			Thread.sleep(period);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		return name;
	}
}
