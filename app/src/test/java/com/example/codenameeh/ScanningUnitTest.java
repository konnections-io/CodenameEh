package com.example.codenameeh;

import org.junit.Test;
import java.io.File;
import com.codenameeh.classes.Scanning;
import static junit.framework.TestCase.assertEquals;

public class ScanningUnitTest {

    @Test
    public void test() {
        //Set up test image
        File img = new File("testISBN.png");
        String photo = img.getPath();
        Scanning scanner = new Scanning();

        //Check ISBN of test photo
        String isbn = scanner.scanISBN(photo);
        assertEquals(isbn, "9783161484100");

    }
}
