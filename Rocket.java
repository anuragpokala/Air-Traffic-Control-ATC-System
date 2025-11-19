/**
 * Rocket object with ascent rate and trajectory.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class Rocket extends AirObject {

    private int ascentRate;
    private double trajectory;

    /**
     * Create a Rocket.
     *
     * @param n   Name
     * @param x   X origin
     * @param y   Y origin
     * @param z   Z origin
     * @param xw  X width
     * @param yw  Y width
     * @param zw  Z width
     * @param ar  Ascent rate
     * @param tr  Trajectory
     */
    public Rocket(String n, int x, int y, int z, int xw, int yw, int zw,
        int ar, double tr) {
        super(n, x, y, z, xw, yw, zw);
        ascentRate = ar;
        trajectory = tr;
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
     * Get the trajectory.
     *
     * @return The trajectory
     */
    public double getTrajectory() {
        return trajectory;
    }

    /**
     * Get a string representation of the rocket.
     *
     * @return String representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rocket ");
        sb.append(getName()).append(" ");
        sb.append(getXorig()).append(" ");
        sb.append(getYorig()).append(" ");
        sb.append(getZorig()).append(" ");
        sb.append(getXwidth()).append(" ");
        sb.append(getYwidth()).append(" ");
        sb.append(getZwidth()).append(" ");
        sb.append(ascentRate).append(" ");
        sb.append(trajectory);
        return sb.toString();
    }
}
