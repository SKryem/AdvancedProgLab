
public class SCSync implements SharedCounter {


    private int counter;
    private Boolean max_reached_flag =false ;


    public synchronized void increment() throws InterruptedException {
        if(counter < max_counter) {
            counter++;
        }
        else
        {
            max_reached_flag = true;
        }
    }

    public synchronized int getCounter() {
        return counter;
    }

    public Boolean maxReached()
    {
        return max_reached_flag;
    }

}
