/**
 * Flyweight Empty Node.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class EmptyNode implements BinNode {
    
    public static final EmptyNode EMPTY = new EmptyNode();

    private EmptyNode() {}

    @Override
    public BinNode insert(AirObject a, int x, int y, int z, int w, int h, int d, int level) {
        LeafNode node = new LeafNode();
        return node.insert(a, x, y, z, w, h, d, level);
    }

    @Override
    public BinNode delete(AirObject a, int x, int y, int z, int w, int h, int d, int level) {
        return this;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int countNodes() {
        return 1;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public String print(int x, int y, int z, int w, int h, int d, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append("E (").append(x).append(", ").append(y).append(", ").append(z)
          .append(", ").append(w).append(", ").append(h).append(", ").append(d)
          .append(") ").append(count()).append("\n");
        return sb.toString();
    }

    @Override
    public int intersect(int x, int y, int z, int w, int h, int d, int level, 
                         int qx, int qy, int qz, int qw, int qh, int qd, StringBuilder sb) {
        return 1;
    }

    @Override
    public void collisions(int x, int y, int z, int w, int h, int d, int level, StringBuilder sb) {
        // No collisions in empty node
    }
}
