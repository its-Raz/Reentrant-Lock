import java.io.Closeable;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyReentrantLock implements Lock
{
    private AtomicBoolean lock; //if false it can be locked, if true its locked
    private ArrayDeque<Thread> threadQueue;
    private Thread owner;
    private int ownerNumOfLocks;
    /**
     * Constructs a new object.
     */
    public MyReentrantLock()
    {
        this.lock = new AtomicBoolean(false);
        threadQueue = new ArrayDeque<>(9999);
    }

    /**
     *
     */
    @Override
    public void acquire()
    {
        Thread currentThread = Thread.currentThread();
        if(currentThread == owner)
        {
            ownerNumOfLocks++;
            return;
        }

            threadQueue.add(currentThread);
            while (owner!=null && threadQueue.peek()!=currentThread)
            {
                try {
                    currentThread.sleep(5);
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
                }
            }
            ownerNumOfLocks=1;
            owner=currentThread;

    }




    /**
     * @return
     */
    @Override
    public boolean tryAcquire()
    {
        Thread currentThread = Thread.currentThread();
        if(lock.compareAndSet(false,true))
        {
            ownerNumOfLocks=1;
            owner = currentThread;
            return true;
        } else if (owner == currentThread) {
            ownerNumOfLocks++;
            return true;
        }
        return false;
    }

    /**
     * Release the lock if number of owner locks equal to number of releases attempts.
     * @throws
     */
    @Override
    public void release()
    {
        Thread currentThread = Thread.currentThread();
        if(owner!=currentThread) //if the lock is released the owner is null
        {
            throw new IllegalReleaseAttempt();
        }
        else {
            ownerNumOfLocks--;
            if (ownerNumOfLocks == 0) {
                owner = null;
                lock.set(false);
                threadQueue.pollFirst();
            }

            }
        }


    /**

     *
     */
    @Override
    public void close()  {
        try
        {
            this.release();
        }
        catch(Exception e)
        {
            this.release();
        }

    }
}
