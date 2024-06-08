import java.util.concurrent.atomic.AtomicInteger;

public class SCCAS implements SharedCounter{
    private AtomicInteger lock;
    private int counter;
    private int locked=1;
    private int unlocked=0;

    public SCCAS() {
        this.lock = new AtomicInteger(unlocked);
    }


    public void increment() {
        while (true)
        {
            int curr_state = lock.compareAndExchange(unlocked, locked); // try to acquire lock
            if(curr_state == unlocked)
            {
                counter++;
                lock.compareAndExchange(locked, unlocked); // unlock. could also use lock.set(unlocked)
                break;
            }
        }
    }

    public int getCounter() {
        return counter;
    }


    @Override
    public void run() {
        this.increment();
    }
}
