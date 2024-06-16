import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SingleThreadExecutor {
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread workerThread;
    private volatile boolean running = true;

    public SingleThreadExecutor() {
        taskQueue = new LinkedBlockingQueue<>();
        workerThread = new Thread(() -> {
            try {
                while (running || !taskQueue.isEmpty()) {
                    Runnable task = taskQueue.poll();
                    if (task != null) {
                        task.run();
                    }
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        });
        workerThread.start();
    }

    public void execute(Runnable task) {
        if (!running) {
            throw new IllegalStateException("Executor has been shut down");
        }
        taskQueue.offer(task);
    }

    public void shutdown() {
        running = false;
        workerThread.interrupt();  // To wake up the worker thread if it's waiting
    }

    public void awaitTermination() {
        try {
            workerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        SingleThreadExecutor executor = new SingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            String taskName = "Task " + i;
            executor.execute(() -> {
                System.out.println("Hello world from " + taskName);
            });
        }

        executor.shutdown();
        executor.awaitTermination();
        //attempting to submit tasks after shutdown will result it the following exception:
        // Exception in thread "main" java.lang.IllegalStateException: Executor has been shut down
        System.out.println("That's all, folks");
    }
}
