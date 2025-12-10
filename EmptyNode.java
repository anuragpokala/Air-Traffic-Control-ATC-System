/**
 * Represents an empty node in the Bintree.
 * Flyweight pattern (Singleton).
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class EmptyNode implements BinNode {

    public static final EmptyNode THE_NODE = new EmptyNode();

    private EmptyNode() {
    }

    @Override
    public BinNode insert(AirObject obj, SpatialBox box, int level) {
        return new LeafNode(obj);
    }

    @Override
    public BinNode remove(String name, SpatialBox box, RemovalContainer held, int level) {
        return this; 
    }

    @Override
    public void print(StringBuilder sb, SpatialBox box, int level, IntWrapper count) {
        sb.append(getIndent(level)).append("E ").append(box.toString())
          .append(" ").append(level).append("\r\n");
        count.increment();
    }

    @Override
    public void findCollisions(StringBuilder sb, SpatialBox box, int level) {
    }

    @Override
    public void findIntersections(SpatialBox query, SpatialBox box, StringBuilder sb, IntWrapper visits, int level) {
        visits.increment();
    }

    private String getIndent(int level) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < level * 2; i++) {
            s.append(" ");
        }
        return s.toString();
    }
}
