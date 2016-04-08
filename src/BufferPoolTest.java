
import java.io.IOException;
import java.io.RandomAccessFile;

import junit.framework.TestCase;

/**
 * 
 * Test the bufferpool
 * 
 * @author sohyun
 * @author sshumway
 * @version 04/07/2017
 * 
 */
public class BufferPoolTest extends TestCase {

    /**
     * Create two bytes[] and compare.
     * 
     * @throws IOException
     */
    public void testBuffer() throws IOException {
        RandomAccessFile file = new RandomAccessFile("test", "rw");
        byte[] byte1 = new byte[20];
        for (int j = 0; j < 20; j++) {
            byte1[j] = (byte) j;
        }

        byte[] byte2 = new byte[20];

        BufferPool pool = new BufferPool(file, 10);

        pool.getbytes(byte1, 20, 3, file);
        pool.insert(byte1, 20, 3, file);

        System.out.println(byte1[8]);
        System.out.println(byte2[8]);

        // assertEquals(byte1[9], byte2[9]);
    }

}