package ua.kharkov.kture.ot.view.swing.changemodel.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.math.Logic;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 11.06.11
 */
public class DecomposeComponent implements EventBus.Event {
    private final Logic logic;

    public DecomposeComponent(Logic logic) {
        this.logic = logic;
    }

    public Logic getLogic() {
        return logic;
    }
}
