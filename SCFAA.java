import java.util.concurrent.atomic.AtomicInteger;

public class SCFAA implements  SharedCounter {
    private AtomicInteger counter;

    public SCFAA() {
        this.counter = new AtomicInteger(0);
    }


    public  void increment() {
        counter.getAndAdd(1) ;
    }

    public  int getCounter() {
        return counter.getAndAdd(0) ;
    }


    @Override
    public void run() {
        this.increment();
    }
}
