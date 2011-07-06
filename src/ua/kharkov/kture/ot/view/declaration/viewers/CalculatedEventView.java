package ua.kharkov.kture.ot.view.declaration.viewers;

import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.common.math.Probability;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public interface CalculatedEventView extends FigureView<CalculatedEventView.Presenter> {
    interface Presenter extends FigureView.Presenter {

        //TODO addWidget method chengeTitle
        void changeLogic(Logic newLogic);

        void addComponentChild(Point location);

        void addEventChild(Logic logic, Point location);

        void twins(int number);
    }

    void setLogic(Logic newLogic);

    void setTwins(int number);

    void setProbability(Probability probability);
}