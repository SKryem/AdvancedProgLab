import java.util.concurrent.atomic.AtomicInteger;

public class SCFAA implements  SharedCounter { // Fetch And Add
    private AtomicInteger counter;
    private Boolean max_reached_flag =false ;

    public SCFAA() {
        this.counter = new AtomicInteger(0);
    }


    public  void increment() throws InterruptedException {
        if(counter.get() < max_counter){
        counter.getAndAdd(1) ;
        }
        else
        {
            max_reached_flag = true;
        }
    }

    public  int getCounter() {
        return counter.getAndAdd(0) ;
    }
    public Boolean maxReached()
    {
        return max_reached_flag;
    }



}
