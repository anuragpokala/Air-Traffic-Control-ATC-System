/**
 * Leaf Node implementation.
 * Holds a list of AirObjects.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class LeafNode implements BinNode {
    
    private class LNode {
        AirObject data;
        LNode next;
        LNode(AirObject d, LNode n) { data = d; next = n; }
    }

    private LNode head;
    private int size;
    private static final int MAX_LEVEL = 30; // 1024 = 2^10, 3 dims => ~30 splits

    public LeafNode() {
        head = null;
        size = 0;
    }
    
    /**
     * Transfer all objects from this node to another LeafNode.
     * @param target The target LeafNode
     */
    public void transferTo(LeafNode target) {
        LNode curr = head;
        while (curr != null) {
            // We can just define params as 0, 0... since target.insert sort logic ignores spatial except for splitting check?
            // Wait, LeafNode.insert does check splitting. 
            // In merge, we know the result fits (count <= 1).
            // So splitting won't happen.
            // But we need to pass valid params to satisfy signature?
            // Actually LeafNode.insert ignores x,y,z unless it splits.
            // If it won't split, we can pass 0s.
            target.insert(curr.data, 0, 0, 0, 0, 0, 0, 0); 
            curr = curr.next;
        }
    }

    @Override
    public BinNode insert(AirObject a, int x, int y, int z, int w, int h, int d, int level) {
        // Insert into sorted linked list
        if (head == null || a.compareTo(head.data) < 0) {
            head = new LNode(a, head);
        } else {
            LNode curr = head;
            while (curr.next != null && a.compareTo(curr.next.data) >= 0) {
                curr = curr.next;
            }
            curr.next = new LNode(a, curr.next);
        }
        size++;

        // Check splitting condition
        // If size > 1 (meaning we added a second one), and not at max level
        if (size > 1 && level < MAX_LEVEL) {
             // Split!
             // Create internal node
             InternalNode newNode = new InternalNode();
             
             // Re-insert all objects
             LNode curr = head;
             while (curr != null) {
                 newNode.insert(curr.data, x, y, z, w, h, d, level);
                 curr = curr.next;
             }
             return newNode;
        }

        return this;
    }

    @Override
    public BinNode delete(AirObject a, int x, int y, int z, int w, int h, int d, int level) {
        if (head == null) return this;
        
        if (head.data.getName().equals(a.getName())) {
            head = head.next;
            size--;
        } else {
            LNode curr = head;
            while (curr.next != null && !curr.next.data.getName().equals(a.getName())) {
                curr = curr.next;
            }
            if (curr.next != null) {
                curr.next = curr.next.next;
                size--;
            }
        }
        
        if (size == 0) {
            return EmptyNode.EMPTY;
        }
        return this;
    }

    @Override
    public int count() {
        return size;
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
        sb.append("L (").append(x).append(", ").append(y).append(", ").append(z)
          .append(", ").append(w).append(", ").append(h).append(", ").append(d)
          .append(") ").append(size).append("\n");
        
        LNode curr = head;
        while (curr != null) {
            for (int i = 0; i < level; i++) {
                sb.append("  ");
            }
            sb.append(curr.data.toString()).append("\n");
            curr = curr.next;
        }
        return sb.toString();
    }

    @Override
    public int intersect(int x, int y, int z, int w, int h, int d, int level, 
                          int qx, int qy, int qz, int qw, int qh, int qd, StringBuilder sb) {
        // Check if this node bounds intersect query (Assuming caller checked? Or we check here?)
        // Standard recursion: parent checks bounds of children before call. 
        // But for root, or general safety, we can check.
        // However, standard visitor: "If it does intersect an internal node, we visit... If it is a leaf node, then we ask..."
        // So we are visited.
        
        int visited = 1;
        // Check intersection of objects
        LNode curr = head;
        while (curr != null) {
            // Check if object intersects query box
            if (boxIntersect(curr.data.getXorig(), curr.data.getYorig(), curr.data.getZorig(),
                             curr.data.getXwidth(), curr.data.getYwidth(), curr.data.getZwidth(),
                             qx, qy, qz, qw, qh, qd)) {
                 sb.append(curr.data.toString()).append("\n");
            }
            curr = curr.next;
        }
        return visited;
    }

    private boolean boxIntersect(int x1, int y1, int z1, int w1, int h1, int d1,
                                 int x2, int y2, int z2, int w2, int h2, int d2) {
        return (x1 < x2 + w2 && x1 + w1 > x2 &&
                y1 < y2 + h2 && y1 + h1 > y2 &&
                z1 < z2 + d2 && z1 + d1 > z2);
    }
    
    // Check if box 1 contains corner of box 2? No, standard intersection.
    // "A collision between two boxes are only reported once: With the node that contains the origin of the intersection box." -> This is for collision detection between objects.
    // For intersect query, we return all objects that intersect the query box.

    @Override
    public void collisions(int x, int y, int z, int w, int h, int d, int level, StringBuilder sb) {
        // Double loop - check all pairs of objects
        LNode i = head;
        while (i != null) {
            LNode j = i.next;
            while (j != null) {
                if (objIntersect(i.data, j.data)) {
                    // Calculate intersection box origin
                    int intX = Math.max(i.data.getXorig(), j.data.getXorig());
                    int intY = Math.max(i.data.getYorig(), j.data.getYorig());
                    int intZ = Math.max(i.data.getZorig(), j.data.getZorig());
                    
                    // Only report if intersection origin is within this leaf's bounds
                    if (intX >= x && intX < x + w &&
                        intY >= y && intY < y + h &&
                        intZ >= z && intZ < z + d) {
                        sb.append(i.data.getName()).append(" and ").append(j.data.getName()).append(" collision\n");
                    }
                }
                j = j.next;
            }
            i = i.next;
        }
    }
    
    private boolean objIntersect(AirObject a, AirObject b) {
        return boxIntersect(a.getXorig(), a.getYorig(), a.getZorig(), a.getXwidth(), a.getYwidth(), a.getZwidth(),
                            b.getXorig(), b.getYorig(), b.getZorig(), b.getXwidth(), b.getYwidth(), b.getZwidth());
    }
}
