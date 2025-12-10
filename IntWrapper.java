/**
 * Simple wrapper for a mutable integer value.
 * Used to pass counters by reference in recursive calls.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class IntWrapper {
    private int value;

    /**
     * Create a new wrapper with value 0.
     */
    public IntWrapper() {
        this.value = 0;
    }

    /**
     * Increment the value.
     */
    public void increment() {
        value++;
    }

    /**
     * Get the current value.
     * @return current int value
     */
    public int getValue() {
        return value;
    }
}

