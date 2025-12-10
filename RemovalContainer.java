/**
 * Container to hold an object that was removed from a structure.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class RemovalContainer {
    private AirObject removedObject;

    /**
     * Create an empty container.
     */
    public RemovalContainer() {
        this.removedObject = null;
    }

    /**
     * Store the removed object.
     * @param obj The object
     */
    public void set(AirObject obj) {
        this.removedObject = obj;
    }

    /**
     * Retrieve the removed object.
     * @return The object, or null
     */
    public AirObject get() {
        return removedObject;
    }
}
