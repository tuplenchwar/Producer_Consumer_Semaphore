import java.util.concurrent.Semaphore;

/**
 * This is a producer class which is using runnable interface to create producer thread.
 **/
public class Producer implements Runnable {
    private CircularQueue buffer;
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;
    private int maxInt;


    public Producer(CircularQueue buffer, int maxInt, Semaphore mutex, Semaphore empty, Semaphore full) {
        this.buffer = buffer;
        this.mutex = mutex;
        this.empty = empty;
        this.full = full;
        this.maxInt = maxInt;
    }

    @Override
    public void run() {
        for (int i = 1; i <= maxInt ; i++) {
            try {
                empty.acquire();
                mutex.acquire();
                System.out.println("Produced:"+i);
                buffer.enqueue(i);
                mutex.release();
                full.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
