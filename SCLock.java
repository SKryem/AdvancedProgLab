import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SCLock implements SharedCounter {


    private int counter;
    private final Lock lock;

    public SCLock() {
        this.counter = 0;
        this.lock = new ReentrantLock() {
        };
    }

    public synchronized void increment() {
        lock.lock();
        counter++;
        lock.unlock();
    }

    public synchronized int getCounter() {
        lock.lock();
        int res = counter;
        lock.unlock();

        return res;
    }


    @Override
    public void run() {
        this.increment();
    }
}