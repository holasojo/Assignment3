/**
 * Test class for buffer list.
 * 
 * 
 * @author sshumway
 * @author sohyun
 * @version 3/23/2016
 *
 */
public class BufferListTest extends student.TestCase {

    private BufferList<Integer> pool;

    /**
     * Sets up the test fixture.
     */
    public void setUp() {

        pool = new BufferList<Integer>(15);

        for (int i = 0; i < 15; i++) {
            pool.append(i);
        }
    }

    /**
     * Tests functions that traverse and move list elements around.
     */
    public void testTraversal() {
        pool.moveToStart();
        assertEquals(pool.getValue().intValue(), 0);
        pool.moveToEnd();
        assertEquals(pool.getValue().intValue(), 14);
        pool.insert(4);
        assertEquals(4, pool.getValue().intValue());
        pool.remove();
        pool.insert(7);
        pool.clear();

    }

    /**
     * Tests the promote method, which moves the element at the passed index to
     * the front of the list.
     * 
     */
    public void testPromote() {

        for (int i = 0; i < 15; i++) {
            pool.promote(14);
        }

        // System.out.println(pool.toString());

        pool.promote(pool.length() - 1);
        // System.out.println(pool.toString());
        pool.promote(1);
        // System.out.println(pool.toString());
        assertEquals(0, pool.getValue().intValue());

    }

    /**
     * Tests the remove method to make sure it properly shifts the list
     * elements.
     */
    public void testRemove() {
        pool.moveToPos(17);
        assertNull(pool.remove());
        pool.moveToPos(-2);
        assertNull(pool.remove());

        pool.moveToPos(pool.length() - 1);
        pool.remove();

        pool.moveToPos(3);
        // System.out.println(pool.toString());
        pool.remove();
        // System.out.println(pool.toString());

        pool.moveToPos(0);
        pool.prev();
        pool.next();
        pool.prev();
        pool.moveToPos(14);
        pool.next();
    }

}
