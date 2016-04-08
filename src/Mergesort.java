import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * On my honor: // // - I have not used source code obtained from another
 * student, // or any other unauthorized source, either modified or //
 * unmodified. // // - All source code and documentation used in my program is
 * // either my original work, or was derived by me from the // source code
 * published in the textbook for this course. // // - I have not discussed
 * coding details about this project with // anyone other than my partner (in
 * the case of a joint // submission), instructor, ACM/UPE tutors or the TAs
 * assigned // to this course. I understand that I may discuss the concepts //
 * of this program with other students, and that another student // may help me
 * debug my program so long as neither of us writes // anything during the
 * discussion or modifies any computer file // during the discussion. I have
 * violated neither the spirit nor // letter of this restriction.
 * 
 * @author sohyun
 * @author sshumway
 * @version 3/23/2016
 */
public class Mergesort {

    private static Processor process;
    /**
     * The entry point of the application 
     * java Mergesort
     * <data-file-name> <numb-buffers> <stat-file-name>
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        try {
            process = new Processor(args[0], args[2], Integer.parseInt(args[1]));
        }
        catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        process.run();
    }
}