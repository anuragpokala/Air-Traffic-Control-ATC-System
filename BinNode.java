/**
 * Interface for Bintree nodes.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public interface BinNode {

    /**
     * Insert an object into the tree.
     * @param obj The object
     * @param box The spatial bounds
     * @param level The current level
     * @return The updated node
     */
    BinNode insert(AirObject obj, SpatialBox box, int level);

    /**
     * Remove an object from the tree.
     * @param name Name of object to remove
     * @param box Spatial bounds
     * @param held Container for removed object
     * @param level Current level
     * @return Updated node
     */
    BinNode remove(String name, SpatialBox box, RemovalContainer held, int level);

    /**
     * Print the tree structure.
     * @param sb StringBuilder for output
     * @param box Spatial bounds
     * @param level Current level
     * @param count Counter for nodes
     */
    void print(StringBuilder sb, SpatialBox box, int level, IntWrapper count);

    /**
     * Report collisions.
     * @param sb Output builder
     * @param box Spatial bounds
     * @param level Current level
     */
    void findCollisions(StringBuilder sb, SpatialBox box, int level);

    /**
     * Report intersections with a query box.
     * @param query Query box
     * @param box Current node box
     * @param sb Output builder
     * @param visits Counter for visited nodes
     * @param level Current level
     */
    void findIntersections(SpatialBox query, SpatialBox box, StringBuilder sb, IntWrapper visits, int level);
}
