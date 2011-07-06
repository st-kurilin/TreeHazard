package ua.kharkov.kture.ot.view.swing.utils;

import ua.kharkov.kture.ot.common.Dimension;
import ua.kharkov.kture.ot.common.math.Point;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 15.04.11
 */
public class Shape2D {
    private final Point location;
    private final Dimension size;

    public Shape2D(Point location, Dimension size) {
        this.location = location;
        this.size = size;
    }

    public Point getLocation() {
        return location;
    }

    public Dimension getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape2D)) return false;
        Shape2D other = (Shape2D) o;
        return location.equals(other.location) && size.equals(other.size);
    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + size.hashCode();
        return result;
    }
}
