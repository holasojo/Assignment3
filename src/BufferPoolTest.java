
import java.io.IOException;
import java.io.RandomAccessFile;

import junit.framework.TestCase;

/**
 * 

 * 
 */
public class BufferPoolTest extends TestCase
{
    
    public void testBufferAltercations() throws IOException
    {
        RandomAccessFile file = new RandomAccessFile("test", "rw");
        byte[] byte1 = new byte[20];
        for ( int j = 0; j < 20; j++ )
        {
            byte1[j] = (byte) j;
        }

        byte[] byte2 = new byte[20];

        BufferPool pool = new BufferPool(file, 10);

        pool.getbytes( byte1, 20, 3 , file);
        pool.insert( byte2, 20, 3 , file);

        System.out.println(byte1[8]);
        System.out.println(byte2[8]);
        
        assertEquals( byte1[9], byte2[9] );
    }

//   
}