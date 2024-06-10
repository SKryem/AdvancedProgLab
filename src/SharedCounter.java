public interface SharedCounter {
    int  max_counter = 1000000;

    void increment() throws InterruptedException;
    int getCounter();
    Boolean maxReached();
}
