public interface SharedCounter extends Runnable{
    void increment();
    int getCounter();
    void run();
}
