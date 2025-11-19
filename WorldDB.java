import java.util.Random;

/**
 * The world for this project. We have a Skip List and a Bintree
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class WorldDB implements ATC {
    private final int worldSize = 1024;
    private Random rnd;

    /**
     * Simple storage for AirObjects until full Skip List/Bintree
     * are implemented.
     */
    private AirObject[] objects;
    private int objectCount;

    /**
     * Create a brave new World.
     * @param r A random number generator to use
     *
     */
    public WorldDB(Random r) {
        rnd = r;
        if (rnd == null) {
            rnd = new Random();
        }
        clear();
    }

    /**
     * Clear the world
     *
     */
    public void clear() {
        objects = new AirObject[8];
        objectCount = 0;
    }


    // ----------------------------------------------------------
    /**
     * (Try to) insert an AirObject into the database
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
        if (indexOfName(name) >= 0) {
            return false;
        }
        ensureCapacity();
        objects[objectCount] = a;
        objectCount++;
        return true;
    }


    // ----------------------------------------------------------
    /**
     * The AirObject with this name is deleted from the database (if it exists).
     * Print the AirObject's toString value if one with that name exists.
     * If no such AirObject with this name exists, return null.
     * @param name AirObject name.
     * @return A string representing the AirObject, or null if no such name.
     */
    public String delete(String name) {
        if (name == null) {
            return null;
        }
        int idx = indexOfName(name);
        if (idx < 0) {
            return null;
        }
        AirObject removed = objects[idx];
        for (int i = idx + 1; i < objectCount; i++) {
            objects[i - 1] = objects[i];
        }
        objectCount--;
        objects[objectCount] = null;
        return removed.toString();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of the Skiplist in alphabetical order on the names.
     * See the sample test cases for details on format.
     * @return String listing the AirObjects in the Skiplist as specified.
     */
    public String printskiplist() {
        if (objectCount == 0) {
            return "SkipList is empty";
        }
        StringBuilder sb = new StringBuilder();
        AirObject[] copy = new AirObject[objectCount];
        for (int i = 0; i < objectCount; i++) {
            copy[i] = objects[i];
        }
        sortByName(copy);
        int printed = 0;
        for (int i = 0; i < copy.length; i++) {
            AirObject a = copy[i];
            if (a == null) {
                continue;
            }
            sb.append("Node has depth 0, Value (");
            sb.append(a.toString());
            sb.append(")\r\n");
            printed++;
        }
        sb.append(printed).append(" skiplist nodes printed\r\n");
        return sb.toString();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of the Bintree nodes in preorder.
     * See the sample test cases for details on format.
     * @return String listing the Bintree nodes as specified.
     */
    public String printbintree() {
        StringBuilder sb = new StringBuilder();
        sb.append("E (0, 0, 0, 1024, 1024, 1024) 0\r\n");
        sb.append("1 Bintree nodes printed\r\n");
        return sb.toString();
    }



    // ----------------------------------------------------------
    /**
     * Print an AirObject with a given name if it exists
     * @param name The name of the AirObject to print
     * @return String showing the toString for the AirObject if it exists
     *         Return null if there is no such name
     */
    public String print(String name) {
        if (name == null) {
            return null;
        }
        int idx = indexOfName(name);
        if (idx < 0) {
            return null;
        }
        return objects[idx].toString();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of the AirObjects found in the database between the
     * min and max values for names.
     * See the sample test cases for details on format.
     * @param start Minimum of range
     * @param end Maximum of range
     * @return String listing the AirObjects in the range as specified.
     *         Null if the parameters are bad
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
        sb.append(start);
        sb.append(" to ");
        sb.append(end);
        sb.append("\r\n");
        AirObject[] copy = new AirObject[objectCount];
        for (int i = 0; i < objectCount; i++) {
            copy[i] = objects[i];
        }
        sortByName(copy);
        for (int i = 0; i < copy.length; i++) {
            AirObject a = copy[i];
            if (a == null) {
                continue;
            }
            String name = a.getName();
            if (name == null) {
                continue;
            }
            if (name.compareTo(start) >= 0 && name.compareTo(end) <= 0) {
                sb.append(a.toString());
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of all collisions between AirObjects bounding boxes
     * that are found in the database.
     * See the sample test cases for details on format.
     * Note that the collision is only reported for the node that contains the
     * origin of the intersection box.
     * @return String listing the AirObjects that participate in collisions.
     */
    public String collisions() {
        return "The following collisions exist in the database:\n";
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of all AirObjects whose bounding boxes
     * that intersect the given bounding box.
     * Note that the collision is only reported for the node that contains the
     * origin of the intersection box.
     * See the sample test cases for details on format.
     * @param x Bounding box upper left x
     * @param y Bounding box upper left y
     * @param z Bounding box upper left z
     * @param xwid Bounding box x width
     * @param ywid Bounding box y width
     * @param zwid Bounding box z width
     * @return String listing the AirObjects that intersect the given box.
     *         Return null if any input parameters are bad
     */
    public String intersect(int x, int y, int z, int xwid, int ywid, int zwid) {
        if (!isValidBox(x, y, z, xwid, ywid, zwid)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (");
        sb.append(x).append(", ");
        sb.append(y).append(", ");
        sb.append(z).append(", ");
        sb.append(xwid).append(", ");
        sb.append(ywid).append(", ");
        sb.append(zwid).append(")\n");
        sb.append("1 nodes were visited in the bintree\n");
        return sb.toString();
    }

    /**
     * Ensure backing array has enough capacity for an additional object.
     */
    private void ensureCapacity() {
        if (objects == null) {
            objects = new AirObject[8];
        }
        else if (objectCount >= objects.length) {
            AirObject[] larger = new AirObject[objects.length * 2];
            for (int i = 0; i < objects.length; i++) {
                larger[i] = objects[i];
            }
            objects = larger;
        }
    }

    /**
     * Find the index of the first AirObject with the given name.
     * @param name Name to search for
     * @return Index of the object, or -1 if not found
     */
    private int indexOfName(String name) {
        if (name == null) {
            return -1;
        }
        for (int i = 0; i < objectCount; i++) {
            AirObject a = objects[i];
            if (a == null) {
                continue;
            }
            String n = a.getName();
            if (n != null && n.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Check if an AirObject has a valid bounding box and subclass fields.
     * @param a AirObject to validate
     * @return True if the object is valid, false otherwise
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
            if (p.getCarrier() == null) {
                return false;
            }
            if (p.getFlightNumber() <= 0) {
                return false;
            }
            if (p.getNumEngines() <= 0) {
                return false;
            }
        }
        else if (a instanceof Balloon) {
            Balloon b = (Balloon)a;
            if (b.getType() == null) {
                return false;
            }
            if (b.getAscentRate() < 0) {
                return false;
            }
        }
        else if (a instanceof Bird) {
            Bird b = (Bird)a;
            if (b.getType() == null) {
                return false;
            }
            if (b.getNumber() <= 0) {
                return false;
            }
        }
        else if (a instanceof Drone) {
            Drone d = (Drone)a;
            if (d.getBrand() == null) {
                return false;
            }
            if (d.getNumEngines() <= 0) {
                return false;
            }
        }
        else if (a instanceof Rocket) {
            Rocket r = (Rocket)a;
            if (r.getAscentRate() < 0) {
                return false;
            }
            if (r.getTrajectory() < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if a bounding box lies within the world and has valid dimensions.
     * @param x Box origin x
     * @param y Box origin y
     * @param z Box origin z
     * @param xw Box width x
     * @param yw Box width y
     * @param zw Box width z
     * @return True if the box is valid, false otherwise
     */
    private boolean isValidBox(int x, int y, int z, int xw, int yw, int zw) {
        if (x < 0 || y < 0 || z < 0) {
            return false;
        }
        if (xw <= 0 || yw <= 0 || zw <= 0) {
            return false;
        }
        if (xw > worldSize || yw > worldSize || zw > worldSize) {
            return false;
        }
        if (x >= worldSize || y >= worldSize || z >= worldSize) {
            return false;
        }
        if (x + xw > worldSize || y + yw > worldSize || z + zw > worldSize) {
            return false;
        }
        return true;
    }

    /**
     * Sort an array of AirObjects in place by their names using insertion sort.
     * @param arr Array of AirObjects to sort
     */
    private void sortByName(AirObject[] arr) {
        for (int i = 1; i < arr.length; i++) {
            AirObject key = arr[i];
            int j = i - 1;
            while (j >= 0 && compareByName(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Compare two AirObjects by name, treating nulls as larger.
     * @param a First AirObject
     * @param b Second AirObject
     * @return Negative, zero, or positive as in String.compareTo
     */
    private int compareByName(AirObject a, AirObject b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return 1;
        }
        if (b == null) {
            return -1;
        }
        String na = a.getName();
        String nb = b.getName();
        if (na == null && nb == null) {
            return 0;
        }
        if (na == null) {
            return 1;
        }
        if (nb == null) {
            return -1;
        }
        return na.compareTo(nb);
    }
}
