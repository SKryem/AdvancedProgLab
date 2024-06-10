import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedList implements  IntegerSet{

    FineGrainedNode head;
    AtomicInteger size;
    Lock set_lock;
    FineGrainedList()
    {
        head = null;
        size = new AtomicInteger(0);
        set_lock = new ReentrantLock();
    }

    @Override
    public boolean add(int i) {


        FineGrainedNode to_add = new FineGrainedNode(i);
        set_lock.lock();
        if (head == null) // list empty
        {
            head = to_add;
            size.getAndIncrement();
            set_lock.unlock();
            return true;
        }

        if(head.data > i)
        {
            to_add.next = head;
            this.head=to_add;
            size.getAndIncrement();
            set_lock.unlock();
            return true;
        }
        set_lock.unlock();
        FineGrainedNode curr = head;
        FineGrainedNode prev = null;
        curr.lock();
        while(curr != null)
        {
            if(curr.data < i) // advance to next node
            {
                if(prev!=null)
                {
                    prev.unlock();
                }
                if(curr.next!=null)
                {
                    curr.next.lock();
                }
                prev = curr;
                curr = curr.next;
            }
            else
            {
                if(curr.data == i)//element already exists
                {
                    if(prev!=null)
                    {
                        prev.unlock();
                    }
                    curr.unlock();
                    return false;
                }
                else // add the node
                {
                    prev.next = to_add;
                    to_add.next = curr;
                    prev.unlock();
                    curr.unlock();
                    size.getAndIncrement();
                    return true;
                }
            }
        }
        // reached end of list (curr = null, prev=last_node), add to the end
        prev.next = to_add;
        to_add.next = null;
        prev.unlock();
        size.getAndIncrement();
        return true;
    }

    @Override
    public boolean remove(int i) {
        set_lock.lock();
        if (head == null) // list empty
        {
            set_lock.unlock();
            return false;
        }
        if(head.data == i) // remove head
        {
            FineGrainedNode temp = head;
            temp.lock();
            this.head = head.next;
            temp.unlock();
            size.getAndDecrement();

            set_lock.unlock();
            return true;
        }

        FineGrainedNode curr = head;
        FineGrainedNode prev = null;

        curr.lock(); // curr = head != null
        set_lock.unlock();
        while(curr != null)
        {
            if(curr.data < i) // advance to next node
            {
                if(prev!=null)
                {
                    prev.unlock(); // prev of this iteration was the current of last iteration, so it is already locked
                }
                if(curr.next!=null)
                {
                    curr.next.lock();
                }


                prev = curr;
                curr = curr.next;
            }
            else
            {
                if(curr.data == i)//found the node
                {
                    prev.next = curr.next; // prev can be null only if curr=head, but this case was handled above
                    curr.unlock();
                    prev.unlock();
                    size.getAndDecrement();
                    return true;
                }
                else { // skipped node / not found
                    break;
                }
            }
        }
        prev.unlock();
        // reached end of list (curr = null, prev=last_node), did not find the node.
        return false;
    }

    @Override
    public boolean contains(int i) {
        set_lock.lock();
        if(head == null)
        {
            set_lock.unlock();
            return false;
        }
        set_lock.unlock();
        FineGrainedNode curr = head;

        curr.lock();
        while(curr!= null)
        {
            if(curr.data == i)
            {
                curr.unlock();
                return true;
            }

            if(curr.next!=null)
            {
                curr.next.lock();
            }
            curr.unlock();
            curr=curr.next;
        }
        return false;
    }
    public int getSize()
    {
        return size.get();
    }
}
