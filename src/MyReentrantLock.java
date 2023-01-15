import java.io.Closeable;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyReentrantLock implements Lock{

    public AtomicBoolean lock;
    public Thread owner;
    public int ownerNumOfLocks;
//    public static int lockNum=1;
//    public int lockID;

    public ArrayDeque<Thread> q;




    public MyReentrantLock()
    {
//        lockID=lockNum;
        lock = new AtomicBoolean(false);
//        lockNum++;
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
        }
        else
        {
            q.add(currentThread);
        while (owner!=null && q.peek()!=currentThread)
        {
            try {
                currentThread.sleep(10);
            }
            catch(InterruptedException e)
            {

            }
        }
        while(!lock.compareAndSet(false,true))
        {
            try{
            currentThread.sleep(10);}
            catch(Exception e)
            {
                System.out.println("exception in sleeping");
            }
        }
        owner=currentThread;
        ownerNumOfLocks=1;

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
            System.out.println(ownerNumOfLocks + " owner num of locks");
            if(owner==null)
            {
                System.out.println("owner is null");
            }
            else{
            System.out.println(owner.getName() + " current owner");}
            System.out.println("not owner");
            throw new RuntimeException();
        }
        else{
            ownerNumOfLocks--;
            if(ownerNumOfLocks == 0)
            {
                try{
                    owner=null;
                 if(lock!=null)   {lock.set(false);} else{
                     System.out.println("lock is null");
                 }

                q.pollFirst();
                }
                catch (Exception e)
                {
                    System.out.println("exception in realase");

                    throw e;
                }

            }
        }

    }

    /**

     */
    @Override
    public void close()  {
        try{
        this.release();}
        catch(Exception e){
            this.release();
            System.out.println("exception in close");
            }
    }
}
