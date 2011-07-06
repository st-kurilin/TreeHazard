package ua.kharkov.kture.ot.view.swing.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.math.Point;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class ShowPopupMenuEvent implements EventBus.Event {
    private Point location;

    public ShowPopupMenuEvent(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }
}
