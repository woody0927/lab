package idv.woody.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceDemo {

	public static void main(String[] args) {
		ExecutorService pool = null;
		final List<? extends Callable<String>> callables = Arrays.asList(
				new SleepCallable("quick", 500),
				new SleepCallable("slow", 5000));
		try {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime()
					.availableProcessors());
			System.out.println(String.format("now: %d", System.currentTimeMillis()));
			// invokeAll executes all the tasks concurrently, but the call itself blocks until all the tasks complete.
			for (final Future<String> future : pool.invokeAll(callables)) {
				System.out.println(String.format("get %s at: %d", future.get(), System.currentTimeMillis()));
			}
			System.out.println(String.format("finished at: %d", System.currentTimeMillis()));
			// } catch (ExecutionException | InterruptedException ex) { }
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pool == null) {
				pool.shutdown();
			}
		}
	}

}
