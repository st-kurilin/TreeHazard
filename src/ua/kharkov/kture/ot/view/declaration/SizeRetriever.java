package ua.kharkov.kture.ot.view.declaration;

import ua.kharkov.kture.ot.common.Dimension;
import ua.kharkov.kture.ot.common.math.Logic;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.04.11
 */
public interface SizeRetriever {
    Dimension getComponentsSize();

    Dimension getCalculatedEventsSize(Logic logic, int twins);

    Dimension getRootSize(Logic logic, int twins);

    Dimension getMaxElementSize();

    Dimension windowSize();

}
