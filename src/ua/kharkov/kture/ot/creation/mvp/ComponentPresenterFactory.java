package ua.kharkov.kture.ot.creation.mvp;

import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.presenters.ComponentPresenter;
import ua.kharkov.kture.ot.presenters.HazardPresenter;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 28.02.11
 */
public interface ComponentPresenterFactory {

    ComponentPresenter create(HazardPresenter parentPresenter, Point location);
}
