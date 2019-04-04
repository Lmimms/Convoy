package com.example.convoy;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PollUnitTest {
    @Test
    public void pollFailsIfNoChoicesTest() {
        ArrayList<String> s = new ArrayList<String>();
        assertFalse(Poll.isValid(s));
    }
    @Test
    public void getChoicesTest() {
        ArrayList<String> a = new ArrayList<String>();
        a.add("test");
        Poll p = new Poll(a);
        assertEquals(a, p.getChoices());
    }
    @Test
    public void getChoiceCountTest() {
        ArrayList<String> a = new ArrayList<>();
        a.add("test");
        a.add("choice b");
        Poll p = new Poll(a);
        assertEquals(2, p.getChoiceCount());
    }
    @Test
    public void duplicateChoiceTest() {
        ArrayList<String> a = new ArrayList<>();
        a.add("test");
        a.add("test");
        assert(Poll.isValid(a));
    }
    @Test
    public void emptyChoiceTest() {
        ArrayList<String> a = new ArrayList<>();
        a.add("");
        assertFalse(Poll.isValid(a));
    }
}