import java.util.Random;

/**
 * Skip List implementation for AirObjects.
 * 
 * @author Anurag Pokala (anuragp34) 
 * @author Parth Mehta (pmehta24)
 * @version 12/3/2025
 */
public class SkipList {

    private static final int MAX_HEIGHT = 16;
    
    private SkipNode sentinel;
    private int currentHeight;
    private int numberOfNodes;
    private Random rng;

    /**
     * Create a new Skip List.
     * @param r Random number generator to use
     */
    public SkipList(Random r) {
        rng = (r == null) ? new Random() : r;
        sentinel = new SkipNode(null, null, MAX_HEIGHT);
        currentHeight = 1;
        numberOfNodes = 0;
    }

    /**
     * Generate a random level for a new node.
     * Pick a level using a geometric distribution.
     * @return The level
     */
    private int pickLevel() {
        int lvl = 1;
        while (lvl < MAX_HEIGHT && Math.abs(rng.nextInt()) % 2 == 0) {
            lvl++;
        }
        return lvl;
    }

    /**
     * Find a value by key.
     * @param key The key to search for
     * @return The value, or null if not found
     */
    public AirObject search(String key) {
        if (key == null) return null;
        
        SkipNode curr = sentinel;
        for (int i = currentHeight - 1; i >= 0; i--) {
            while (curr.getNext(i) != null) {
                String nextKey = curr.getNext(i).getKey();
                if (nextKey == null || nextKey.compareTo(key) >= 0) {
                    break;
                }
                curr = curr.getNext(i);
            }
            if (curr.getNext(i) != null && curr.getNext(i)
                .getKey().equals(key)) {
                return curr.getNext(i).getValue();
            }
        }
        return null;
    }

    /**
     * Insert a value.
     * @param value The value
     * @return true if inserted, false if duplicate
     */
    public boolean insert(AirObject value) {
        if (value == null) return false;
        String key = value.getName();
        
        SkipNode[] preds = new SkipNode[MAX_HEIGHT];
        SkipNode curr = sentinel;

        for (int i = currentHeight - 1; i >= 0; i--) {
            while (true) {
                SkipNode next = curr.getNext(i);
                if (next == null || next.getKey().compareTo(key) >= 0) {
                    break;
                }
                curr = next;
            }
            preds[i] = curr;
        }

        SkipNode nextNode = curr.getNext(0);
        if (nextNode != null && nextNode.getKey().equals(key)) {
            return false; 
        }

        int newLvl = pickLevel();
        if (newLvl > currentHeight) {
            for (int i = currentHeight; i < newLvl; i++) {
                preds[i] = sentinel;
            }
            currentHeight = newLvl;
        }

        SkipNode nodeToInsert = new SkipNode(key, value, newLvl);
        for (int i = 0; i < newLvl; i++) {
            nodeToInsert.setNext(i, preds[i].getNext(i));
            preds[i].setNext(i, nodeToInsert);
        }
        
        numberOfNodes++;
        return true;
    }

    /**
     * Remove a key-value pair by key.
     * @param key The key to remove
     * @return The removed value, or null if not found
     */
    public AirObject remove(String key) {
        if (key == null) return null;

        SkipNode[] preds = new SkipNode[MAX_HEIGHT];
        SkipNode curr = sentinel;

        for (int i = currentHeight - 1; i >= 0; i--) {
            while (true) {
                SkipNode next = curr.getNext(i);
                if (next == null || next.getKey().compareTo(key) >= 0) {
                    break;
                }
                curr = next;
            }
            preds[i] = curr;
        }

        SkipNode target = curr.getNext(0);
        if (target == null || !target.getKey().equals(key)) {
            return null;
        }

        for (int i = 0; i < currentHeight; i++) {
            if (preds[i].getNext(i) != target) {
                break;
            }
            preds[i].setNext(i, target.getNext(i));
        }

        while (currentHeight > 1 && sentinel
            .getNext(currentHeight - 1) == null) {
            currentHeight--;
        }
        
        numberOfNodes--;
        return target.getValue();
    }
    
    /**
     * Dump the skip list contents within a range.
     * @param sb StringBuilder to append to
     * @param min Minimum key (inclusive)
     * @param max Maximum key (inclusive)
     */
    public void dumpRange(StringBuilder sb, String min, String max) {
        SkipNode curr = sentinel.getNext(0);
        while (curr != null) {
            String k = curr.getKey();
            if (k.toLowerCase().compareTo(min.toLowerCase()) >= 0 && 
                k.toLowerCase().compareTo(max.toLowerCase()) <= 0) {
                sb.append(curr.getValue().toString()).append("\n");
            }
            curr = curr.getNext(0);
        }
    }

    /**
     * Get the head (sentinel) node.
     * @return The sentinel node
     */
    public SkipNode getHead() {
        return sentinel;
    }

    /**
     * Dump the skip list contents.
     * @return String description
     */
    public String describe() {
        if (numberOfNodes == 0) {
            return "SkipList is empty";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Node has depth ").append(currentHeight)
        .append(", Value (null)\n");
        SkipNode curr = sentinel.getNext(0);
        int count = 0;
        while (curr != null) {
            sb.append("Node has depth ").append(curr.getLevels())
              .append(", Value (").append(curr.
                  getValue().toString()).append(")\n");
            curr = curr.getNext(0);
            count++;
        }
        sb.append(count).append(" skiplist nodes printed\n");
        return sb.toString();
    }
}
