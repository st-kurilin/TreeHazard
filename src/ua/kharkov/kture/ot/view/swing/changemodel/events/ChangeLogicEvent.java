package ua.kharkov.kture.ot.view.swing.changemodel.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.math.Logic;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class ChangeLogicEvent implements EventBus.Event {
    private final Logic logic;

    public ChangeLogicEvent(Logic logic) {
        this.logic = logic;
    }

    public Logic getLogic() {
        return logic;
    }
}
