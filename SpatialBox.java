/**
 * Represents a 3D rectangular box in the spatial monitoring system.
 * Used for Bintree subdivision.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class SpatialBox {
    private final int xPos;
    private final int yPos;
    private final int zPos;
    private final int xSize;
    private final int ySize;
    private final int zSize;

    /**
     * Create a new SpatialBox.
     *
     * @param x      X origin
     * @param y      Y origin
     * @param z      Z origin
     * @param width  Width in X
     * @param height Width in Y
     * @param depth  Width in Z
     */
    public SpatialBox(int x, int y, int z, int width, int height, int depth) {
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
        this.xSize = width;
        this.ySize = height;
        this.zSize = depth;
    }

    /**
     * Get X origin.
     * @return x coordinate
     */
    public int getX() {
        return xPos;
    }

    /**
     * Get Y origin.
     * @return y coordinate
     */
    public int getY() {
        return yPos;
    }

    /**
     * Get Z origin.
     * @return z coordinate
     */
    public int getZ() {
        return zPos;
    }

    /**
     * Get size along X axis.
     * @return width
     */
    public int getXDimension() {
        return xSize;
    }

    /**
     * Get size along Y axis.
     * @return height
     */
    public int getYDimension() {
        return ySize;
    }

    /**
     * Get size along Z axis.
     * @return depth
     */
    public int getZDimension() {
        return zSize;
    }

    /**
     * Check if the box can be split along the specified axis.
     *
     * @param axisIndex 0 for X, 1 for Y, 2 for Z
     * @return true if dimension >= 2
     */
    public boolean canBeSplit(int axisIndex) {
        return getDimension(axisIndex) >= 2;
    }

    /**
     * Get the 'lower' (first) half of the box when split along an axis.
     *
     * @param axisIndex Axis to split (0, 1, or 2)
     * @return New SpatialBox representing the first half
     */
    public SpatialBox getFirstHalf(int axisIndex) {
        return split(axisIndex, true);
    }

    /**
     * Get the 'upper' (second) half of the box when split along an axis.
     *
     * @param axisIndex Axis to split (0, 1, or 2)
     * @return New SpatialBox representing the second half
     */
    public SpatialBox getSecondHalf(int axisIndex) {
        return split(axisIndex, false);
    }

    /**
     * Check if a point is contained within this box.
     *
     * @param px Point X
     * @param py Point Y
     * @param pz Point Z
     * @return true if inside (inclusive start, exclusive end)
     */
    public boolean encloses(int px, int py, int pz) {
        return isInside(px, xPos, xSize) && 
               isInside(py, yPos, ySize) && 
               isInside(pz, zPos, zSize);
    }

    @Override
    public String toString() {
        return "(" + xPos + ", " + yPos + ", " + zPos + ", " + 
               xSize + ", " + ySize + ", " + zSize + ")";
    }

    private int getDimension(int axis) {
        if (axis == 0) return xSize;
        if (axis == 1) return ySize;
        return zSize;
    }

    private SpatialBox split(int axis, boolean isLower) {
        int nx = xPos;
        int ny = yPos;
        int nz = zPos;
        int nw = xSize;
        int nh = ySize;
        int nd = zSize;

        switch (axis) {
            case 0:
                nw = xSize / 2;
                if (!isLower) {
                    nx += nw; // Move origin
                    nw = xSize - nw; // Remainder width
                }
                break;
            case 1:
                nh = ySize / 2;
                if (!isLower) {
                    ny += nh;
                    nh = ySize - nh;
                }
                break;
            case 2:
            default:
                nd = zSize / 2;
                if (!isLower) {
                    nz += nd;
                    nd = zSize - nd;
                }
                break;
        }
        return new SpatialBox(nx, ny, nz, nw, nh, nd);
    }

    private boolean isInside(int val, int start, int len) {
        return val >= start && val < start + len;
    }
}

