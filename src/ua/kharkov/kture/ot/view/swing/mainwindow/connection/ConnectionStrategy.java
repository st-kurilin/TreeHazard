package ua.kharkov.kture.ot.view.swing.mainwindow.connection;

import ua.kharkov.kture.ot.view.swing.utils.Shape2D;

import java.awt.*;
import java.util.Collection;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 15.04.11
 */
public interface ConnectionStrategy {
    void connect(Shape2D parent, Collection<Shape2D> shape2Ds, Graphics graphics);
}
