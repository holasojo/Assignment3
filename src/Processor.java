import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Processor {

    private String dataFile;
    private String statFile;
    private String tempFile;
    private int buffNum;
    private BufferPool pool;
    private RandomAccessFile dataAccess;
    private RandomAccessFile tempFileAccess;
    
    
    public Processor(String data, String stat, int buff) throws FileNotFoundException{
        dataFile = data;
        statFile = stat;
        tempFile = "temp";
        buffNum = buff;
        dataAccess = new RandomAccessFile(dataFile, "rw");
        tempFileAccess = new RandomAccessFile(tempFile, "rw");
        
    }
    
    public void run() throws IOException{

        BufferPool pool = new BufferPool(dataAccess, buffNum);
        BufferSort sorter = new BufferSort(pool);

        long begin = System.currentTimeMillis();
        sorter.mergeSort(dataAccess, tempFileAccess);
        pool.flush();
        long end = System.currentTimeMillis();
        double timetaken = (double) end-begin;
        System.out.println("Sorting took " + timetaken / 1000.0
                + " seconds");

        StatFileWriter fw = new StatFileWriter(statFile, pool);
        fw.write(dataFile, timetaken);
        
    }
}
