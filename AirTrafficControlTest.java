import java.util.Random;
import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class AirControlTest extends TestCase {

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Get code coverage of the class declaration.
     *
     * @throws Exception
     */
    public void testRInit() throws Exception {
        AirControl recstore = new AirControl();
        assertNotNull(recstore);
    }


    // ----------------------------------------------------------
    /**
     * Test syntax: Sample Input/Output
     *
     * @throws Exception
     */
    public void testSampleInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFalse(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertTrue(w.add(new Rocket("Enterprise",
            0, 100, 20, 10, 50, 50, 5000, 99.29)));

        assertFuzzyEquals(
            "Rocket Enterprise 0 100 20 10 50 50 5000 99.29",
            w.delete("Enterprise"));

        assertFuzzyEquals("Airplane Air1 0 10 1 20 2 30 USAir 717 4",
            w.print("Air1"));
        assertNull(w.print("air1"));

        assertFuzzyEquals(
            "Found these records in the range a to z\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n",
            w.rangeprint("a", "z"));
        assertFuzzyEquals(
            "Found these records in the range a to l\r\n",
            w.rangeprint("a", "l"));
        assertNull(w.rangeprint("z", "a"));
    }


    // ----------------------------------------------------------
    /**
     * Test syntax: Check various forms of bad input parameters
     *
     * @throws Exception
     */
    public void testBadInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, null, 1, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 0, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 1, 0)));
        assertFalse(w.add(new Balloon(null, 1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", -1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, -1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, -1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 0, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 0, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 0, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, "hot", -1)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, "Ostrich", 0)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, "Droner", 0)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, -1, 1.1)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, 1, -1.1)));
        assertFalse(w.add(
            new AirPlane("a", 2000, 1, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 2000, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 2000, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 2000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 1, 2000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 1, 1, 2000, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1000, 1, 1, 1000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1000, 1, 1, 1000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1000, 1, 1, 1000, "Alaska", 1, 1)));
        assertNull(w.delete(null));
        assertNull(w.print(null));
        assertNull(w.rangeprint(null, "a"));
        assertNull(w.rangeprint("a", null));
        assertNull(w.intersect(-1, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, -1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, -1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, -1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, -1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, -1));
        assertNull(w.intersect(2000, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 2000, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 2000, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 2000, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 2000, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, 2000));
        assertNull(w.intersect(1000, 1, 1, 1000, 1, 1));
        assertNull(w.intersect(1, 1000, 1, 1, 1000, 1));
        assertNull(w.intersect(1, 1, 1000, 1, 1, 1000));
    }


    // ----------------------------------------------------------
    /**
     * Test empty: Check various returns from commands on empty database
     *
     * @throws Exception
     */
    public void testEmpty() throws Exception {
        WorldDB w = new WorldDB(null);
        assertNull(w.delete("hello"));
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
            w.printbintree());
        assertNull(w.print("hello"));
        assertFuzzyEquals("Found these records in the range begin to end\n",
            w.rangeprint("begin", "end"));
        assertFuzzyEquals("The following collisions exist in the database:\n",
            w.collisions());
        assertFuzzyEquals(
            "The following objects intersect (1, 1, 1, 1, 1, 1)\n" +
                "1 nodes were visited in the bintree\n",
            w.intersect(1, 1, 1, 1, 1, 1));
    }


    // ----------------------------------------------------------
    /**
     * Test that clear resets the world to its initial empty state.
     *
     * @throws Exception
     */
    public void testClearResetsWorld() throws Exception {
        WorldDB w = new WorldDB(null);
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertNotNull(w.print("Air1"));
        w.clear();
        assertNull(w.print("Air1"));
        assertNull(w.delete("Air1"));
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
            w.printbintree());
    }


    // ----------------------------------------------------------
    /**
     * Test that inserting many objects forces internal storage growth
     * and all objects remain accessible.
     *
     * @throws Exception
     */
    public void testEnsureCapacity() throws Exception {
        WorldDB w = new WorldDB(null);
        for (int i = 0; i < 12; i++) {
            String name = "P" + i;
            assertTrue(w.add(new Balloon(name,
                i, i, i, 10, 10, 10, "hot", 1)));
        }
        assertNotNull(w.print("P0"));
        assertNotNull(w.print("P11"));
    }


    // ----------------------------------------------------------
    /**
     * Test rangeprint when names are exactly on the min and max bounds.
     *
     * @throws Exception
     */
    public void testRangeprintEdgeBounds() throws Exception {
        WorldDB w = new WorldDB(null);
        assertTrue(w.add(new Balloon("a",
            0, 0, 0, 10, 10, 10, "hot", 1)));
        assertTrue(w.add(new Balloon("mid",
            20, 20, 20, 10, 10, 10, "hot", 1)));
        assertTrue(w.add(new Balloon("z",
            40, 40, 40, 10, 10, 10, "hot", 1)));
        String result = w.rangeprint("a", "z");
        assertNotNull(result);
        assertTrue(result.indexOf("Balloon a 0 0 0 10 10 10 hot 1") >= 0);
        assertTrue(result.indexOf("Balloon mid 20 20 20 10 10 10 hot 1") >= 0);
        assertTrue(result.indexOf("Balloon z 40 40 40 10 10 10 hot 1") >= 0);
    }
    
    // ----------------------------------------------------------
    /**
     * Test add with an empty name.
     *
     * @throws Exception
     */
    public void testAddNullAndEmptyName() throws Exception {
        WorldDB w = new WorldDB(null);
        assertFalse(w.add(new Balloon("",
            1, 1, 1, 1, 1, 1, "hot", 1)));
    }


    // ----------------------------------------------------------
    /**
     * Test that boxes on the world boundary are considered valid.
     *
     * @throws Exception
     */
    public void testAddBoundaryBoxes() throws Exception {
        WorldDB w = new WorldDB(null);
        assertTrue(w.add(new AirPlane("EdgeFull",
            0, 0, 0, 1024, 1024, 1024, "USAir", 1, 1)));
        assertTrue(w.add(new AirPlane("EdgeCorner",
            1023, 1023, 1023, 1, 1, 1, "USAir", 2, 2)));
        assertNotNull(w.print("EdgeFull"));
        assertNotNull(w.print("EdgeCorner"));
    }


    // ----------------------------------------------------------
    /**
     * Test intersect with a full-world bounding box on a non-empty database.
     *
     * @throws Exception
     */
    public void testIntersectFullWorldBox() throws Exception {
        WorldDB w = new WorldDB(null);
        assertTrue(w.add(new Balloon("B1",
            10, 10, 10, 10, 10, 10, "hot", 1)));
        String result = w.intersect(0, 0, 0, 1024, 1024, 1024);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }


    // ----------------------------------------------------------
    /**
     * Test non-empty printskiplist output and name ordering.
     *
     * @throws Exception
     */
    public void testNonEmptyPrintskiplist() throws Exception {
        WorldDB w = new WorldDB(null);
        assertTrue(w.add(new Balloon("M",
            0, 0, 0, 10, 10, 10, "hot", 1)));
        assertTrue(w.add(new Balloon("A",
            20, 20, 20, 10, 10, 10, "hot", 1)));
        assertTrue(w.add(new Balloon("Z",
            40, 40, 40, 10, 10, 10, "hot", 1)));
        String out = w.printskiplist();
        int idxA = out.indexOf("Balloon A 20 20 20 10 10 10 hot 1");
        int idxM = out.indexOf("Balloon M 0 0 0 10 10 10 hot 1");
        int idxZ = out.indexOf("Balloon Z 40 40 40 10 10 10 hot 1");
        assertTrue(idxA >= 0);
        assertTrue(idxM > idxA);
        assertTrue(idxZ > idxM);
    }
}
