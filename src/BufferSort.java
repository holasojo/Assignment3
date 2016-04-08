import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class BufferSort {

    private byte[] buf;
    private byte[] buf2;
    private BufferPool pool;
    private long arrayLength;

    public BufferSort(BufferPool thePool) throws IOException {

        buf = new byte[4];
        buf2 = new byte[4];
        pool = thePool;
        arrayLength = pool.fileLength() / 4;

    }

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
            if (i1 == mid + 1) // Left sublist exhausted
            {
                pool.insert(buf2, 4, curr << 2, inputfile);
                i2++;
                pool.getbytes(buf2, 4, i2 << 2, tempfile);
            }
            else if (i2 > right) {
                pool.insert(buf, 4, curr << 2, inputfile);
                i1++;
                pool.getbytes(buf, 4, i1 << 2, tempfile);
            }
            else {
                short key1 = ByteBuffer.wrap(buf).getShort();
                short key2 = ByteBuffer.wrap(buf2).getShort();
                if (key1 <= key2) {
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

    public void mergeSort(RandomAccessFile inputfile, RandomAccessFile tempfile)
            throws IOException {
        mergeSortHelp(inputfile, tempfile, 0, (int) (arrayLength - 1));
    }

    // private short getKey(byte[] space, int sz, int pos, RandomAccessFile
    // file) throws IOException {
    // pool.getbytes(space, sz, pos, file);
    // short keyVal = ByteBuffer.wrap(space).getShort();
    // return keyVal;
    // }
}
