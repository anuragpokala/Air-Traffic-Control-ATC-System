/**
 * Interface for Bintree nodes.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public interface BinNode {
    
    /**
     * Insert an AirObject into the node.
     * @param a The AirObject
     * @param x Node x origin
     * @param y Node y origin
     * @param z Node z origin
     * @param w Node width
     * @param h Node height
     * @param d Node depth
     * @param level Current level
     * @return The updated node structure
     */
    BinNode insert(AirObject a, int x, int y, int z, int w, int h, int d, int level);

    /**
     * Delete an AirObject from the node.
     * @param a The AirObject
     * @param x Node x origin
     * @param y Node y origin
     * @param z Node z origin
     * @param w Node width
     * @param h Node height
     * @param d Node depth
     * @param level Current level
     * @return The updated node structure
     */
    BinNode delete(AirObject a, int x, int y, int z, int w, int h, int d, int level);

    /**
     * Get the number of objects in this node's subtree.
     * @return The count
     */
    int count();
    
    /**
     * Get the number of nodes in this subtree.
     * @return The node count
     */
    int countNodes();

    /**
     * Check if this is a leaf node.
     * @return True if leaf (Empty or Full leaf), False if Internal.
     */
    boolean isLeaf();

    /**
     * Print the node structure.
     * @param x Node x origin
     * @param y Node y origin
     * @param z Node z origin
     * @param w Node width
     * @param h Node height
     * @param d Node depth
     * @param level Current level
     * @return String representation
     */
    String print(int x, int y, int z, int w, int h, int d, int level);
    
    /**
     * Check for intersections with a query box.
     * @param x Node x origin
     * @param y Node y origin
     * @param z Node z origin
     * @param w Node width
     * @param h Node height
     * @param d Node depth
     * @param level Current level
     * @param qx Query box x
     * @param qy Query box y
     * @param qz Query box z
     * @param qw Query box width
     * @param qh Query box height
     * @param qd Query box depth
     * @param sb StringBuilder to append results
     * @return Number of nodes visited
     */
    int intersect(int x, int y, int z, int w, int h, int d, int level,
                   int qx, int qy, int qz, int qw, int qh, int qd, StringBuilder sb);
                   
    /**
     * Report all collisions within this node's subtree.
     * @param x Node x origin
     * @param y Node y origin
     * @param z Node z origin
     * @param w Node width
     * @param h Node height
     * @param d Node depth
     * @param level Current level
     * @param sb StringBuilder to append results
     */
    void collisions(int x, int y, int z, int w, int h, int d, int level, StringBuilder sb);
}
