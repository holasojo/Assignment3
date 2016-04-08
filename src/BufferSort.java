import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * This class contains the implementation for mergesort that runs on a binary
 * file, and utilizes a buffer pool to interact with the file.
 * 
 * @author sshumway
 * @author sohyun
 * @version 4/7/16
 *
 */
public class BufferSort {

    private byte[] buf;
    private byte[] buf2;
    private BufferPool pool;
    // private long arrayLength;

    /**
     * Constructor for Mergesort class.
     * 
     * @param thePool
     *            The buffer pool that mediates file access.
     * @throws IOException
     */
    public BufferSort(BufferPool thePool) throws IOException {

        buf = new byte[4];
        buf2 = new byte[4];
        pool = thePool;
        // arrayLength = pool.fileLength() / 4;

    }

    /**
     * The mergesort algorithm. Uses an input file and a temporary file as the
     * logical arrays used by mergesort.
     * 
     * @param inputfile
     *            The sourcefile to be sorted.
     * @param tempfile
     *            The second "array"
     * @param left
     *            The left index
     * @param right
     *            The right index
     * @throws IOException
     */
    public void mergeSortHelp(RandomAccessFile inputfile,
            RandomAccessFile tempfile, int left, int right) throws IOException {
        if (left == right) {
            return; // List has one record
        }

        int mid = (left + right) / 2; // Select midpoint

        mergeSortHelp(inputfile, tempfile, left, mid); // Mergesort first half
        mergeSortHelp(inputfile, tempfile, mid + 1, right); // Mergesort second
                                                            // half

        for (int i = left; i <= right; i++) // Copy subarray to temp
        {
            pool.getbytes(buf, 4, i << 2, inputfile);
            pool.insert(buf, 4, i << 2, tempfile);
        }
        // Do the merge operation back to A
        int i1 = left;
        int i2 = mid + 1;
        pool.getbytes(buf, 4, i1 << 2, tempfile);

        pool.getbytes(buf2, 4, i2 << 2, tempfile);

        for (int curr = left; curr <= right; curr++) {

            // pool.getbytes(buf, 4, i1 << 2, tempfile);

            // pool.getbytes(buf2, 4, i2 << 2, tempfile);

            if (i1 == mid + 1) // Left sublist exhausted

            {

                pool.insert(buf2, 4, curr << 2, inputfile);

                i2++;

                // if (curr + 1 <= right)

                pool.getbytes(buf2, 4, i2 << 2, tempfile);

            }

            else if (i2 > right) {

                pool.insert(buf, 4, curr << 2, inputfile);
                i1++;
                pool.getbytes(buf, 4, i1 << 2, tempfile);

            }

            else {

                if (getKey(buf) <= getKey(buf2)) {
                    pool.insert(buf, 4, curr << 2, inputfile);
                    i1++;
                    pool.getbytes(buf, 4, i1 << 2, tempfile);

                }

                else {

                    pool.insert(buf2, 4, curr << 2, inputfile);

                    i2++;

                    pool.getbytes(buf2, 4, i2 << 2, tempfile);

                }

            }
        }

    }

    /**
     * Uses bitwise operators to obtain 2 byte keyValue from a record.
     * 
     * @param record
     *            The byte record
     * @return The key value.
     * @throws IOException
     */
    private short getKey(byte[] record) throws IOException {

        int k = (int) record[0] & 0xFF;
        k <<= 8;
        k += (int) record[1] & 0xFF;
        return (short) k;
    }

}
