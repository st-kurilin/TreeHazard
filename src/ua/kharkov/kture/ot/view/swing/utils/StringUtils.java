package ua.kharkov.kture.ot.view.swing.utils;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.06.11
 */
public class StringUtils {
    public static String replace(String text, int offset, int length, String added) {
        String beforeReplace = text.substring(0, offset);
        String afterReplace = text.substring(offset + length);
        return beforeReplace + added + afterReplace;
    }

    public static String insertString(String text, int offset, String inserted) {
        String beforeRegion = text.substring(0, offset);
        String afterRegion = text.substring(offset);
        return beforeRegion + inserted + afterRegion;
    }

    public static String remove(String text, int offset, int length) {
        String beforeRegion = text.substring(0, offset);
        String afterRegion = text.substring(offset + length);
        return beforeRegion + afterRegion;
    }
}
