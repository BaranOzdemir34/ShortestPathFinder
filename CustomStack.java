package dataStructuresProject;

public class CustomStack<E> {
    
    private E[] CustomStack;   // This array will hold the elements of our stack
    private int size;          // This keeps track of how many elements are in the stack right now
    private int maxCapacity;   // The maximum number of elements our stack can hold

    // Constructor to create a stack with a specific capacity
    public CustomStack(int maxCapacity) {
        this.maxCapacity = maxCapacity;  // Set the max number of elements the stack can hold
        size = 0;  // Start with an empty stack
        CustomStack = (E[]) new Object[maxCapacity];  // Create the array to hold the elements
    }
    
    // Default constructor to create a stack with a default capacity of 10
    public CustomStack() {
        maxCapacity = 10;  // Default to a stack that can hold 10 elements
        size = 0;  // Start with an empty stack
        CustomStack = (E[]) new Object[maxCapacity];  // Create the array to hold the elements
    }

    // Push a new element onto the stack
    public void push(E element) {
        // Check if the stack is full and needs more space
        if (size == maxCapacity) {
            int newCapacity = maxCapacity * 2;  // Double the size of the stack
            E[] newStack = (E[]) new Object[newCapacity];  // Create a new bigger array

            // Copy the old stack's elements to the new stack
            for (int i = 0; i < size; i++) {
                newStack[i] = CustomStack[i];  // Copy each element over
            }

            CustomStack = newStack;  // Update our stack to be the new larger stack
            maxCapacity = newCapacity;  // Update the capacity to reflect the new size
        }

        // Add the new element to the top of the stack
        CustomStack[size++] = element;
    }

    // Pop an element off the stack (remove it)
    public E pop() {
        if (isEmpty()) {
            return null;  // Return null because there's nothing to pop
        }

        // Get the element at the top of the stack and remove it
        E element = CustomStack[--size];  // Decrease the size and get the top element
        CustomStack[size] = null;  // Clean up the old element

        return element;  // Return the popped element
    }

    // Peek at the top element of the stack without removing it
    public E peek() {
         if (isEmpty()) {
            return null;  // Return null because there's nothing at the top
        }

        // Return the top element without removing it
        return CustomStack[size - 1];
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return size == 0;  // If the size is 0, the stack is empty
    }

    // Get the current size of the stack (how many elements are in it)
    public int size() {
        return size;  // Return the number of elements currently in the stack
    }
}