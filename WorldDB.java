import java.util.Random;

/**
 * The world for this project. We have a Skip List and a Bintree.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class WorldDB implements ATC {
    private final int worldSize = 1024;
    private Random rnd;
    private SkipList<String, AirObject> skipList;
    private Bintree bintree;

    /**
     * Create a brave new World.
     * @param r A random number generator to use
     */
    public WorldDB(Random r) {
        rnd = r;
        if (rnd == null) {
            rnd = new Random();
        }
        clear();
    }

    /**
     * Clear the world.
     */
    public void clear() {
        skipList = new SkipList<>(rnd);
        bintree = new Bintree();
    }

    /**
     * (Try to) insert an AirObject into the database.
     * @param a An AirObject.
     * @return True iff the AirObject is successfully entered into the database
     */
    public boolean add(AirObject a) {
        if (a == null) {
            return false;
        }
        if (!isValidAirObject(a)) {
            return false;
        }
        String name = a.getName();
        if (name == null || name.length() == 0) {
            return false;
        }
        // Check for duplicate BEFORE inserting (important for random sync)
        if (skipList.find(name) != null) {
            return false;
        }
        skipList.insert(name, a);
        bintree.insert(a);
        return true;
    }

    /**
     * Delete an AirObject by name.
     * @param name AirObject name.
     * @return A string representing the AirObject, or null if no such name.
     */
    public String delete(String name) {
        if (name == null) {
            return null;
        }
        AirObject removed = skipList.remove(name);
        if (removed == null) {
            return null;
        }
        bintree.delete(removed);
        return removed.toString();
    }

    /**
     * Return a listing of the Skiplist.
     * @return String listing the AirObjects in the Skiplist as specified.
     */
    public String printskiplist() {
        if (skipList.getSize() == 0) {
            return "SkipList is empty";
        }
        StringBuilder sb = new StringBuilder();
        SkipNode<String, AirObject> curr = skipList.getHead().getForward(0);
        int count = 0;
        while (curr != null) {
            sb.append("Node has depth ");
            sb.append(curr.getLevel());
            sb.append(", Value (");
            sb.append(curr.getValue().toString());
            sb.append(")\n");
            count++;
            curr = curr.getForward(0);
        }
        sb.append(count).append(" skiplist nodes printed\n");
        return sb.toString();
    }

    /**
     * Return a listing of the Bintree nodes in preorder.
     * @return String listing the Bintree nodes as specified.
     */
    public String printbintree() {
        return bintree.print();
    }

    /**
     * Print an AirObject with a given name if it exists.
     * @param name The name of the AirObject to print
     * @return String showing the toString for the AirObject if it exists
     */
    public String print(String name) {
        if (name == null) {
            return null;
        }
        AirObject obj = skipList.find(name);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * Return a listing of the AirObjects in a name range.
     * @param start Minimum of range
     * @param end Maximum of range
     * @return String listing the AirObjects in the range as specified.
     */
    public String rangeprint(String start, String end) {
        if (start == null || end == null) {
            return null;
        }
        if (start.compareTo(end) > 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Found these records in the range ");
        sb.append(start).append(" to ").append(end).append("\n");
        SkipNode<String, AirObject> curr = skipList.getHead().getForward(0);
        while (curr != null) {
            String name = curr.getKey();
            if (name.compareTo(start) >= 0 && name.compareTo(end) <= 0) {
                sb.append(curr.getValue().toString()).append("\n");
            }
            curr = curr.getForward(0);
        }
        return sb.toString();
    }

    /**
     * Return a listing of all collisions.
     * @return String listing the AirObjects that participate in collisions.
     */
    public String collisions() {
        return bintree.collisions();
    }

    /**
     * Return a listing of all AirObjects that intersect a box.
     * @param x Bounding box upper left x
     * @param y Bounding box upper left y
     * @param z Bounding box upper left z
     * @param xwid Bounding box x width
     * @param ywid Bounding box y width
     * @param zwid Bounding box z width
     * @return String listing the AirObjects that intersect the given box.
     */
    public String intersect(int x, int y, int z, int xwid, int ywid, int zwid) {
        if (!isValidBox(x, y, z, xwid, ywid, zwid)) {
            return null;
        }
        return bintree.intersect(x, y, z, xwid, ywid, zwid);
    }

    /**
     * Check if an AirObject has valid fields.
     * @param a AirObject to validate
     * @return True if the object is valid
     */
    private boolean isValidAirObject(AirObject a) {
        int x = a.getXorig();
        int y = a.getYorig();
        int z = a.getZorig();
        int xw = a.getXwidth();
        int yw = a.getYwidth();
        int zw = a.getZwidth();
        if (!isValidBox(x, y, z, xw, yw, zw)) {
            return false;
        }
        if (a instanceof AirPlane) {
            AirPlane p = (AirPlane)a;
            if (p.getCarrier() == null) return false;
            if (p.getFlightNumber() <= 0) return false;
            return p.getNumEngines() > 0;
        }
        else if (a instanceof Balloon) {
            Balloon b = (Balloon)a;
            if (b.getType() == null) return false;
            return b.getAscentRate() >= 0;
        }
        else if (a instanceof Bird) {
            Bird b = (Bird)a;
            if (b.getType() == null) return false;
            return b.getNumber() > 0;
        }
        else if (a instanceof Drone) {
            Drone d = (Drone)a;
            if (d.getBrand() == null) return false;
            return d.getNumEngines() > 0;
        }
        else if (a instanceof Rocket) {
            Rocket r = (Rocket)a;
            return r.getAscentRate() >= 0 && r.getTrajectory() >= 0;
        }
        return true;
    }

    /**
     * Check if a bounding box is valid within the world.
     * @param x Box origin x
     * @param y Box origin y
     * @param z Box origin z
     * @param xw Box width x
     * @param yw Box width y
     * @param zw Box width z
     * @return True if the box is valid
     */
    private boolean isValidBox(int x, int y, int z, int xw, int yw, int zw) {
        if (x < 0 || y < 0 || z < 0) return false;
        if (xw <= 0 || yw <= 0 || zw <= 0) return false;
        if (xw > worldSize || yw > worldSize || zw > worldSize) return false;
        if (x >= worldSize || y >= worldSize || z >= worldSize) return false;
        return x + xw <= worldSize && y + yw <= worldSize && z + zw <= worldSize;
    }
}
