package ua.kharkov.kture.ot.creation.mvp;

import com.google.inject.assistedinject.Assisted;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.presenters.HazardPresenter;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 05.04.11
 */
public interface CalculatedEventFactory {
    HazardPresenter create(@Assisted HazardPresenter presenter, @Assisted String brokenEventTitle, @Assisted Logic logic, @Assisted Point location);

    HazardPresenter create(@Assisted HazardPresenter presenter, @Assisted Logic logic, @Assisted Point location);
}
