/**
 * Represents a Leaf node containing AirObjects.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class LeafNode implements BinNode {

    private static final int MAX_ITEMS = 3;
    private static final int START_SIZE = 5;

    private AirObject[] data;
    private int count;

    /**
     * Create a leaf with one initial object.
     * @param startObj The first object
     */
    public LeafNode(AirObject startObj) {
        data = new AirObject[START_SIZE];
        data[0] = startObj;
        count = 1;
    }

    private void expand() {
        AirObject[] next = new AirObject[data.length * 2];
        System.arraycopy(data, 0, next, 0, count);
        data = next;
    }

    private int findPos(String name) {
        for (int i = 0; i < count; i++) {
            if (name.compareTo(data[i].getName()) < 0) {
                return i;
            }
        }
        return count;
    }

    private int findIndex(String name) {
        if (name == null) return -1;
        for (int i = 0; i < count; i++) {
            if (data[i].getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private boolean checkIntersectionCommonality() {
        if (count == 0) return true;
        int minX = data[0].getXorig();
        int maxX = minX + data[0].getXwidth();
        int minY = data[0].getYorig();
        int maxY = minY + data[0].getYwidth();
        int minZ = data[0].getZorig();
        int maxZ = minZ + data[0].getZwidth();

        for (int i = 1; i < count; i++) {
            AirObject obj = data[i];
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
    
    // Check if overlap exists (helper)
    private boolean overlaps(int s1, int e1, int s2, int e2) {
        return s1 < e2 && s2 < e1;
    }

    private boolean objIntersectsBox(AirObject obj, SpatialBox box) {
        return overlaps(obj.getXorig(), obj.getXorig() + obj.getXwidth(), box.getX(), box.getX() + box.getXDimension()) &&
               overlaps(obj.getYorig(), obj.getYorig() + obj.getYwidth(), box.getY(), box.getY() + box.getYDimension()) &&
               overlaps(obj.getZorig(), obj.getZorig() + obj.getZwidth(), box.getZ(), box.getZ() + box.getZDimension());
    }

    @Override
    public BinNode insert(AirObject obj, SpatialBox box, int level) {
        if (count >= data.length) {
            expand();
        }
        int idx = findPos(obj.getName());
        if (idx < count) {
            System.arraycopy(data, idx, data, idx + 1, count - idx);
        }
        data[idx] = obj;
        count++;


        if (count > MAX_ITEMS && box.canBeSplit(level % 3)) {
            if (!checkIntersectionCommonality()) {
                InternalNode newNode = new InternalNode();
                for (int i = 0; i < count; i++) {
                    newNode.insert(data[i], box, level);
                }
                return newNode;
            }
        }
        return this;
    }

    @Override
    public BinNode remove(String name, SpatialBox box, RemovalContainer held, int level) {
        int idx = findIndex(name);
        if (idx != -1) {
            if (held != null) held.set(data[idx]);
            if (idx < count - 1) {
                System.arraycopy(data, idx + 1, data, idx, count - idx - 1);
            }
            data[count - 1] = null;
            count--;

            if (count == 0) {
                return EmptyNode.THE_NODE;
            }
        }
        return this;
    }

    @Override
    public void print(StringBuilder sb, SpatialBox box, int level, IntWrapper countVar) {
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < level * 2; i++) space.append(" ");
        
        sb.append(space).append("Leaf with ").append(count).append(" objects ")
          .append(box.toString()).append(" ").append(level).append("\r\n");
        
        for (int i = 0; i < count; i++) {
            sb.append(space).append("(").append(data[i].toString()).append(")\r\n");
        }
        countVar.increment();
    }

    @Override
    public void findCollisions(StringBuilder sb, SpatialBox box, int level) {
        sb.append("In leaf node ").append(box.toString()).append(" ").append(level).append("\r\n");
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                AirObject a = data[i];
                AirObject b = data[j];
                
                boolean boxesTouch = 
                    overlaps(a.getXorig(), a.getXorig() + a.getXwidth(), b.getXorig(), b.getXorig() + b.getXwidth()) &&
                    overlaps(a.getYorig(), a.getYorig() + a.getYwidth(), b.getYorig(), b.getYorig() + b.getYwidth()) &&
                    overlaps(a.getZorig(), a.getZorig() + a.getZwidth(), b.getZorig(), b.getZorig() + b.getZwidth());

                if (boxesTouch) {
                    int colX = Math.max(a.getXorig(), b.getXorig());
                    int colY = Math.max(a.getYorig(), b.getYorig());
                    int colZ = Math.max(a.getZorig(), b.getZorig());
                    
                    if (box.encloses(colX, colY, colZ)) {
                        sb.append("(").append(a.toString()).append(") and (")
                          .append(b.toString()).append(")\r\n");
                    }
                }
            }
        }
    }

    @Override
    public void findIntersections(SpatialBox query, SpatialBox box, StringBuilder sb, IntWrapper visits, int level) {
        visits.increment();
        sb.append("In leaf node ").append(box.toString()).append(" ").append(level).append("\r\n");
        for (int i = 0; i < count; i++) {
            AirObject obj = data[i];
            if (objIntersectsBox(obj, query) && box.encloses(obj.getXorig(), obj.getYorig(), obj.getZorig())) {
                sb.append(obj.toString()).append("\r\n");
            }
        }
    }
    
    // Package helper for merging
    int getItemCount() { return count; }
    AirObject[] getArray() { return data; }
    void addDirect(AirObject o) {
        if (count >= data.length) expand();
        int idx = findPos(o.getName());
        if (idx < count) System.arraycopy(data, idx, data, idx + 1, count - idx);
        data[idx] = o;
        count++;
    }
}
