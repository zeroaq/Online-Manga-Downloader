package anonscanlations.downloader.chapter;

import java.io.*;
import java.net.*;
import org.junit.*;
import static org.junit.Assert.*;

import anonscanlations.downloader.*;

/**
 *
 * @author /a/non <anonymousscanlations@gmail.com>
 */
public class ChapterTest
{
    public ChapterTest(){}
    
    private void testChapterCRCs(Chapter instance, long[] CRC32) throws Exception
    {
        File directory = TestUtils.createTempDirectory();
        directory.deleteOnExit();
        Downloader.execute(0, instance, directory, true);

        assertTrue(CRC32.length == directory.listFiles().length);
        int i = 0;
        for(File file : directory.listFiles())
        {
            assertTrue(CRC32[i] == TestUtils.doChecksum(file));
            i++;

            assertTrue(file.delete());
        }
        assertTrue(directory.delete());
    }
    
    private void getChapterCRCs(Chapter instance) throws Exception
    {
        File directory = TestUtils.createTempDirectory();
        directory.deleteOnExit();
        Downloader.execute(0, instance, directory, true);
        
        System.out.print("long CRC32[] = new long[]{");
        int i = 0;
        for(File file : directory.listFiles())
        {
            System.out.printf("%dL, ", TestUtils.doChecksum(file));
            if((i++) % 4 == 3)
                System.out.println();
            assertTrue(file.delete());
        }
        System.out.println("};");
        assertTrue(directory.delete());
    }
    
    @Test
    public void testActibookRich() throws Exception
    {
        URL gangan = new URL("http://www.square-enix.com/jp/magazine/ganganonline/comic/ryushika/viewer/001/_SWF_Window.html");
        long ganganCRC32[] = new long[]{953943091L, 3741872794L, 801860577L, 568980999L, 
                                    148808672L, 3300037805L, 3945052797L, 35967345L, 
                                    1609260701L, 3725976640L, 2986456343L, 1550304275L, 
                                    621495340L, 3176832710L};
        testChapterCRCs(new ActibookChapter(gangan), ganganCRC32);
    }
    
    @Test
    public void testActibookNormal() throws Exception
    {
        URL doki = new URL("http://www.dokidokivisual.com/comics/book/actibook/wb79414/_SWF_Window.html");
        long dokiCRC32[] = new long[]{3760306193L, 2228918759L, 2591779391L, 3832333511L, 
                                    2054715754L, 97390896L, 2251394709L, 1995319103L, 
                                    3667164662L, 3840079627L, 2837765636L, 4233292560L};
        testChapterCRCs(new ActibookChapter(doki), dokiCRC32);
    }
    
    @Test
    public void testPocoChapter() throws Exception
    {
        URL url = new URL("http://www.poco2.jp/viewer/play.php?partid=f4b9ec30ad9f68f89b29639786cb62ef");
        long CRC32[] = new long[]{1664894238L, 235207973L, 958148213L, 2885041639L,
                                    1735256010L, 361631088L, 562778741L, 1674556494L,
                                    2406686717L, 685363998L, 934753219L, 3073342046L,
                                    2704109636L, 1906777089L};
        testChapterCRCs(new PocoChapter(url), CRC32);
    }
    
    @Test
    public void testMCSChapter() throws Exception
    {
        URL url = new URL("http://comic-rush.jp/viewer/sample?contentId=398&product_id=0000000008-00-0000");
        long CRC32[] = new long[]{2766193580L, 127162681L, 2384547131L, 1271547731L, 
                                    1585750090L, 2876598844L, 423447643L, 172077421L, 
                                    3409156279L, 1327683888L, 1424545672L, 361830919L, 
                                    2431235423L, 268000575L, 4157293085L, 3564055179L, 
                                    442942882L, 424692177L};
        testChapterCRCs(new MCSChapter(url), CRC32);
    }
}
