import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * Buffer pool class. Used by client to read and write to a file.
 * 
 * 
 * @author sohyun
 * @author sshumway
 * @version 4/7/16
 * 
 */
public class BufferPool {

    private BufferList<Buffer> pool;

    private RandomAccessFile input;

    private int blockSize;

    private int hits;

    private int misses;

    private int reads;

    private int writes;

    private long fileLen;

    private static byte[] temp2;

    /**
     * Buffer pool constructor.
     * 
     * @param newDisk
     *            The input file on disk.
     * @param numBuffer
     *            The number of buffers allowed.
     * @throws IOException
     */
    public BufferPool(RandomAccessFile newDisk, 
            int numBuffer) throws IOException {

        pool = new BufferList<Buffer>(numBuffer);
        input = newDisk;
        hits = 0;
        misses = 0;
        reads = 0;
        writes = 0;
        temp2 = new byte[4];
        fileLen = newDisk.length();
        blockSize = 4096;

        // Initializes the buffers in the pool.
        for (int i = 0; i < numBuffer; i++) {
            pool.append(new Buffer(-1, blockSize, null));
        }

    }

    /**
     * Inserts sz bytes from space into position pos in the buffer pool.
     * 
     * @param space
     *            The data to be copied.
     * @param sz
     *            The number of bytes.
     * @param pos
     *            The position of the bytes in the file.
     * @param disk
     *            The parent file of the data.
     * @throws IOException
     */
    public void insert(byte[] space, int sz, int pos, 
            RandomAccessFile disk) throws IOException {
        int blockID = pos / blockSize;
        int poolIndex = this.bufferAt(blockID, disk);

        // if buffer was not in the buffer pool
        if (poolIndex < 0) {
            this.getbytes(temp2, sz, pos, disk);
            poolIndex = this.bufferAt(blockID, disk);

        }
        else {
            pool.moveToPos(poolIndex);
            hits++;
            pool.promote(poolIndex);
        }

        System.arraycopy(space, 0, pool.getValue().getBuffer(), pos % blockSize,
                sz);

        pool.getValue().setDirty(true);
        pool.getValue().setFile(disk);
    }

    /**
     * Copies sz bytes from position pos in the buffer pool to the byte array
     * space.
     * 
     * @param space
     *            The container for the data.
     * @param sz
     *            The number of bytes
     * @param pos
     *            The position in the buffer pool.
     * @param disk
     *            The parent file of the desired data.
     * @throws IOException
     */
    public void getbytes(byte[] space, int sz, int pos, 
            RandomAccessFile disk) throws IOException {
        int blockID = pos / blockSize;
        int poolIndex = this.bufferAt(blockID, disk);

        // if buffer is not in the pool
        if (poolIndex < 0) {
            pool.moveToEnd();
            // buffer is dirty
            if (pool.getValue().isDirty()) {
                // seek and write to the file
                pool.getValue().getFile()
                        .seek(pool.getValue().getID() * blockSize);
                pool.getValue().getFile().write(pool.getValue().getBuffer());
                writes++;

            }
            // moves to correct position in file
            disk.seek(blockID * blockSize);
            // read the new one and put in to the buffer
            disk.read(pool.getValue().getBuffer(), 0, blockSize); // changed to
                                                                  // read
                                                                  // directly
                                                                  // into buffer
            // pool.getValue().setBuffer(temp.clone());
            reads++;

            pool.getValue().setID(blockID);
            pool.getValue().setDirty(false);
            pool.getValue().setFile(disk);
            pool.promote(pool.currPos());
            misses++;

        }
        else {
            pool.promote(poolIndex);
            hits++;
        }

        // gets desired bytes from buffer pool
        int startIndex = pos % blockSize;
        pool.moveToStart();

        System.arraycopy(pool.getValue().getBuffer(), startIndex, space, 0, sz);

    }

    /**
     * flushes all values from cache to disk
     * 
     * @throws IOException
     */
    public void flush() throws IOException {
        pool.moveToStart();
        for (int i = 0; i < pool.length(); pool.moveToPos(++i)) { // changed to
                                                                  // ++i
            if (pool.getValue().isDirty()) {

                pool.getValue().getFile().seek(pool.getValue().getID() * blockSize);
                pool.getValue().getFile().write(pool.getValue().getBuffer());

            }
        }
    }

    /**
     * Determines if the desired buffer block is in the BufferPool
     * 
     * @param blockNumber
     *            block number you are looking for
     * @param file
     *            The file that the desired buffer's data belongs to.
     * @return location in the pool
     */
    private int bufferAt(int blockNumber, RandomAccessFile file) {
        pool.moveToStart();
        for (int i = 0; i < pool.length(); i++) {
            if (blockNumber == pool.getValue().getID()
                    && pool.getValue().getFile() == file) {
                return i;
            }
            pool.next();
        }
        return -1;
    }

    /**
     * Determines length of file
     * 
     * @return file length.
     * @throws IOException
     */
    public long fileLength() throws IOException {
        return fileLen;
    }

    /**
     * Number of times data was found in buffer pool.
     * 
     * @return hits.
     */
    public int getCacheHits() {
        return hits;
    }

    /**
     * Number of times disk was read.
     * 
     * @return number of reads.
     */
    public int getDiskReads() {

        return reads;

    }

    /**
     * Getter for disk writes
     * 
     * @return number of writes.
     */
    public int getDiskWrites() {
        return writes;
    }

    /**
     * Getter for pool misses
     * 
     * @return misses
     */
    public int getCacheMisses() {
        return misses;
    }

}
