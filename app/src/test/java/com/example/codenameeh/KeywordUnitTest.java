package com.example.codenameeh;

import org.junit.Test;

import com.example.codenameeh.classes.KeywordTracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KeywordUnitTest {
    // DATABASE TESTS MUST BE DONE MANUALLY, as I don't know how to assert that
    // This instead only checks adding, removing should perhaps also be added...

    String keyword = "fine";
    @Test
    public void test(){
        KeywordTracker keywords = new KeywordTracker();
        assertEquals(0, keywords.getKeywords().size());
        keywords.add(keyword);
        assertEquals(1, keywords.getKeywords().size());
        assertTrue(keywords.getKeywords().contains(keyword));
        keywords.add(keyword+5);
        // we get multiple
        assertEquals(2, keywords.getKeywords().size());
        assertTrue(keywords.getKeywords().contains(keyword+5));
        // previous is not overwritten
        assertTrue(keywords.getKeywords().contains(keyword));
    }
}
