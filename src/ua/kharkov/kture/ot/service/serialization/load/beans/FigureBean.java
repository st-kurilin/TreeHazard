package ua.kharkov.kture.ot.service.serialization.load.beans;

import ua.kharkov.kture.ot.common.math.Point;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 20.04.11
 */
public class FigureBean {
    Point location;
    String title;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
