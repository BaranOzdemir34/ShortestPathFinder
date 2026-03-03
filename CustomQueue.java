package dataStructuresProject;

public class CustomQueue<E> {    
    // This array holds the elements of the queue
    private E[] queue;
    
    // The capacity of the queue
    private int capacity;
    
    // The index of the front and rear of the queue
    private int front;
    private int rear;
    
    // The current number of elements in the queue
    private int size;
    
    /*
     Constructor to initialize the queue with a specified capacity.
     (initialCapacity) The initial capacity of the queue.
     */
    public CustomQueue(int initialCapacity) {
        this.capacity = initialCapacity;
        this.queue = (E[]) new Object[capacity]; // Create an array to hold the queue's elements
        this.size = 0; // Number of elements currently in the queue
        this.front = 0; // Points to the front of the queue
        this.rear = -1; // Points to the end of the queue (empty at first)
    }
    
    /*
     Adds a new element to the back of the queue.
     If the queue is full, it makes more space by doubling the size.
     (Element) The new element to add to the queue.
     */
    public void enqueue(E element) {
        if (size == capacity) { // Check if the queue is full
            resize();
        }
        rear = (rear + 1) % capacity; // Move the rear to the next position
        queue[rear] = element; // Add the new element to the rear of the queue
        size++; // Increase the size of the queue
        
    }
    
    /*
     Removes and returns the front element of the queue.
     If the queue is empty, it returns null.
     Return The dequeued element, or null if the queue is empty.
     */
    public E dequeue() {
        if (isEmpty()) return null; // If no elements to dequeue, return null
        E dequeuedElement = queue[front]; // Get the element at the front
        queue[front] = null; // Clear the reference for garbage collection
        front = (front + 1) % capacity; // Circularly move the front index
        size--; // Reduce the size of the queue
        return dequeuedElement; // Return the dequeued element
    }
    
    /*
     Checks if the queue is empty.
     Return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0; // If size is 0, then the queue is empty
    }
    
    /*
     * Doubles the size of the queue when it becomes full.
     */
    private void resize() {
        E[] newQueue = (E[]) new Object[capacity * 2]; // Create a new array with double capacity
        for (int i = 0; i < size; i++) { // Copy all elements to the new array
            newQueue[i] = queue[(front + i) % capacity];
        }
        queue = newQueue; // Update the queue reference to the new array
        front = 0; // Reset front to the start of the array
        rear = size - 1; // Set rear to the last valid index
        capacity *= 2; // Double the capacity
    }
} 