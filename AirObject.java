/**
 * Base class for all air objects with a bounding box and a name.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class AirObject implements Comparable<AirObject> {

    private String name;
    private int xorig;
    private int yorig;
    private int zorig;
    private int xwidth;
    private int ywidth;
    private int zwidth;

    /**
     * Create an AirObject with a name and bounding box.
     *
     * @param n   Name of the object
     * @param x   X origin
     * @param y   Y origin
     * @param z   Z origin
     * @param xw  X width
     * @param yw  Y width
     * @param zw  Z width
     */
    public AirObject(String n, int x, int y, int z, int xw, int yw, int zw) {
        name = n;
        xorig = x;
        yorig = y;
        zorig = z;
        xwidth = xw;
        ywidth = yw;
        zwidth = zw;
    }

    /**
     * Get the X origin.
     *
     * @return The X origin
     */
    public int getXorig() {
        return xorig;
    }

    /**
     * Get the Y origin.
     *
     * @return The Y origin
     */
    public int getYorig() {
        return yorig;
    }

    /**
     * Get the Z origin.
     *
     * @return The Z origin
     */
    public int getZorig() {
        return zorig;
    }

    /**
     * Get the X width.
     *
     * @return The X width
     */
    public int getXwidth() {
        return xwidth;
    }

    /**
     * Get the Y width.
     *
     * @return The Y width
     */
    public int getYwidth() {
        return ywidth;
    }

    /**
     * Get the Z width.
     *
     * @return The Z width
     */
    public int getZwidth() {
        return zwidth;
    }

    /**
     * Get the object name.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Compare against another AirObject by name.
     * @param other The other AirObject
     * @return Comparison result
     */
    public int compareTo(AirObject other) {
        return this.name.compareTo(other.name);
    }
}
