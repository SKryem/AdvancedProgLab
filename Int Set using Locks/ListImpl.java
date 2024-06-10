import java.util.concurrent.atomic.AtomicInteger;

public class ListImpl implements  IntegerSet{

    Node head;
    AtomicInteger size;

    ListImpl()
    {
        head = null;
        size = new AtomicInteger(0);
    }
    @Override
    public boolean add(int i) {


        Node to_add = new Node(i);

        if (head == null) // list empty
        {
            head = to_add;
            size.getAndIncrement();
            return true;
        }
        if(head.data > i)
        {
            to_add.next = head;
            this.head=to_add;
            size.getAndIncrement();
            return true;
        }
        Node curr = head;
        Node prev = null;

        while(curr != null)
        {
            if(curr.data < i) // advance to next node
            {
                prev = curr;
                curr = curr.next;
            }
            else
            {
                if(curr.data == i)//element already exists
                {
                    return false;
                }
                else // add the node
                {
                    prev.next = to_add;
                    to_add.next = curr;
                    size.getAndIncrement();
                    return true;
                }
            }
        }
        // reached end of list (curr = null, prev=last_node), add to the end
        prev.next = to_add;
        to_add.next = null;
        size.getAndIncrement();
        return true;
    }

    @Override
    public boolean remove(int i) {

        if (head == null) // list empty
        {
            return false;
        }
        if(head.data == i) // remove head
        {
            this.head = head.next;
            size.getAndDecrement();
            return true;
        }
        Node curr = head;
        Node prev = null;

        while(curr != null)
        {
            if(curr.data < i) // advance to next node
            {
                prev = curr;
                curr = curr.next;
            }
            else
            {
                if(curr.data == i)//found the node
                {
                    prev.next = curr.next; // delete
                    size.getAndDecrement();
                    return true;
                }
            }
        }
        // reached end of list (curr = null, prev=last_node), did not find the node.
        return false;
    }

    @Override
    public boolean contains(int i) {
        Node curr = head;
        while(curr!= null)
        {
            if(curr.data == i)
            {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }
    public int getSize()
    {
        return size.get();
    }
}
