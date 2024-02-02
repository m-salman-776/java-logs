package Common;



public class DynamicCircularQueue {
    private int[] queue;
    private int front, rear, maxSize, currentSize;

    public DynamicCircularQueue(int initialSize) {
        this.maxSize = initialSize;
        this.queue = new int[maxSize];
        this.front = this.rear = this.currentSize = 0;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean isFull() {
        return currentSize == maxSize;
    }

    public void enqueue(int item) {
        if (isFull()) {
            resize();
        }
        queue[rear] = item;
        rear = (rear + 1) % maxSize;
        currentSize++;
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        int item = queue[front];
        front = (front + 1) % maxSize;
        currentSize--;
        return item;
    }

    private void resize() {
        int newMaxSize = 2 * maxSize; // Double the size in this example
        int[] newQueue = new int[newMaxSize];
        for (int i = 0; i < currentSize; i++) {
            newQueue[i] = queue[(front + i) % maxSize];
        }
        front = 0;
        rear = currentSize;
        maxSize = newMaxSize;
        queue = newQueue;
    }
    private void shrink() {
        int newMaxSize = maxSize / 2; // Halve the size in this example
        resizeHelper(newMaxSize);
    }

    private void resizeHelper(int newMaxSize) {
        int[] newQueue = new int[newMaxSize];
        for (int i = 0; i < currentSize; i++) {
            newQueue[i] = queue[(front + i) % maxSize];
        }
        front = 0;
        rear = currentSize;
        maxSize = newMaxSize;
        queue = newQueue;
    }

    public static void main(String[] args) {
        DynamicCircularQueue circularQueue = new DynamicCircularQueue(3);

        circularQueue.enqueue(1);
        circularQueue.enqueue(2);
        circularQueue.enqueue(3);

        System.out.println("Dequeuing: " + circularQueue.dequeue());
        System.out.println("Dequeuing: " + circularQueue.dequeue());

        circularQueue.enqueue(4);

        while (!circularQueue.isEmpty()) {
            System.out.println("Dequeuing: " + circularQueue.dequeue());
        }
    }
}
