package ua.kharkov.kture.ot.view.swing.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.xml.soap.Text;

import static org.testng.AssertJUnit.assertEquals;

/**
 * User: akril
 * Date: 6/13/11
 * Time: 8:48 PM
 */
public class StringUtilsTest {
    @Test(dataProvider = "replaceableStrings")
    public void testReplace(String text, int offset, int length, String added, String expected) throws Exception {
        assertEquals(StringUtils.replace(text, offset, length, added), expected);
    }

    @Test(dataProvider = "insertableStrings")
    public void testInsertString(String text, int offset, String inserted, String expected) throws Exception {
        assertEquals(StringUtils.insertString(text, offset, inserted), expected);
    }

    @Test(dataProvider = "removableStrings")
    public void testRemove(String text, int offset, int length, String expected) throws Exception {
        assertEquals(StringUtils.remove(text, offset, length), expected);
    }

    @DataProvider(name = "replaceableStrings")
    public Object[][] replaceableStrings(){
        return new Object[][] {
                {"a", 0, 1, "b", "b"},
                {"abc", 0, 1, "d", "dbc"},
                {"abc", 1, 1, "d", "adc"},
                {"abc", 0, 3, "cba", "cba"},
                {"abc", 0, 3, "", ""},
                {"abc", 2, 1, "xyz", "abxyz"},
                {"abc", 2, 1, "", "ab"}
        };
    }

    @DataProvider(name = "insertableStrings")
    public Object[][] insertableStrings(){
        return new Object[][] {
                {"", 0, "abc", "abc"},
                {"abc", 0, "def", "defabc"},
                {"abc", 2, "def", "abdefc"},
                {"abc", 3, "def", "abcdef"},
                {"abc", 0, "", "abc"},
                {"abc", 3, "", "abc"}
        };
    }

    @DataProvider(name = "removableStrings")
    public Object[][] removableStrings() {
        return new Object[][]{
                {"", 0, 0, ""},
                {"a", 0, 0, "a"},
                {"a", 0, 1, ""},
                {"abc", 0, 2, "c"},
                {"abc", 1, 1, "ac"},
                {"abc", 2, 0, "abc"},
                {"abc", 2, 1, "ab"}
        };
    }
}
