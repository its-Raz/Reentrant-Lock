import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyReentrantLock implements Lock{

    public AtomicBoolean lock;
    public Thread owner;
    int ownerNumOfLocks;


    public MyReentrantLock()
    {
        lock = new AtomicBoolean(false);
    }


    /**
     *
     */
    @Override
    public void acquire() {

        while(true)
        {
                if(Thread.currentThread() == owner || lock.compareAndSet(false,true))
                {
                    try
                    {
                    owner = Thread.currentThread();
                    ownerNumOfLocks++;
                    return;
                    }
                    catch (Exception e)
                    {
                        owner=null;
                        lock.set(false);
                        return;
                    }
                }
                }

        }



    /**
     * @return
     */
    @Override
    public boolean tryAcquire() {
        if(Thread.currentThread() == owner || lock.compareAndSet(false,true))
        {
            try
            {
                owner = Thread.currentThread();
                ownerNumOfLocks++;
                return true;
            }
            catch (Exception e)
            {
                owner=null;
                lock.set(false);
            }
        }
        return false;

    }

    /**
     *
     */
    @Override
    public void release() {
        Thread current = Thread.currentThread();
        if(lock.get()==false || current != owner)
        {
//            throw new IllegalReleaseAttempt();
            System.out.println("exception");
        }
        else{
            ownerNumOfLocks--;
            if(ownerNumOfLocks == 0)
            {
                lock.set(false);
                owner=null;
            }
        }

    }

    /**

     */
    @Override
    public void close()  {
        this.release();
    }
}
