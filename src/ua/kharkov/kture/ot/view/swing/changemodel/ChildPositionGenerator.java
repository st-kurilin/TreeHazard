package ua.kharkov.kture.ot.view.swing.changemodel;

import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.05.11
 */
public interface ChildPositionGenerator {
    Point generatePosition(CalculatedEventView parent);
}
