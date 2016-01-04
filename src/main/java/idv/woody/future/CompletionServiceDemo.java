package idv.woody.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceDemo {
	public static void main(String[] args) {
		ExecutorService pool = null;
		final List<? extends Callable<String>> callables = Arrays.asList(
				new SleepCallable("quick", 500),
				new SleepCallable("slow", 5000));
		try {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime()
					.availableProcessors());
			final CompletionService<String> service = new ExecutorCompletionService<String>(
					pool);
			System.out.println(String.format("now: %d",
					System.currentTimeMillis()));
			for (Callable<String> callable : callables) {
				service.submit(callable);
			}
			// get the result in completion order once it finished using CompletionService
			while (!pool.isTerminated()) {
				final Future<String> future = service.take();
				System.out.println(String.format("get %s at: %d", future.get(),
						System.currentTimeMillis()));
			}
			System.out.println(String.format("finished at: %d",
					System.currentTimeMillis()));
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
