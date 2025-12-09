/**
 * Bintree wrapper class.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class Bintree {
    
    private BinNode root;
    private final int worldSize = 1024;
    
    public Bintree() {
        root = EmptyNode.EMPTY;
    }
    
    /**
     * Insert an AirObject into the tree.
     * @param a The object
     */
    public void insert(AirObject a) {
        root = root.insert(a, 0, 0, 0, worldSize, worldSize, worldSize, 0);
    }
    
    /**
     * Delete an AirObject from the tree.
     * @param a The object (must have same properties as inserted, or at least pass spatial check)
     * Note: BinNode delete uses name to find in the list, but requires traversing to the correct leaf.
     * Since efficient traversal requires coordinates, we assume 'a' has correct coordinates.
     */
    public void delete(AirObject a) {
        root = root.delete(a, 0, 0, 0, worldSize, worldSize, worldSize, 0);
    }
    
    /**
     * Print the tree structure in preorder.
     * @return String representation
     */
    public String print() {
        return root.print(0, 0, 0, worldSize, worldSize, worldSize, 0) + 
               root.countNodes() + " Bintree nodes printed\n";
    }
    
    /**
     * Find intersections with a query box.
     * @param x Box x
     * @param y Box y
     * @param z Box z
     * @param xw Box width
     * @param yw Box height
     * @param zw Box depth
     * @return String result
     */
    public String intersect(int x, int y, int z, int xw, int yw, int zw) {
        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (")
          .append(x).append(", ").append(y).append(", ").append(z).append(", ")
          .append(xw).append(", ").append(yw).append(", ").append(zw).append(")\n");
          
        int visited = root.intersect(0, 0, 0, worldSize, worldSize, worldSize, 0,
                                     x, y, z, xw, yw, zw, sb);
                                     
        sb.append(visited).append(" nodes were visited in the bintree\n");
        return sb.toString();
    }
    
    /**
     * Report all collisions.
     * @return String result
     */
    public String collisions() {
        StringBuilder sb = new StringBuilder();
        sb.append("The following collisions exist in the database:\n");
        root.collisions(0, 0, 0, worldSize, worldSize, worldSize, 0, sb);
        return sb.toString();
    }
}
