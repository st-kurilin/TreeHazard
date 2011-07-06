package ua.kharkov.kture.ot.view.swing.changemodel.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.05.11
 */
public class ChangeTitleEvent implements EventBus.Event {
    private final String newTitle;

    public ChangeTitleEvent(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewTitle() {
        return newTitle;
    }
}
