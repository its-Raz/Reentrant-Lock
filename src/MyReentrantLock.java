import java.io.Closeable;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyReentrantLock implements Lock{

    public AtomicBoolean lock;
    public Thread owner;
    public int ownerNumOfLocks;
    public static int lockNum=1;
    public int lockID;

    public ArrayDeque<Thread> q;




    public MyReentrantLock()
    {
        System.out.println(Thread.currentThread() + " IS CREATING A LOCK NUMBER " + lockNum);
        lockID=lockNum;
        lock = new AtomicBoolean(false);
        lockNum++;
        q = new ArrayDeque<>();
    }


    /**
     *
     */
    @Override
    public void acquire() {
        Thread currentThread = Thread.currentThread();
        if(owner == currentThread)
        {
            ownerNumOfLocks++;
            System.out.println("Owner " + owner.getName() + " is locking key number " +
                    lockID + " in the" + ownerNumOfLocks + " time");
            return;
        }
        else
        {
            q.add(currentThread);
        while (q.peek()!=currentThread)
        {
            try {
                currentThread.sleep(5000);
            }
            catch(InterruptedException e)
            {
                System.out.println("Thread " + currentThread.getName() + " wakedUp");
            }
        }
            System.out.println(lock.compareAndSet(false,true) +" THE LOCK LOCKED");
        owner=currentThread;
        ownerNumOfLocks=1;
        System.out.println(Thread.currentThread() + " IS GETTING OWNER SHIP OF LOCK NUMBER " + this.lockID);
        }
    }







    /**
     * @return
     */
    @Override
    public boolean tryAcquire() {
        System.out.println("sldifh2lwesdklfwelifnwleifnlweinflwekfnwlkefn"); return true;
//        System.out.println("in tryAcuire");
//        if(Thread.currentThread() == owner || lock.compareAndSet(false,true))
//        {
//            try
//            {
//                owner = Thread.currentThread();
//                ownerNumOfLocks++;
//                System.out.println(Thread.currentThread() + " IS GETTING OWNER SHIP OF LOCK NUMBER BY tryAcquire " + this.lockID);
//                return true;
//            }
//            catch (Exception e)
//            {
//                owner=null;
//                lock.set(false);
//                throw new RuntimeException();
//
//            }
//        }
//        return false;

    }

    /**
     *
     */
    @Override
    public void release() {

        if(Thread.currentThread() != owner)
        {
            System.out.println("not owner");
            throw new RuntimeException();
        }
        else{
            ownerNumOfLocks--;
            System.out.println(Thread.currentThread() + "released " + this + "now number of releases" + ownerNumOfLocks);
            if(ownerNumOfLocks == 0)
            {
                try{
                System.out.println(Thread.currentThread() + " IS RELEASING FINALLY " + this);
                lock.set(false);
                owner=null;
                    System.out.println(q.pollFirst() + " is out of line");
                    if(q.peek()!=null){
                   q.peek().interrupt();}
                }
                catch (Exception e)
                {
                    System.out.println("exception in realase");
                    throw new RuntimeException();
                }

            }
        }

    }

    /**

     */
    @Override
    public void close()  {
        System.out.println("in close");
        try{
        this.release();}
        catch(Exception e){
            System.out.println("exception in close");
            throw new RuntimeException();
            }

    }
}
