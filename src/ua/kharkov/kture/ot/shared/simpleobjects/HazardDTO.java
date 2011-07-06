package ua.kharkov.kture.ot.shared.simpleobjects;

import ua.kharkov.kture.ot.common.math.Logic;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 24.04.11
 */
public class HazardDTO extends FigureDTO {
    private Logic logic;
    private int twins;

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public int getTwins() {
        return twins;
    }

    public void setTwins(int twins) {
        this.twins = twins;
    }
}
