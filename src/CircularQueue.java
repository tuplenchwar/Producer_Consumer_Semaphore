public class CircularQueue {
    private int[] buffer;
    private int size;
    private int front;
    private int rear;

    public CircularQueue(int size) {
        this.size = size;
        buffer = new int[size];
        front = 0;
        rear = 0;
    }

    public void enqueue(int item) {
        buffer[rear] = item;
        rear = (rear + 1) % size;
    }

    public int dequeue() {
        int item = buffer[front];
        front = (front + 1) % size;
        return item;
    }



    public boolean isEmpty() {
        return front == rear;
    }
}
