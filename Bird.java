/**
 * Bird object with type and count.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class Bird extends AirObject {

    private String type;
    private int number;

    /**
     * Create a Bird.
     *
     * @param n   Name
     * @param x   X origin
     * @param y   Y origin
     * @param z   Z origin
     * @param xw  X width
     * @param yw  Y width
     * @param zw  Z width
     * @param t   Type
     * @param num Number of birds
     */
    public Bird(String n, int x, int y, int z, int xw, int yw, int zw,
        String t, int num) {
        super(n, x, y, z, xw, yw, zw);
        type = t;
        number = num;
    }

    /**
     * Get the bird type.
     *
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the number of birds.
     *
     * @return The number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get a string representation of the bird.
     *
     * @return String representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bird ");
        sb.append(getName()).append(" ");
        sb.append(getXorig()).append(" ");
        sb.append(getYorig()).append(" ");
        sb.append(getZorig()).append(" ");
        sb.append(getXwidth()).append(" ");
        sb.append(getYwidth()).append(" ");
        sb.append(getZwidth()).append(" ");
        sb.append(type).append(" ");
        sb.append(number);
        return sb.toString();
    }
}
