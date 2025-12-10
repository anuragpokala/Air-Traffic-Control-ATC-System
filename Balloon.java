/**
 * Balloon object with type and ascent rate.
 *
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class Balloon extends AirObject {

    private String type;
    private int ascentRate;

    /**
     * Create a Balloon.
     *
     * @param n   Name
     * @param x   X origin
     * @param y   Y origin
     * @param z   Z origin
     * @param xw  X width
     * @param yw  Y width
     * @param zw  Z width
     * @param t   Type
     * @param ar  Ascent rate
     */
    public Balloon(String n, int x, int y, int z, int xw, int yw, int zw,
        String t, int ar) {
        super(n, x, y, z, xw, yw, zw);
        type = t;
        ascentRate = ar;
    }

    /**
     * Get the balloon type.
     *
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the ascent rate.
     *
     * @return The ascent rate
     */
    public int getAscentRate() {
        return ascentRate;
    }

    /**
     * Get a string representation of the balloon.
     *
     * @return String representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Balloon ");
        sb.append(getName()).append(" ");
        sb.append(getXorig()).append(" ");
        sb.append(getYorig()).append(" ");
        sb.append(getZorig()).append(" ");
        sb.append(getXwidth()).append(" ");
        sb.append(getYwidth()).append(" ");
        sb.append(getZwidth()).append(" ");
        sb.append(type).append(" ");
        sb.append(ascentRate);
        return sb.toString();
    }
}
