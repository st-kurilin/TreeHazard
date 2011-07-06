package ua.kharkov.kture.ot.view.swing.utils;

import ua.kharkov.kture.ot.common.math.Point;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.04.11
 */
public interface Widget {
    void draw(Graphics g, JPanel panel);

    boolean contains(Point location);
}
