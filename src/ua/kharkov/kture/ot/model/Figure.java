package ua.kharkov.kture.ot.model;

import ua.kharkov.kture.ot.common.connector.Observer;
import ua.kharkov.kture.ot.common.math.Point;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public abstract class Figure implements Cloneable {
    protected Point location;
    protected Observer listener;
    protected String title;

    public void setListener(Observer listener) {
        this.listener = listener;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        if (!this.location.equals(location)) {
            this.location = location;
            listener.update(this, "location");
        }
    }

    public void delete() {
        listener.dispose(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        listener.update(this, "title");
    }

    public abstract <R> R accept(Visitor<R> visitor);
}
