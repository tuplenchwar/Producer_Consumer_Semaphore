import java.util.concurrent.Semaphore;

/**
 * This is a consumer class which is using runnable interface to create multiple consumers.
 **/
class Consumer implements Runnable {
    volatile public int consumedSoFar;
    private final CircularQueue buffer;
    private final Semaphore mutex;
    private final Semaphore empty;
    private final Semaphore full;
    private final int maxInt;


    public Consumer(CircularQueue buffer, Semaphore mutex, Semaphore empty, Semaphore full, int maxInt) {
        this.buffer = buffer;
        this.mutex = mutex;
        this.empty = empty;
        this.full = full;
        this.maxInt = maxInt;
    }

    public void run() {
        while (true) {
            try {

                full.acquire();
                mutex.acquire();
                consumedSoFar++;

                if (consumedSoFar <= maxInt){
                    System.out.println(Thread.currentThread().getName() + " consumed: " + buffer.dequeue());
                }

                if(consumedSoFar >= maxInt){
                    mutex.release();
                    full.release();
                    System.out.println(Thread.currentThread().getName() + " exiting ");
                    return;
                }
                mutex.release();
                empty.release();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
