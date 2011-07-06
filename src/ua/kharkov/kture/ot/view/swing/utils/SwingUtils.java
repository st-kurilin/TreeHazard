package ua.kharkov.kture.ot.view.swing.utils;

import ua.kharkov.kture.ot.common.MouseButtonType;
import ua.kharkov.kture.ot.common.math.Point;

public class SwingUtils {

    public static Point convert(java.awt.Point point) {
        return new Point((int) point.getX(), (int) point.getY());
    }

    public static java.awt.Point convert(Point point) {
        return new java.awt.Point(point.getX(), point.getY());
    }

    public static MouseButtonType convert(int button) {
        switch (button) {
            case 0:
                return MouseButtonType.LEFT;
            case 1:
                return MouseButtonType.LEFT;
            case 3:
                return MouseButtonType.RIGHT;
            default:
                throw new AssertionError("unexpected value " + button);
        }
    }
}
