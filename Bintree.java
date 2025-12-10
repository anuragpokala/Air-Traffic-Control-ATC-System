/**
 * The Bintree structure for spatial indexing.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class Bintree {
    private SpatialBox worldBox;
    private BinNode root;

    /**
     * Initialize Bintree with a given size.
     * @param size World cubic size
     */
    public Bintree(int size) {
        worldBox = new SpatialBox(0, 0, 0, size, size, size);
        root = EmptyNode.THE_NODE;
    }

    /**
     * Reset the tree.
     */
    public void clear() {
        root = EmptyNode.THE_NODE;
    }

    /**
     * Insert an object.
     * @param funcObject The object
     */
    public void insert(AirObject funcObject) {
        root = root.insert(funcObject, worldBox, 0);
    }

    /**
     * Remove an object by name.
     * @param name The name
     * @return The removed object or null
     */
    public AirObject remove(String name) {
        RemovalContainer holder = new RemovalContainer();
        root = root.remove(name, worldBox, holder, 0);
        return holder.get();
    }

    /**
     * Generate print string.
     * @return The string
     */
    public String print() {
        StringBuilder b = new StringBuilder();
        IntWrapper cnt = new IntWrapper();
        root.print(b, worldBox, 0, cnt);
        b.append(cnt.getValue()).append(" Bintree nodes printed\r\n");
        return b.toString();
    }

    /**
     * Check collisions.
     * @return Collision report
     */
    public String collisions() {
        StringBuilder b = new StringBuilder();
        b.append("The following collisions exist in the database:\n");
        root.findCollisions(b, worldBox, 0);
        return b.toString();
    }

    /**
     * Find intersections with a box.
     * @param x X
     * @param y Y
     * @param z Z
     * @param w Width
     * @param h Height
     * @param d Depth
     * @return Intersection report
     */
    public String intersect(int x, int y, int z, int w, int h, int d) {
        SpatialBox query = new SpatialBox(x, y, z, w, h, d);
        StringBuilder b = new StringBuilder();
        b.append("The following objects intersect ").append(query.toString()).append("\n");
        
        StringBuilder content = new StringBuilder();
        IntWrapper visits = new IntWrapper();
        
        root.findIntersections(query, worldBox, content, visits, 0);
        
        b.append(content);
        b.append(visits.getValue()).append(" nodes were visited in the bintree\n");
        return b.toString();
    }
}
