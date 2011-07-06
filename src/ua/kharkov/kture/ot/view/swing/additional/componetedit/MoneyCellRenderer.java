package ua.kharkov.kture.ot.view.swing.additional.componetedit;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.06.11
 */
//TODO: should deal with Money, not double
public class MoneyCellRenderer extends DefaultTableCellRenderer {
    public void setValue(Object value) {
        if (value == null) {
            setText("");
            return;
        }
        if (!(value instanceof Double)) {
            throw new AssertionError("Double's instance expected, but" + value + "found");
        }
        setText(new DecimalFormat("#0.######", new DecimalFormatSymbols(Locale.US)).format(value) + ", y.e.");
    }
}
