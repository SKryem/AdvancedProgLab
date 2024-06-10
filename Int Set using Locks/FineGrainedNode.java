import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedNode {
    FineGrainedNode next;
    int data;
    final private Lock  _lock = new ReentrantLock();

    FineGrainedNode(int d)
    {
        data = d;
    }
    public void lock()
    {
        _lock.lock();
    }
    public void unlock()
    {
        _lock.unlock();
    }
}
