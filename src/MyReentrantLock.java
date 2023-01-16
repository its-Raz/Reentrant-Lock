import java.io.Closeable;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyReentrantLock implements Lock{

    public AtomicBoolean lock;
    public Thread owner;
    public int ownerNumOfLocks;
    public ArrayDeque<Thread> threadsQueue;

    /**
     * constructor
     */
    public MyReentrantLock()
    {
        lock = new AtomicBoolean(false);
        threadsQueue = new ArrayDeque<>(999999);
    }

    /**
     *locks the lock. if the lock is locked, the method waits until the lock is released
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
            threadsQueue.add(currentThread);
            while (owner!=null && threadsQueue.peek()!=currentThread)
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
                    currentThread.sleep(5);}
                catch(Exception e)
                {
                }
            }
            owner=currentThread;
            ownerNumOfLocks=1;

        }
    }

    /**
     * tries to lock the lock
     * @return true if succeeded to lock, otherwise false
     */
    @Override
    public boolean tryAcquire() {
        Thread currentThread = Thread.currentThread();
        if(lock.compareAndSet(false,true))
        {
            owner=currentThread;
            ownerNumOfLocks=1;
            return true;
        } else if (currentThread==owner)
        {
            ownerNumOfLocks++;
            return true;
        }
        return false;
    }

    /**
     *releases the lock
     * @throws IllegalReleaseAttempt exception
     */
    @Override
    public void release() {

        if(Thread.currentThread() != owner)
        {
            throw new IllegalReleaseAttempt();
        }
        else{
            ownerNumOfLocks--;
            if(ownerNumOfLocks == 0)
            {
                    owner=null;
                    lock.set(false);
                    threadsQueue.pollFirst();
            }
        }

    }

    /**
     *automatic release of the lock
     */
    @Override
    public void close()  {
        try{
            this.release();}
        catch(Exception e){
            this.release();
        }
    }
}
