/**
 * Airplane object with carrier, flight number, and engine count.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class AirPlane extends AirObject {

    private String carrier;
    private int flightNumber;
    private int numEngines;

    /**
     * Create an AirPlane.
     *
     * @param n   Name
     * @param x   X origin
     * @param y   Y origin
     * @param z   Z origin
     * @param xw  X width
     * @param yw  Y width
     * @param zw  Z width
     * @param c   Carrier
     * @param fn  Flight number
     * @param ne  Number of engines
     */
    public AirPlane(String n, int x, int y, int z, int xw, int yw, int zw,
        String c, int fn, int ne) {
        super(n, x, y, z, xw, yw, zw);
        carrier = c;
        flightNumber = fn;
        numEngines = ne;
    }

    /**
     * Get the carrier.
     *
     * @return The carrier
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * Get the flight number.
     *
     * @return The flight number
     */
    public int getFlightNumber() {
        return flightNumber;
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
     * Get a string representation of the airplane.
     *
     * @return String representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Airplane ");
        sb.append(getName()).append(" ");
        sb.append(getXorig()).append(" ");
        sb.append(getYorig()).append(" ");
        sb.append(getZorig()).append(" ");
        sb.append(getXwidth()).append(" ");
        sb.append(getYwidth()).append(" ");
        sb.append(getZwidth()).append(" ");
        sb.append(carrier).append(" ");
        sb.append(flightNumber).append(" ");
        sb.append(numEngines);
        return sb.toString();
    }
}
