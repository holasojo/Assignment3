import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class BufferSort {

    private byte[] buf;
    private BufferPool pool;
    private long arrayLength;

    public BufferSort(BufferPool thePool) throws IOException {

        buf = new byte[4];
        pool = thePool;
        arrayLength = pool.fileLength()/4;

    }

    private void mergeSortHelp(RandomAccessFile inputfile,
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
            pool.getbytes(buf, 4, i*4, inputfile);
            pool.insert(buf, 4, i*4, tempfile);
        }
        // Do the merge operation back to A
        int i1 = left;
        int i2 = mid + 1;
       
        for (int curr = left; curr <= right; curr++) {
         
            if (i1 == mid + 1) // Left sublist exhausted
            {
                pool.getbytes(buf, 4, i2*4, tempfile);
                pool.insert(buf, 4, curr*4, inputfile);
                i2++;
            }

            else if (i2 > right) {
                pool.getbytes(buf, 4, i1*4, tempfile);
                pool.insert(buf, 4, curr*4, inputfile);
                i1++;
            }

            else if (getKey(buf, 4, i1*4, tempfile) <= getKey(buf, 4, i2*4, tempfile)) {
                pool.getbytes(buf, 4, i1*4, tempfile);
                pool.insert(buf, 4, curr*4, inputfile);
                i1++;
            }

            else {
                pool.getbytes(buf, 4, i2*4, tempfile);
                pool.insert(buf, 4, curr*4, inputfile);
                i2++;
            }

        }

    }

    public void mergeSort(RandomAccessFile inputfile,
            RandomAccessFile tempfile) throws IOException {
        mergeSortHelp(inputfile, tempfile, 0, (int) (arrayLength-1));
    }

    private short getKey(byte[] space, int sz, int pos, RandomAccessFile file) throws IOException {
        pool.getbytes(space, sz, pos, file);
        short bytebuf = ByteBuffer.wrap(space).getShort();
        return bytebuf;
    }
}
