import java.util.concurrent.atomic.AtomicInteger;

public class SCCAS implements SharedCounter{ //Compare And Swap
    private AtomicInteger lock;
    private int counter;
    private int locked=1;
    private int unlocked=0;
    private Boolean max_reached_flag =false ;


    public SCCAS() {
        this.lock = new AtomicInteger(unlocked);
    }


    public void increment() throws InterruptedException {
        while (true)
        {
            int curr_state = lock.compareAndExchange(unlocked, locked); // try to acquire lock
            if(curr_state == unlocked)
            {
                if(counter < max_counter){
                counter++;
                }
                else
                {
                    max_reached_flag = true;
                }
                lock.compareAndExchange(locked, unlocked); // unlock. could also use lock.set(unlocked)
                break;
            }
        }
    }

    public int getCounter() {
        return counter;
    }
    public Boolean maxReached()
    {
        return max_reached_flag;
    }


}
