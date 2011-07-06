package ua.kharkov.kture.ot.view.swing.additional.componetedit;

import ua.kharkov.kture.ot.common.math.Probability;

import javax.swing.*;
import java.awt.*;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 15.06.11
 */
public class ProbabilityEditor extends DefaultCellEditor {
    private JFormattedTextField textField;
    private static final Probability DEFAULT_VALUE = Probability.commonForm(0);
    private static final ProbabilityIO IO = new ProbabilityIO();

    //TODO: ATTENTION. USE IT CAREFULLY. ProbabilityEditor should be Injected
    public static ProbabilityEditor create() {
        JFormattedTextField formattedTextField =
                new JFormattedTextField(new Format() {
                    @Override
                    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                        return toAppendTo.append(IO.toString((Probability) obj));
                    }

                    @Override
                    public Object parseObject(String source, ParsePosition pos) {
                        return IO.parse(source.substring(pos.getIndex()), DEFAULT_VALUE);
                    }
                });
        formattedTextField.setFocusLostBehavior(JFormattedTextField.COMMIT);
        return new ProbabilityEditor(formattedTextField);

    }


    protected ProbabilityEditor(JFormattedTextField textField) {
        super(textField);
        this.textField = textField;
        textField.setLocale(Locale.US);
        textField.setHorizontalAlignment(JFormattedTextField.CENTER);
    }

    @Override
    public Object getCellEditorValue() {
        String text = textField.getText();
        return IO.parse(text, DEFAULT_VALUE);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setText((value == null) ? "" : IO.toString(((Probability) value)));
        if (isSelected) textField.selectAll();
        return textField;
    }

}