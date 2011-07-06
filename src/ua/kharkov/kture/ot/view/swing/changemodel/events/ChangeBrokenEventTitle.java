package ua.kharkov.kture.ot.view.swing.changemodel.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.05.11
 */
public class ChangeBrokenEventTitle implements EventBus.Event {
    private final String newBrokenEventTitle;

    public ChangeBrokenEventTitle(String newBrokenEventTitle) {
        this.newBrokenEventTitle = newBrokenEventTitle;
    }

    public String getNewBrokenEventTitle() {
        return newBrokenEventTitle;
    }
}