package ua.kharkov.kture.ot.service.serialization.load.beans;

import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.model.Figure;

import java.util.Collection;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 17.04.11
 */
public class HazardBean extends FigureBean {
    private Collection<FigureBean> childs;
    private Figure parent;
    private Logic logic;
    private int twins;

    public Collection<FigureBean> getChilds() {
        return childs;
    }

    public void setChilds(Collection<FigureBean> childs) {
        this.childs = childs;
    }

    public Figure getParent() {
        return parent;
    }

    public void setParent(Figure parent) {
        this.parent = parent;
    }

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
