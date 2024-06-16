import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloWorld implements Runnable {
    private final String taskName;

    public HelloWorld(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println("Hello world from task number " + taskName);
    }

    public static void main(String[] args) {
        int numTasks = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numTasks);

        for (int i = 0; i < numTasks; i++) {
            String taskName = Integer.toString(i);
            executor.submit(new HelloWorld(taskName)); // submit tasks to the executor
        }

        executor.shutdown(); // no more tasks will be submitted

        while (!executor.isTerminated()) {
            // wait for all tasks to complete
        }

        System.out.println("That's all, folks");
    }
}
