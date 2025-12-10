/**
 * Internal Node of the Bintree.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class InternalNode implements BinNode {

    private BinNode left;
    private BinNode right;

    /**
     * Create new Internal Node with empty children.
     */
    public InternalNode() {
        left = EmptyNode.THE_NODE;
        right = EmptyNode.THE_NODE;
    }

    private boolean overlaps(int s1, int e1, int s2, int e2) {
        return s1 < e2 && s2 < e1;
    }

    private boolean boxIntersectsObj(SpatialBox box, AirObject obj) {
        return overlaps(box.getX(), box.getX() + box.getXDimension(), obj.getXorig(), obj.getXorig() + obj.getXwidth()) &&
               overlaps(box.getY(), box.getY() + box.getYDimension(), obj.getYorig(), obj.getYorig() + obj.getYwidth()) &&
               overlaps(box.getZ(), box.getZ() + box.getZDimension(), obj.getZorig(), obj.getZorig() + obj.getZwidth());
    }

    @Override
    public BinNode insert(AirObject obj, SpatialBox box, int level) {
        int axis = level % 3;
        SpatialBox b1 = box.getFirstHalf(axis);
        SpatialBox b2 = box.getSecondHalf(axis);

        if (boxIntersectsObj(b1, obj)) {
            left = left.insert(obj, b1, level + 1);
        }
        if (boxIntersectsObj(b2, obj)) {
            right = right.insert(obj, b2, level + 1);
        }
        return this;
    }

    @Override
    public BinNode remove(String name, SpatialBox box, RemovalContainer held, int level) {
        int axis = level % 3;
        SpatialBox b1 = box.getFirstHalf(axis);
        SpatialBox b2 = box.getSecondHalf(axis);

        left = left.remove(name, b1, held, level + 1);
        right = right.remove(name, b2, held, level + 1);



        if (shouldMerge()) {
            return performMerge();
        }
        return this;
    }

    private boolean isLeafOrEmpty(BinNode node) {
        return node == EmptyNode.THE_NODE || node instanceof LeafNode;
    }

    private boolean shouldMerge() {
        if (!isLeafOrEmpty(left) || !isLeafOrEmpty(right)) return false;

        AirObject[] all = getAllUnique();
        int count = 0;
        for (AirObject a : all) {
            if (a != null) count++;
        }

        if (count <= 3) return true;
        

        if (count == 0) return true;
        int minX = all[0].getXorig();
        int maxX = minX + all[0].getXwidth();
        int minY = all[0].getYorig();
        int maxY = minY + all[0].getYwidth();
        int minZ = all[0].getZorig();
        int maxZ = minZ + all[0].getZwidth();

        for (int i = 1; i < count; i++) {
            AirObject obj = all[i];
            minX = Math.max(minX, obj.getXorig());
            maxX = Math.min(maxX, obj.getXorig() + obj.getXwidth());
            minY = Math.max(minY, obj.getYorig());
            maxY = Math.min(maxY, obj.getYorig() + obj.getYwidth());
            minZ = Math.max(minZ, obj.getZorig());
            maxZ = Math.min(maxZ, obj.getZorig() + obj.getZwidth());

            if (minX >= maxX || minY >= maxY || minZ >= maxZ) {
                return false;
            }
        }
        return true;
    }

    private AirObject[] getAllUnique() {
        int cap = 0;
        if (left instanceof LeafNode) cap += ((LeafNode) left).getItemCount();
        if (right instanceof LeafNode) cap += ((LeafNode) right).getItemCount();
        
        AirObject[] arr = new AirObject[cap];
        int idx = 0;

        if (left instanceof LeafNode) {
            LeafNode l = (LeafNode) left;
            for (int i = 0; i < l.getItemCount(); i++) {
                if (!contains(arr, idx, l.getArray()[i].getName())) {
                    arr[idx++] = l.getArray()[i];
                }
            }
        }
        if (right instanceof LeafNode) {
            LeafNode r = (LeafNode) right;
            for (int i = 0; i < r.getItemCount(); i++) {
                if (!contains(arr, idx, r.getArray()[i].getName())) {
                    arr[idx++] = r.getArray()[i];
                }
            }
        }
        

        AirObject[] finalArr = new AirObject[idx];
        System.arraycopy(arr, 0, finalArr, 0, idx);
        return finalArr;
    }

    private boolean contains(AirObject[] arr, int len, String name) {
        for (int i = 0; i < len; i++) {
            if (arr[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private BinNode performMerge() {
        AirObject[] objs = getAllUnique();
        if (objs.length == 0) return EmptyNode.THE_NODE;
        
        LeafNode newLeaf = new LeafNode(objs[0]);
        for (int i = 1; i < objs.length; i++) {
            newLeaf.addDirect(objs[i]);
        }
        return newLeaf;
    }

    @Override
    public void print(StringBuilder sb, SpatialBox box, int level, IntWrapper count) {
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < level * 2; i++) space.append(" ");
        
        sb.append(space).append("I ").append(box.toString()).append(" ")
          .append(level).append("\r\n");
        count.increment();
        
        int axis = level % 3;
        left.print(sb, box.getFirstHalf(axis), level + 1, count);
        right.print(sb, box.getSecondHalf(axis), level + 1, count);
    }

    @Override
    public void findCollisions(StringBuilder sb, SpatialBox box, int level) {
        int axis = level % 3;
        left.findCollisions(sb, box.getFirstHalf(axis), level + 1);
        right.findCollisions(sb, box.getSecondHalf(axis), level + 1);
    }

    @Override
    public void findIntersections(SpatialBox query, SpatialBox box, StringBuilder sb, IntWrapper visits, int level) {
        visits.increment();
        sb.append("In Internal node ").append(box.toString()).append(" ").append(level).append("\r\n");
        
        int axis = level % 3;
        SpatialBox b1 = box.getFirstHalf(axis);
        SpatialBox b2 = box.getSecondHalf(axis);

        if (boxesOverlap(query, b1)) {
            left.findIntersections(query, b1, sb, visits, level + 1);
        }
        if (boxesOverlap(query, b2)) {
            right.findIntersections(query, b2, sb, visits, level + 1);
        }
    }

    private boolean boxesOverlap(SpatialBox a, SpatialBox b) {
        return overlaps(a.getX(), a.getX() + a.getXDimension(), b.getX(), b.getX() + b.getXDimension()) &&
               overlaps(a.getY(), a.getY() + a.getYDimension(), b.getY(), b.getY() + b.getYDimension()) &&
               overlaps(a.getZ(), a.getZ() + a.getZDimension(), b.getZ(), b.getZ() + b.getZDimension());
    }
}
