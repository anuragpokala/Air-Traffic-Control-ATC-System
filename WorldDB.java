import java.util.Random;

/**
 * The world database controller.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class WorldDB implements ATC {
    private static final int ENV_SIZE = 1024;
    private Random randomizer;
    private SkipList internalList;
    private Bintree internalTree;

    /**
     * Create the database.
     * @param r Random source
     */
    public WorldDB(Random r) {
        randomizer = (r == null) ? new Random() : r;
        wipe();
    }

    /**
     * Clear the database.
     */
    public void wipe() {
        internalList = new SkipList(randomizer);
        internalTree = new Bintree(ENV_SIZE);
    }
    

    private boolean verifySpecifics(AirObject a) {
        if (a instanceof AirPlane) {
            AirPlane p = (AirPlane)a;
            return AirObject.checkName(p.getCarrier()) && p.getFlightNumber() > 0 && p.getNumEngines() > 0;
        }
        if (a instanceof Balloon) {
            Balloon b = (Balloon)a;
            return AirObject.checkName(b.getType()) && b.getAscentRate() > 0;
        }
        if (a instanceof Bird) {
            Bird b = (Bird)a;
            return AirObject.checkName(b.getType()) && b.getNumber() > 0;
        }
        if (a instanceof Drone) {
            Drone d = (Drone)a;
            return AirObject.checkName(d.getBrand()) && d.getNumEngines() > 0;
        }
        if (a instanceof Rocket) {
            Rocket r = (Rocket)a;
            return r.getAscentRate() > 0 && r.getTrajectory() >= 0;
        }
        return true;
    }

    private boolean verifyObject(AirObject a) {
        if (a == null) return false;
        if (!AirObject.checkName(a.getName())) return false;
        
        if (!AirObject.checkCoord(a.getXorig()) || 
            !AirObject.checkCoord(a.getYorig()) || 
            !AirObject.checkCoord(a.getZorig())) return false;
            
        if (!AirObject.checkDim(a.getXwidth()) || 
            !AirObject.checkDim(a.getYwidth()) || 
            !AirObject.checkDim(a.getZwidth())) return false;
            
        if (!AirObject.checkFit(a.getXorig(), a.getXwidth()) ||
            !AirObject.checkFit(a.getYorig(), a.getYwidth()) ||
            !AirObject.checkFit(a.getZorig(), a.getZwidth())) return false;

        return verifySpecifics(a);
    }

    private boolean verifyBox(int x, int y, int z, int w, int h, int d) {
         if (!AirObject.checkCoord(x) || !AirObject.checkCoord(y) || !AirObject.checkCoord(z)) return false;
         if (!AirObject.checkDim(w) || !AirObject.checkDim(h) || !AirObject.checkDim(d)) return false;
         return AirObject.checkFit(x, w) && AirObject.checkFit(y, h) && AirObject.checkFit(z, d);
    }

    public void clear() {
        wipe();
    }

    public boolean add(AirObject a) {
        if (!verifyObject(a)) return false;
        
        if (internalList.search(a.getName()) != null) {
            return false;
        }

        internalList.insert(a);
        internalTree.insert(a);
        return true;
    }

    public String delete(String name) {
        if (name == null) return null;
        AirObject removed = internalList.remove(name);
        if (removed == null) return null;
        
        internalTree.remove(name);
        return removed.toString();
    }

    public String printskiplist() {
        return internalList.describe();
    }


    public String rangeprint(String start, String end) {
        if (start == null || end == null) return null;
        if (start.compareTo(end) > 0) return null;
        
        StringBuilder b = new StringBuilder();
        b.append("Found these records in the range ").append(start).append(" to ").append(end).append("\n");
        internalList.dumpRange(b, start, end);
        return b.toString();
    }

    public String printbintree() {
        return internalTree.print();
    }

    public String print(String name) {
        if (name == null) return null;
        AirObject a = internalList.search(name);
        return (a == null) ? null : a.toString();
    }

    public String collisions() {
        return internalTree.collisions();
    }

    public String intersect(int x, int y, int z, int w, int h, int d) {
        if (!verifyBox(x, y, z, w, h, d)) return null;
        return internalTree.intersect(x, y, z, w, h, d);
    }
}
