package ua.kharkov.kture.ot.view.swing.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.06.11
 */
public abstract class CommonFilter extends DocumentFilter {
    protected abstract boolean valid(String newText);

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String newText = StringUtils.replace(fb.getDocument().getText(0, fb.getDocument().getLength()), offset, length, text);
        if (valid(newText)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        String newText = StringUtils.insertString(fb.getDocument().getText(0, fb.getDocument().getLength()), offset, string);
        if (valid(newText)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        String newText = StringUtils.remove(fb.getDocument().getText(0, fb.getDocument().getLength()), offset, length);
        if (valid(newText)) {
            super.remove(fb, offset, length);
        }
    }
}
