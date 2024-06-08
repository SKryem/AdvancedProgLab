
public class SCSync implements SharedCounter {


    private int counter;

    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }


    @Override
    public void run() {
        this.increment();
    }
}
