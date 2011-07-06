package ua.kharkov.kture.ot.view.swing.changemodel.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 08.05.11
 */
public class ChangeTwinsEvent implements EventBus.Event {
    private final int twins;

    public ChangeTwinsEvent(int twins) {
        this.twins = twins;
    }

    public ChangeTwinsEvent() {
        this(1);
    }

    public int getTwins() {
        return twins;
    }
}
