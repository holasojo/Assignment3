import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Buffer class, holds data being stored originally stored on disk.
 * 
 * @author sohyun
 * @author sshumway
 * @version 4/7/16
 * 
 *
 */
public class Buffer {

    private int id;
    private boolean dirtyBit;
    private byte[] buffer;
    private RandomAccessFile disk;

    /**
     * Constructor for buffer.
     * 
     * @param size
     *            The byte size of the buffer.
     * @param blockID
     *            The ID to determine data's position in disk file.
     * @param file
     *            The parent file to which the data belongs.
     */
    public Buffer(int blockID, int size, RandomAccessFile file) {
        buffer = new byte[size];
        id = blockID;
        disk = file;
        dirtyBit = false;
    }

    /**
     * Determines if the data in the buffer has been changed.
     * 
     * @return True if changed, else false.
     */
    public boolean isDirty() {
        return dirtyBit;
    }

    /**
     * Sets the buffers parent file. Used when buffer is written to disk and
     * then reused with a different file.
     * 
     * @param file
     *            The parent file.
     */
    public void setFile(RandomAccessFile file) {
        disk = file;
    }

    /**
     * Used to obtain reference to parent file. Used when the client is
     * searching for a specific buffer.
     * 
     * @return The parent file.
     */
    public RandomAccessFile getFile() {
        return disk;
    }

    /**
     * Setter for the dirty bit.
     * 
     * @param bool
     *            True or false.
     */
    public void setDirty(boolean bool) {
        dirtyBit = bool;
    }

    /**
     * Returns block id, used to determine what position to write the buffer to
     * in the original file.
     * 
     * @return The block ID.
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the block ID.
     * 
     * @param num
     *            The ID.
     */
    public void setID(int num) {
        this.id = num;
    }

    /**
     * Returns the data stored in the buffer.
     * 
     * @return The byte array.
     */
    public byte[] getBuffer() {
        return buffer;
    }

}
