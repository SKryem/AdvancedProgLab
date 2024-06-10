import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloThreads {
    public static void execute(int numTasks, int numThreads) {
        ComputationTask tasks[] = new ComputationTask[numTasks];
        for(int i = 0; i < numTasks; i++) {
            tasks[i] = new ComputationTask();
        }
        int tasks_per_thread = numTasks/numThreads;
        Thread[] threads = new Thread[numThreads];
        for (int i=0; i<numThreads;i++)
        {
            int curr_thread_index = i;
            threads[i] = new Thread(){
                public void run(){
                    Set<Integer> tasks_to_run = new HashSet<Integer>();
                    for (int j = 0; j<tasks_per_thread; j++) {
                        int offset_index = curr_thread_index * tasks_per_thread;
                        tasks_to_run.add(j+offset_index);
                    }

                    for( int task_num : tasks_to_run)
                    {
                        tasks[task_num].compute();
                    }
                    /*synchronized (System.out) {
                        System.out.println("Thread number " + Thread.currentThread().getName() + " executed tasks:");
                        for (int task_num : tasks_to_run) {
                            System.out.print(task_num + ", ");
                        }
                        System.out.println();
                    }*/
                }
            };
            threads[i].setName(Integer.toString(i));
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

    }



    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        if (args.length < 2){
            System.out.println("Usage: HelloThreads <number of tasks> <number of threads>");
            return;
        }

        int numTasks = Integer.parseInt(args[0]);
        int numThreads = Integer.parseInt(args[1]);

        execute(numTasks, numThreads);

        long finishTime = System.currentTimeMillis();
        System.out.println("Excecution time: "+(finishTime-startTime)+" ms");
    }
}
