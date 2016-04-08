/**
 * Tests the sort
 * @author sohyun
 * @author sshumway
 * @version 04/07/2016
 *
 */
public class BufferSortTest extends student.TestCase {

    private FileGenerator generate;
    private CheckFile check;

    /**
     * set up
     */
    public void setUp() {
        generate = new FileGenerator();
        check = new CheckFile();

    }

    /**
     * generate a file and runs the test to see if it is sorted
     * @throws Exception
     */
    public void test1() throws Exception {

        String[] str1 = { "-a", "text", "10" };
        generate.generateFile(str1);
        String[] str2 = { "text", "4", "stat" };
        Mergesort.main(str2);
        assertTrue(check.checkFile("text"));

    }

}
