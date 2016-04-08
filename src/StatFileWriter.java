import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is for writing statistics to the file.
 * 
 * @author sohyun
 * @author sshumway
 * @version 4/7/2016
 *
 */
public class StatFileWriter {
    private FileWriter writer;
    private BufferedWriter bw;
    private BufferPool pool;

    /**
     * constructor for the writer
     * 
     * @param statFile
     *            is the file that writes to
     * @param b
     *            is buffer pool
     * @throws IOException
     */
    public StatFileWriter(String statFile, BufferPool b) throws IOException {
        writer = new FileWriter(statFile, true);
        bw = new BufferedWriter(writer);
        pool = b;

    }

    /**
     * Actually write to the file
     * 
     * @param filename
     *            is the input file name
     * @param time
     *            how long it took
     * @throws IOException
     */
    public void write(String filename, double time) throws IOException {
        bw.write("Filename:     " + filename + '\n');
        bw.write("Cache Hits:   " + pool.getCacheHits() + '\n');
        bw.write("Cache Misses: " + pool.getCacheMisses() + '\n');
        bw.write("Disk Reads:   " + pool.getDiskReads() + '\n');
        bw.write("Disk Writes:  " + pool.getDiskWrites() + '\n');
        bw.write("Time (ms):    " + time + '\n');
        bw.write('\n');
        bw.close();

    }

}
