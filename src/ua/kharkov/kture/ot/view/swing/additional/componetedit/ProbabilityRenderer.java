package ua.kharkov.kture.ot.view.swing.additional.componetedit;

import ua.kharkov.kture.ot.common.math.Probability;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.06.11
 */
//TODO: ATTENTION. USE IT CAREFULLY. ProbabilityRenderer should be Injected
public class ProbabilityRenderer extends DefaultTableCellRenderer {
    private static final ProbabilityIO IO = new ProbabilityIO();

    public void setValue(Object value) {
        if (value == null) {
            setText("");
            return;
        }
        if (!(value instanceof Probability)) {
            throw new AssertionError("Probability's instance expected, but" + value + "found");
        }
        setText(IO.toString((Probability) value));
    }
}

