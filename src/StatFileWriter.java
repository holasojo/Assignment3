import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StatFileWriter {
    FileWriter writer;
    BufferedWriter bw;
    BufferPool pool;

    public StatFileWriter(String statFile, BufferPool b) throws IOException {
        writer = new FileWriter(statFile, true);
        bw = new BufferedWriter(writer);
        pool = b;

    }

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
