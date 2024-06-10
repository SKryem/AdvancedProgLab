import static java.lang.Math.min;

public class SharedCounterTests {
    private static final int NUM_THREADS = 4;
    private static final int INCREMENTS_PER_THREAD = SharedCounter.max_counter*10;

    public static void main(String[] args) throws InterruptedException {

        testCounter(new SCSync());
        testCounter(new SCFAA());
        testCounter(new SCLock());
        testCounter(new SCCAS());

    }

    public static void testCounter(SharedCounter counter) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        String implementationName = counter.getClass().getSimpleName();
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i=0; i<NUM_THREADS;i++)
        {
            threads[i] = new Thread(){
                public void run(){
                    for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                        if(counter.maxReached())
                        {
                            return;
                        }
                        try {
                            counter.increment();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    }
            };
        }

        for (Thread thread : threads) {
            thread.start(); // start the threads
        }
        for (Thread thread : threads) {
            try {
                thread.join(); // wait for the threads to terminate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Check the final counter value
        int expected = min(NUM_THREADS * INCREMENTS_PER_THREAD,SharedCounter.max_counter);
        int actual = counter.getCounter();
        synchronized (System.out) {
            System.out.println("***********************************************");
            System.out.println("Implementation " + implementationName + ":");
            System.out.println("Expected: " + expected + ", Actual: " + actual);

            if (expected != actual) {
                System.err.println("Test failed!");
            } else {
                System.out.println("Test passed!");
            }
            long finishTime = System.currentTimeMillis();
            System.out.println(implementationName+"Excecution time: "+(finishTime-startTime)+" ms");

        }
    }
}
