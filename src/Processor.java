import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Class to process to sort files. It runs the sorting.
 * 
 * @author sohyun
 * @author sshumway
 * @version 4/07/2016
 *
 */
public class Processor {

    private String dataFile;
    private String statFile;
    private String tempFile;
    private int buffNum;
    private RandomAccessFile dataAccess;
    private RandomAccessFile tempFileAccess;

    /**
     * Constructor
     * 
     * @param data
     *            is inputfile
     * @param stat
     *            is file we write Stats
     * @param buff
     *            is the number of buffers
     * @throws FileNotFoundException
     */
    public Processor(String data, String stat, int buff)
            throws FileNotFoundException {
        dataFile = data;
        statFile = stat;
        tempFile = "temp.txt";
        buffNum = buff;
        dataAccess = new RandomAccessFile(dataFile, "rw");
        tempFileAccess = new RandomAccessFile(tempFile, "rw");

    }

    /**
     * Calls Mergesort to sort. Calculates how long it takes too
     * 
     * @throws IOException
     */
    public void run() throws IOException {

        BufferPool pool = new BufferPool(dataAccess, buffNum);
        BufferSort sorter = new BufferSort(pool);

        long begin = System.currentTimeMillis();
        sorter.mergeSortHelp(dataAccess, tempFileAccess, 0,
                (int) (pool.fileLength() / 4 - 1));
        pool.flush();
        long end = System.currentTimeMillis();
        double timetaken = (double) end - begin;
        System.out.println("Sorting took " + timetaken / 1000.0 + " seconds");

        StatFileWriter fw = new StatFileWriter(statFile, pool);
        fw.write(dataFile, timetaken);

    }
}
