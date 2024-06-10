public class ThreadSafetyTest {
    private static final int NUM_THREADS = 100;
    private static final int ADD_PER_THREAD = 10000;

    public static void main(String[] args) throws InterruptedException {

        String res2 = testSet(new ListImpl());
        System.out.println(res2);

        String res1 = testSet(new FineGrainedList());

        System.out.println(res1);

    }
    public static String testSet(IntegerSet set) throws InterruptedException
    {
        String implementationName = set.getClass().getSimpleName();
        Thread[] add_threads = new Thread[NUM_THREADS];
        for (int i=0; i<NUM_THREADS;i++)
        {
            add_threads[i] = new Thread(){
                public void run() {
                    for(int j=0; j<ADD_PER_THREAD*NUM_THREADS;j++) {
                       assert  (set.add(j));
                    }
                }
            };
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
            remove_threads[i] = new Thread(){
                public void run() {
                    for(int j=0; j<ADD_PER_THREAD*NUM_THREADS;j++)
                    {
                        set.remove(j);
                    }
                }
            };
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
//        if(set.getSize()==0)
//        {
//            res+=" Test Passed";
//            return res;
//        }
        for(int i=0; i<ADD_PER_THREAD*NUM_THREADS;i++)
        {
            if(set.contains(i))
            {
                res+=" Test Failed";
                return res;
            }
        }
        res+=" Test Passed";
        return res;

    }

    }
