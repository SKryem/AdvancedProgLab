import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SCLock implements SharedCounter {


    private int counter;
    private final Lock lock;
    private Boolean max_reached_flag =false ;


    public SCLock() { // constructor
        this.counter = 0;
        this.lock = new ReentrantLock();
    }

    public  void increment() {
        lock.lock();
        if(counter<max_counter) {
            counter++;
        }
        else
        {
            max_reached_flag = true;
        }
        lock.unlock();
    }

    public  int getCounter() {
        lock.lock();
        int res = counter;
        lock.unlock();

        return res;
    }

    public Boolean maxReached()
    {
        return max_reached_flag;
    }

}