public class ThreadSafetyTest {
    private static final int NUM_THREADS = 6;
    private static final int ADD_PER_THREAD = 500;

    public static void main(String[] args)
    {

        String res2 = testSet(new ListImpl());
        System.out.println(res2);

        String res1 = testSet(new FineGrainedList());

        System.out.println(res1);

    }
    public static String testSet(IntegerSet set)
    {
        String implementationName = set.getClass().getSimpleName();
        Thread[] add_threads = new Thread[NUM_THREADS];
        for (int i=0; i<NUM_THREADS;i++)
        {
            int curr_i = i;
            add_threads[i] = new Thread(() -> {
                for(int j=0; j<ADD_PER_THREAD;j++) {
                   set.add(curr_i*ADD_PER_THREAD + j);
                }
            });
        }

        for (Thread thread : add_threads) {
            thread.start(); // start the threads
        }
        for (Thread thread : add_threads) {
            try {
                thread.join(); // wait for the threads to terminate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Thread[] remove_threads = new Thread[NUM_THREADS];
        for (int i=0; i<NUM_THREADS;i++)
        {
            int curr_i = i;
            remove_threads[i] = new Thread(() -> {
                for(int j=curr_i; j<ADD_PER_THREAD*NUM_THREADS;j+=NUM_THREADS)
                {
                    set.remove(j);
                }
            });
        }

        for (Thread thread : remove_threads) {
            thread.start(); // start the threads
        }
        for (Thread thread : remove_threads) {
            try {
                thread.join(); // wait for the threads to terminate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String res = implementationName;
        int counter = 0 ;
        for(int i=0; i<ADD_PER_THREAD*NUM_THREADS;i++)
        {
            if(set.contains(i))
            {
                counter ++;
            }
        }
        if(counter > 0)
        {
            res+=" Test Failed: "+counter+" Contained";
        }
        else
        {
            res+=" Test Passed";
        }
        return res;

    }

}
