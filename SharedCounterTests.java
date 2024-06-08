import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SharedCounterTests {
    private static final int NUM_THREADS = 100;
    private static final int INCREMENTS_PER_THREAD = 1000;

    public static void main(String[] args) throws InterruptedException {
        testCounter(new SCSync());
        testCounter(new SCFAA());
        testCounter(new SCCAS());
        testCounter(new SCLock());
    }

    public static void testCounter(SharedCounter counter) throws InterruptedException {
        String implementationName = counter.getClass().getSimpleName();
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        // Create and submit tasks
        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    counter.increment();
                }
            });
        }

        // Shutdown the executor and wait for tasks to complete
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // Check the final counter value
        int expected = NUM_THREADS * INCREMENTS_PER_THREAD;
        int actual = counter.getCounter();
        synchronized (System.out) {
            System.out.println("Implementation " + implementationName + ":");
            System.out.println("Expected: " + expected + ", Actual: " + actual);

            if (expected != actual) {
                System.err.println("Test failed!");
            } else {
                System.out.println("Test passed!");
            }
        }
    }
}
