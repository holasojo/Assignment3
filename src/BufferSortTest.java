
public class BufferSortTest extends student.TestCase {

    FileGenerator generate;
    CheckFile check;
    String[] args;
    public void setUp(){
        generate = new FileGenerator();
        check = new CheckFile();
        args = new String[3];

    }
    
public void test1() throws Exception{

    
  
    String[] str1 = { "-a", "text", "1" };
    generate.generateFile(str1);
    String[] str2 = { "text", "3", "stat" };
    Mergesort.main(str2);
    assertTrue(check.checkFile("text"));

}

}
