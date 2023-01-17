import java.io.Closeable;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyReentrantLock implements Lock{


    private AtomicBoolean lock;
    private Thread owner;
    private int ownerNumOfLocks;
    private ArrayDeque<Thread> threadsQueue;
    private final int numOfElements = 999999;
    private final int miliSecSleepTime = 5;


    /**
     * the constructor generates an AtomicBoolean variable that represent the state of the lock.
     * if the variable is false, the state is unlocked, if true, it locked.
     * the constructor generates a thread queue that responsible for managing the synchronization between the threads,
     * the queue is unique for each lock, means threads can acquire the lock fairly, and avoiding situation of thread
     * 'starvation'.
     */
    public MyReentrantLock()
    {
        lock = new AtomicBoolean(false);
        threadsQueue = new ArrayDeque<>(numOfElements);
    }

    /**
     * This method is responsible for acquiring the lock for a thread.
     * If the lock is locked, the method waits until the lock is released in the following form:
     * if the thread is not at the head of the line the method force it to sleep in order to avoid 'busy waiting' situation
     * and reduce CPU consumption, the waiting time is till there is no owner for the lock and the current thread is in the head of the queue.
     * The second while loop is responsible for make sure that during the synchronization between the release and to acquire,
     * no matter what, the new owner is manage to change the lock state, if and only if the last owner finally released it and
     * change its state to unlocked(false). so if for some reason (unexpected thread synchronization) the state is still locked,
     * The new designated owner will 'go to sleep' in order to let the last owner finish the release action.
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
                    currentThread.sleep(miliSecSleepTime);
                }
                catch(InterruptedException e)
                {
                }
            }
            while(!lock.compareAndSet(false,true))
            {
                try{
                    currentThread.sleep(miliSecSleepTime);}
                catch(InterruptedException e)
                {
                }
            }
            owner=currentThread;
            ownerNumOfLocks=1;

        }
    }

    /**
     * tries to lock the lock, without waiting.
     * If the lock is in unlocked state(means lock variable is false) the current thread will acquire the lock and will be its new owner.
     * If the owner calls the method, its increase its number of locks by one.
     * @return true if succeeded to lock by a new owner or by the current one, otherwise false
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
     * each release action of the owner, the lock counter subtract by 1, if the counter reach 0, it means that num of lock=num of releases,
     * and the lock is actually released from the current owner, after it drops the owner from the head of line.
     * the form of counting the owner lock numbers and compare it to the number of releases is the implement of reentrant property
     * @throws IllegalReleaseAttempt exception if not the owner trys to unlock it
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
     * Overrides the close method, the lock is defined as the resource that should be auto-released.
     *So the method is responsible for automatic release of the lock at the end of
     * try-with-resource block.
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
