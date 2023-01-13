
import java.util.concurrent.locks.ReentrantLock;

public interface Lock extends AutoCloseable {
    void acquire();
    boolean tryAcquire();
    void release();

    ReentrantLock raz = new ReentrantLock();
}
