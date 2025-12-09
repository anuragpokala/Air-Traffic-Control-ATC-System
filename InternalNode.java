/**
 * Internal Node implementation.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class InternalNode implements BinNode {
    
    private BinNode left;
    private BinNode right;

    public InternalNode() {
        left = EmptyNode.EMPTY;
        right = EmptyNode.EMPTY;
    }

    @Override
    public BinNode insert(AirObject a, int x, int y, int z, int w, int h, int d, int level) {
        int dim = level % 3;
        int mid;
        
        if (dim == 0) { // x split
            mid = x + w / 2;
            if (a.getXorig() < mid) {
                left = left.insert(a, x, y, z, w / 2, h, d, level + 1);
            } else {
                right = right.insert(a, mid, y, z, w / 2, h, d, level + 1);
            }
        } else if (dim == 1) { // y split
            mid = y + h / 2;
            if (a.getYorig() < mid) {
                left = left.insert(a, x, y, z, w, h / 2, d, level + 1);
            } else {
                right = right.insert(a, x, mid, z, w, h / 2, d, level + 1);
            }
        } else { // z split
            mid = z + d / 2;
            if (a.getZorig() < mid) {
                left = left.insert(a, x, y, z, w, h, d / 2, level + 1);
            } else {
                right = right.insert(a, x, y, mid, w, h, d / 2, level + 1);
            }
        }
        
        return this;
    }

    @Override
    public BinNode delete(AirObject a, int x, int y, int z, int w, int h, int d, int level) {
        int dim = level % 3;
        int mid;
        
        if (dim == 0) { // x
            mid = x + w / 2;
            if (a.getXorig() < mid) {
                left = left.delete(a, x, y, z, w / 2, h, d, level + 1);
            } else {
                right = right.delete(a, mid, y, z, w / 2, h, d, level + 1);
            }
        } else if (dim == 1) { // y
            mid = y + h / 2;
            if (a.getYorig() < mid) {
                left = left.delete(a, x, y, z, w, h / 2, d, level + 1);
            } else {
                right = right.delete(a, x, mid, z, w, h / 2, d, level + 1);
            }
        } else { // z
            mid = z + d / 2;
            if (a.getZorig() < mid) {
                left = left.delete(a, x, y, z, w, h, d / 2, level + 1);
            } else {
                right = right.delete(a, x, y, mid, w, h, d / 2, level + 1);
            }
        }
        
        // Merge check
        if (left.isLeaf() && right.isLeaf()) {
            if (left.count() + right.count() <= 1) {
                LeafNode newLeaf = new LeafNode();
                mergeInto(newLeaf, left);
                mergeInto(newLeaf, right);
                return newLeaf;
            }
        }
        
        return this;
    }
    
    private void mergeInto(LeafNode target, BinNode source) {
        if (source instanceof LeafNode) {
            ((LeafNode) source).transferTo(target);
        }
    }

    @Override
    public int count() {
        return left.count() + right.count();
    }

    @Override
    public int countNodes() {
        return 1 + left.countNodes() + right.countNodes();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public String print(int x, int y, int z, int w, int h, int d, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append("I (").append(x).append(", ").append(y).append(", ").append(z)
          .append(", ").append(w).append(", ").append(h).append(", ").append(d)
          .append(") ").append(count()).append("\n");
        
        int dim = level % 3;
        // Left
        sb.append(left.print(getChildX(0, dim, x, w), getChildY(0, dim, y, h), getChildZ(0, dim, z, d),
                             getChildW(0, dim, w),    getChildH(0, dim, h),    getChildD(0, dim, d),
                             level + 1));
        // Right
        sb.append(right.print(getChildX(1, dim, x, w), getChildY(1, dim, y, h), getChildZ(1, dim, z, d),
                              getChildW(1, dim, w),    getChildH(1, dim, h),    getChildD(1, dim, d),
                              level + 1));
        return sb.toString();
    }

    @Override
    public int intersect(int x, int y, int z, int w, int h, int d, int level,
                          int qx, int qy, int qz, int qw, int qh, int qd, StringBuilder sb) {
        int visited = 1; // Visit self
        int dim = level % 3;
        
        // Left child
        int lx = getChildX(0, dim, x, w);
        int ly = getChildY(0, dim, y, h);
        int lz = getChildZ(0, dim, z, d);
        int lw = getChildW(0, dim, w);
        int lh = getChildH(0, dim, h);
        int ld = getChildD(0, dim, d);
        
        if (boxIntersect(lx, ly, lz, lw, lh, ld, qx, qy, qz, qw, qh, qd)) {
            visited += left.intersect(lx, ly, lz, lw, lh, ld, level + 1, qx, qy, qz, qw, qh, qd, sb);
        }
        
        // Right child
        int rx = getChildX(1, dim, x, w);
        int ry = getChildY(1, dim, y, h);
        int rz = getChildZ(1, dim, z, d);
        int rw = getChildW(1, dim, w);
        int rh = getChildH(1, dim, h);
        int rd = getChildD(1, dim, d);
        
        if (boxIntersect(rx, ry, rz, rw, rh, rd, qx, qy, qz, qw, qh, qd)) {
            visited += right.intersect(rx, ry, rz, rw, rh, rd, level + 1, qx, qy, qz, qw, qh, qd, sb);
        }
        
        return visited;
    }

    @Override
    public void collisions(int x, int y, int z, int w, int h, int d, int level, StringBuilder sb) {
        int dim = level % 3;
        // Left
        left.collisions(getChildX(0, dim, x, w), getChildY(0, dim, y, h), getChildZ(0, dim, z, d),
                        getChildW(0, dim, w),    getChildH(0, dim, h),    getChildD(0, dim, d),
                        level + 1, sb);
        // Right
        right.collisions(getChildX(1, dim, x, w), getChildY(1, dim, y, h), getChildZ(1, dim, z, d),
                         getChildW(1, dim, w),    getChildH(1, dim, h),    getChildD(1, dim, d),
                         level + 1, sb);
    }

    private boolean boxIntersect(int x1, int y1, int z1, int w1, int h1, int d1,
                                 int x2, int y2, int z2, int w2, int h2, int d2) {
        return (x1 < x2 + w2 && x1 + w1 > x2 &&
                y1 < y2 + h2 && y1 + h1 > y2 &&
                z1 < z2 + d2 && z1 + d1 > z2);
    }

    // Helpers
    private int getChildX(int side, int dim, int x, int w) {
        if (dim == 0 && side == 1) return x + w / 2;
        return x;
    }
    private int getChildY(int side, int dim, int y, int h) {
        if (dim == 1 && side == 1) return y + h / 2;
        return y;
    }
    private int getChildZ(int side, int dim, int z, int d) {
        if (dim == 2 && side == 1) return z + d / 2;
        return z;
    }
    private int getChildW(int side, int dim, int w) {
        if (dim == 0) return w / 2;
        return w;
    }
    private int getChildH(int side, int dim, int h) {
        if (dim == 1) return h / 2;
        return h;
    }
    private int getChildD(int side, int dim, int d) {
        if (dim == 2) return d / 2;
        return d;
    }
}
