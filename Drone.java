/**
 * Drone object with brand and engine count.
 *
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class Drone extends AirObject {

    private String brand;
    private int numEngines;

    /**
     * Create a Drone.
     *
     * @param n   Name
     * @param x   X origin
     * @param y   Y origin
     * @param z   Z origin
     * @param xw  X width
     * @param yw  Y width
     * @param zw  Z width
     * @param b   Brand
     * @param ne  Number of engines
     */
    public Drone(String n, int x, int y, int z, int xw, int yw, int zw,
        String b, int ne) {
        super(n, x, y, z, xw, yw, zw);
        brand = b;
        numEngines = ne;
    }

    /**
     * Get the brand.
     *
     * @return The brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Get the number of engines.
     *
     * @return The number of engines
     */
    public int getNumEngines() {
        return numEngines;
    }

    /**
     * Get a string representation of the drone.
     *
     * @return String representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Drone ");
        sb.append(getName()).append(" ");
        sb.append(getXorig()).append(" ");
        sb.append(getYorig()).append(" ");
        sb.append(getZorig()).append(" ");
        sb.append(getXwidth()).append(" ");
        sb.append(getYwidth()).append(" ");
        sb.append(getZwidth()).append(" ");
        sb.append(brand).append(" ");
        sb.append(numEngines);
        return sb.toString();
    }
}
