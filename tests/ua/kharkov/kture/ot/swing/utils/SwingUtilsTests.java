package ua.kharkov.kture.ot.swing.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.kharkov.kture.ot.view.swing.utils.StringUtils;

import static org.testng.Assert.assertEquals;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.06.11
 */
public class SwingUtilsTests {


    @Test(dataProvider = "replaceableStrings")
    public void replaceTest(String text, int offset, int length, String added, String expected) {
        assertEquals(StringUtils.replace(text, offset, length, added), expected);
    }

    @Test(dataProvider = "insertableStrings")
    public void insertStringTest(String text, int offset, String inserted, String expected) {
        assertEquals(StringUtils.insertString(text, offset, inserted), expected);
    }

    @Test(dataProvider = "removableStrings")
    public void removeTest(String text, int offset, int length, String expected) {
        assertEquals(StringUtils.remove(text, offset, length), expected);
    }

    @DataProvider(name = "replaceableStrings")
    public Object[][] replaceableStrings() {
        return new Object[][]{
                //text, offset, length, added
                {"a", 0, 1, "b", "b"},
                {"abc", 0, 1, "d", "dbc"},
                {"abc", 1, 1, "d", "adc"},
                {"abc", 0, 3, "cba", "cba"},
                {"abc", 0, 3, "", ""},
                {"abc", 2, 1, "xyz", "abxyz"},
                {"abc", 2, 1, "", "ab"},

                {"aaa", 1, 2, "b", "ab"},
                {"aaa", 1, 2, "bc", "abc"},
                {"aaa", 1, 2, "", "a"},
                {"", 0, 0, "ba", "ba"},
                {"", 0, 0, "", ""}
        };
    }

    @DataProvider(name = "insertableStrings")
    public Object[][] insertableStrings() {
        return new Object[][]{
                //text, offset, inserted
                {"", 0, "abc", "abc"},
                {"abc", 0, "def", "defabc"},
                {"abc", 2, "def", "abdefc"},
                {"abc", 3, "def", "abcdef"},
                {"abc", 0, "", "abc"},
                {"abc", 3, "", "abc"},

                {"abc", 1, "d", "adbc"},
                {"abc", 2, "e", "abec"},
                {"abc", 2, "ef", "abefc"},
                {"abc", 1, "ef", "aefbc"},
                {"abc", 0, "de", "deabc"},
                {"abc", 1, "", "abc"},
                {"abc", 0, "", "abc"},
                {"", 0, "ba", "ba"},
                {"a", 0, "", "a"},
                {"", 0, "", ""}
        };
    }

    @DataProvider(name = "removableStrings")
    public Object[][] removableStrings() {
        return new Object[][]{
                //text, offset, length
                {"", 0, 0, ""},
                {"a", 0, 0, "a"},
                {"a", 0, 1, ""},
                {"abc", 0, 2, "c"},
                {"abc", 1, 1, "ac"},
                {"abc", 2, 0, "abc"},
                {"abc", 2, 1, "ab"},

                {"abc", 1, 1, "ac"},
                {"abc", 0, 1, "bc"},
                {"abc", 0, 2, "c"},
                {"", 0, 0, ""},
                {"abc", 0, 3, ""},
                {"abc", 2, 0, "abc"},
                {"abcd", 1, 2, "ad"},
                {"abcde", 2, 2, "abe"},
                {"abcd", 1, 2, "ad"}
        };
    }
}
