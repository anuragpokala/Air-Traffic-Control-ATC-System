import java.util.Random;

/**
 * Skip List implementation.
 * 
 * @param <K> Key type (must be Comparable)
 * @param <V> Value type
 * 
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class SkipList<K extends Comparable<K>, V> {

    private SkipNode<K, V> head;
    private int level;
    private int size;
    private Random rnd;

    /**
     * Create a new Skip List.
     * @param r Random number generator to use
     */
    public SkipList(Random r) {
        rnd = r;
        level = 0;  // ← CHANGE FROM -1 to 0
        size = 0;
        head = new SkipNode<>(null, null, 0);
    }

    /**
     * Generate a random level for a new node.
     * Pick a level using a geometric distribution.
     * @return The level
     */
    private int randomLevel() {
        int lev = 0;
        while (Math.abs(rnd.nextInt()) % 2 == 0) {
            lev++;
        }
        return lev;
    }

    /**
     * Adjust head node to accommodate a new level.
     * @param newLevel The new level needed
     */
    @SuppressWarnings("unchecked")
    private void adjustHead(int newLevel) {
        SkipNode<K, V> oldHead = head;
        head = new SkipNode<>(null, null, newLevel);
        for (int i = 0; i <= oldHead.getLevel(); i++) {
            head.setForward(i, oldHead.getForward(i));
        }
        level = newLevel;
    }

    /**
     * Find a value by key.
     * @param key The key to search for
     * @return The value, or null if not found
     */
    public V find(K key) {
        if (size == 0) {
            return null;
        }
        SkipNode<K, V> curr = head;
        for (int i = level; i >= 0; i--) {
            while (curr.getForward(i) != null &&
                   curr.getForward(i).getKey().compareTo(key) < 0) {
                curr = curr.getForward(i);
            }
        }
        curr = curr.getForward(0);
        if (curr != null && curr.getKey().compareTo(key) == 0) {
            return curr.getValue();
        }
        return null;
    }

    /**
     * Insert a key-value pair.
     * @param key The key
     * @param value The value
     */
    @SuppressWarnings("unchecked")
    public void insert(K key, V value) {
        int newLevel = randomLevel();
        if (newLevel > level) {
            adjustHead(newLevel);
        }
        SkipNode<K, V>[] update = new SkipNode[level + 1];
        SkipNode<K, V> curr = head;
        for (int i = level; i >= 0; i--) {
            while (curr.getForward(i) != null &&
                   curr.getForward(i).getKey().compareTo(key) < 0) {
                curr = curr.getForward(i);
            }
            update[i] = curr;
        }
        SkipNode<K, V> newNode = new SkipNode<>(key, value, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            newNode.setForward(i, update[i].getForward(i));
            update[i].setForward(i, newNode);
        }
        size++;
    }

    /**
     * Remove a key-value pair by key.
     * @param key The key to remove
     * @return The removed value, or null if not found
     */
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        if (size == 0) {
            return null;
        }
        SkipNode<K, V>[] update = new SkipNode[level + 1];
        SkipNode<K, V> curr = head;
        for (int i = level; i >= 0; i--) {
            while (curr.getForward(i) != null &&
                   curr.getForward(i).getKey().compareTo(key) < 0) {
                curr = curr.getForward(i);
            }
            update[i] = curr;
        }
        curr = curr.getForward(0);
        if (curr == null || curr.getKey().compareTo(key) != 0) {
            return null;
        }
        V val = curr.getValue();
        for (int i = 0; i <= curr.getLevel(); i++) {
            update[i].setForward(i, curr.getForward(i));
        }
        size--;
        return val;
    }

    /**
     * Get the number of elements.
     * @return The size
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the head node (for iteration).
     * @return The head node
     */
    public SkipNode<K, V> getHead() {
        return head;
    }
    
    
}
